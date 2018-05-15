package de.msg.jbit7.migration.itnrw.partner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingRepository;
import de.msg.jbit7.migration.itnrw.mapping.PageableCollection;
import de.msg.jbit7.migration.itnrw.mapping.support.CatchExceptionRuleListener;
import de.msg.jbit7.migration.itnrw.partner.support.FamilyMemberTerminationDatesByPartnerNumberConverter;
import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;



@Service
abstract class PartnerService {
	final static Logger LOGGER = LoggerFactory.getLogger(PartnerService.class);
	
	private final IdMappingRepository idMappingRepository;
	
	private final PartnerRepository partnerRepository;
	
	private final Date defaultDate = Date.from(LocalDate.of(1900, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());

	private final StammRepository stammRepository;
	
	private final FamilyMemberTerminationDatesByPartnerNumberConverter converter;
	private final Rules rules;
	
	
	
	
	PartnerService(final IdMappingRepository idMappingRepository, final StammRepository stammRepository,final PartnerRepository partnerRepository, @Qualifier("partnerRules") final Rules rules, final FamilyMemberTerminationDatesByPartnerNumberConverter converter) {
		this.idMappingRepository = idMappingRepository;
		this.stammRepository=stammRepository;
		this.partnerRepository=partnerRepository;		
		this.rules=rules;
		this.converter=converter;
	} 
	
	public void importPartners(final long mandator, final boolean cleanMandator) {
		countersExistsGuard(mandator);
		
		final List<IdMapping> idMappings = idMappingRepository.findAll(mandator);
		if( cleanMandator) {
			delete(idMappings,10);
		}
	//	final List<IdMapping> idMappings = idMappingRepository.findAll();
		final Map<Long,Date> contractDates =  stammRepository.beginDates();
				
			idMappings.forEach(mapping -> processPartner(mandator, contractDates, mapping));
	}

	private void countersExistsGuard(final long mandator) {
		idMappingRepository.findCounters(mandator);
	}

	private void processPartner(final long mandator, final Map<Long, Date> contractDates, IdMapping mapping) {
		Assert.isTrue(mandator == mapping.getMandator(), "Mandator should be the same.");	
		Date contractDate = contractDates.get(mapping.getBeihilfenr());
		if( contractDate == null) {
			contractDate=defaultDate;
			LOGGER.warn("BeihilfeNr: " + mapping.getBeihilfenr() + " no contract begin date found, 01.01.1900 used.");
		}
		final StammImpl stamm = stammRepository.findStamm(mapping.getBeihilfenr());
		
		final List<SepaBankVerbindung> sepaBankVerbindung = stammRepository.findSepaBank(mapping.getBeihilfenr());
		
		
		
		final List<Object> results = new ArrayList<>();
		final Facts facts = new SpelFacts(Arrays.asList(converter));
		facts.put(PartnerFacts.ID_MAPPING, mapping);
		facts.put(PartnerFacts.CONTRACT_DATE, contractDate);
		facts.put(PartnerFacts.STAMM, stamm);
	
		facts.put(PartnerFacts.SEPA_BANK, sepaBankVerbindung);
		facts.put(PartnerFacts.RESULTS, results );
		final DefaultRulesEngine rulesEngine = rulesEngine();
		final CatchExceptionRuleListener ruleListener = ruleListener();
		rulesEngine.registerRuleListener(ruleListener);
		
		rulesEngine.fire(rules, facts);
		
		if( ruleListener.hasErrors()) {
			ruleListener.exceptions().forEach(ex -> LOGGER.error("Exceptions Beihilfenr: " + mapping.getBeihilfenr(), ex));
		} else {
			LOGGER.info("Regeln erfolgreich verarbeitet Beihilfenr: " + mapping.getBeihilfenr());
		}
		
		results.forEach(result -> persist(mapping.getBeihilfenr(), result));
		
	}

	private <T> void persist(final Long beihilfeNr, T object ) {
		try {
			partnerRepository.persist(object);
		} catch (final Exception exception) {
			LOGGER.error("Error saving entity: " + beihilfeNr, exception);
		}
	}
	
	
	private void delete(final Collection<IdMapping> idMappings, final int pageSize) {
		final PageableCollection<String> partners  = new PageableCollection<>(idMappings.stream().map(mapping -> mapping.getPartnerNr()).collect(Collectors.toList()), pageSize); 

		IntStream.range(0, partners.maxPages()).forEach(page -> partnerRepository.cleanPartners(partners.page(page)));
		
		final PageableCollection<Long> contacts  = new PageableCollection<>(idMappings.stream().map(mapping -> mapping.getContractNumber()).collect(Collectors.toList()), pageSize); 
		IntStream.range(0, contacts.maxPages()).forEach(page -> partnerRepository.cleanContracts(contacts.page(page)));
	}

	
	@Lookup
	abstract DefaultRulesEngine rulesEngine();
	
	@Lookup
	abstract CatchExceptionRuleListener ruleListener();
}
