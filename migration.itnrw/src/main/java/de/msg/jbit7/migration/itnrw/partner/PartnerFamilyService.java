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
import java.util.stream.Stream;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingRepository;
import de.msg.jbit7.migration.itnrw.mapping.PageableCollection;
import de.msg.jbit7.migration.itnrw.mapping.support.CatchExceptionRuleListener;
import de.msg.jbit7.migration.itnrw.partner.support.FamilyMemberTerminationDatesByPartnerNumberConverter;
import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;

@Service
abstract class PartnerFamilyService {
	
	final static Logger LOGGER = LoggerFactory.getLogger(PartnerFamilyService.class);
	
	private final Date defaultDate = Date.from(LocalDate.of(1900, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
	
	private final FamilyMemberTerminationDatesByPartnerNumberConverter converter;
	
	private final Integer pageSize;
	
	@Autowired
	PartnerFamilyService(final IdMappingRepository idMappingRepository, final StammRepository stammRepository, final PartnerRepository partnerRepository, @Qualifier("partnerFamilyRules")final Rules rules, final FamilyMemberTerminationDatesByPartnerNumberConverter converter,  @Value("${cleanup.page.size:10}") final Integer pageSize ) {
		this.idMappingRepository = idMappingRepository;
		this.stammRepository = stammRepository;
		this.partnerRepository=partnerRepository;
		this.rules=rules;
		this.converter=converter;
		this.pageSize = pageSize;
	}

	private final IdMappingRepository idMappingRepository;

	private final StammRepository stammRepository;
	private final PartnerRepository partnerRepository;
	
	private final Rules rules; 
	public void createPartners(final Long mandator, final boolean cleanup) {
		
		
		final List<IdMapping> idMappings = idMappingRepository.findAll(mandator);
		
		if( cleanup) {
			delete(idMappings);
		}
		
		final Map<Long,Date> contractDates =  stammRepository.beginDates();
		
		final Map<Long, Ehegatte> marrigePartners = stammRepository.findAllEhegatte().stream().collect(Collectors.toMap(mp -> mp.getBeihilfenr(), mp -> mp));
		
		idMappings.forEach(mapping -> {
			Date contractDate = contractDates.get(mapping.getBeihilfenr());
			if( contractDate == null) {
				contractDate=defaultDate;
				LOGGER.warn("BeihilfeNr: " + mapping.getBeihilfenr() + " no contract begin date found, 01.01.1900 used.");
			}
			final StammImpl stamm = stammRepository.findStamm(mapping.getBeihilfenr());
			final Collection<KindInfo> children = stammRepository.findChildren(mapping.getBeihilfenr(), mapping.getChildrenNr());
			
			final List<Object> results = new ArrayList<>();
			final Facts facts = new SpelFacts(Arrays.asList(converter));
			facts.put(PartnerFamilyFacts.ID_MAPPING, mapping);
			facts.put(PartnerFamilyFacts.RESULTS, results);
			facts.put(PartnerFamilyFacts.STAMM, stamm);
			facts.put(PartnerFamilyFacts.CONTRACT_DATE, contractDate);
			facts.put(PartnerFamilyFacts.MARRIAGE_PARTNER, marrigePartners.get(mapping.getBeihilfenr()));
			facts.put(PartnerFamilyFacts.CHILDREN, children);
			
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
			
		});
	}
	
	private <T> void persist(final Long beihilfeNr, T object ) {
		try {
			partnerRepository.persist(object);
		} catch (final Exception exception) {
			LOGGER.error("Error saving entity: " + beihilfeNr, exception);
		}
	}
	
	private void delete(final Collection<IdMapping> mappings) {
	
		final PageableCollection<String> partners = new PageableCollection<String>(marriagePartnerAndChildrenPartnerNumbers(mappings), pageSize);
		
		IntStream.range(0, partners.maxPages()).forEach(page -> partnerRepository.cleanPartners(partners.page(page)));
		
	}

	private Collection<String> marriagePartnerAndChildrenPartnerNumbers(final Collection<IdMapping> mappings) {
		return Stream.concat(
		mappings.stream().filter(mapping -> StringUtils.hasText(mapping.getMarriagePartnerNr())).map(mapping -> mapping.getMarriagePartnerNr()),	
		mappings.stream().filter(mapping -> mapping.getChildrenPartnerNr().length > 0).map(mapping -> Arrays.asList(mapping.getChildrenPartnerNr())).reduce(new ArrayList<>()
				, (a, b) -> {
				    a.addAll(b);
				    return a;
				}).stream() ).collect(Collectors.toList());
	}
	
	@Lookup
	abstract DefaultRulesEngine rulesEngine();
	
	@Lookup
	abstract CatchExceptionRuleListener ruleListener();

}
