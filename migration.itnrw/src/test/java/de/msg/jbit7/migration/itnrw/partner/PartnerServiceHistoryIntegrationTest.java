package de.msg.jbit7.migration.itnrw.partner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingBuilder;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingRepository;
import de.msg.jbit7.migration.itnrw.mapping.support.CatchExceptionRuleListener;
import de.msg.jbit7.migration.itnrw.mapping.support.Counters;
import de.msg.jbit7.migration.itnrw.partner.support.FamilyMemberTerminationDatesByPartnerNumberConverter;
import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindungBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;

@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({ "/beans.xml" })
public class PartnerServiceHistoryIntegrationTest {

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	@Qualifier("partnerRules")
	private Rules rules;

	@Autowired
	private FamilyMemberTerminationDatesByPartnerNumberConverter converter;

	@Autowired
	private DefaultRulesEngine rulesEngine;

	@Autowired
	private CatchExceptionRuleListener ruleListener;

	private final IdMappingRepository idMappingRepository = Mockito.mock(IdMappingRepository.class);

	private final StammRepository stammRepository = Mockito.mock(StammRepository.class);

	private final long MANDATOR = 4711L;
	
	private PartnerService partnerService;
	
	@BeforeEach
	void setup() {
		partnerService = new PartnerService(idMappingRepository, stammRepository,
				partnerRepository, rules, converter) {

			@Override
			DefaultRulesEngine rulesEngine() {
				return rulesEngine;
			}

			@Override
			CatchExceptionRuleListener ruleListener() {
				return ruleListener;
			}
		};

		final IdMapping mapping = IdMappingBuilder.builder().withMandator(MANDATOR).build();
		Mockito.doReturn(Arrays.asList(mapping)).when(idMappingRepository).findAll();
		final StammImpl stamm = StammBuilder.builder().withBeihilfenr(mapping.getBeihilfenr()).withSterbeDatum()
				.build();

		Mockito.doReturn(stamm).when(stammRepository).findStamm(mapping.getBeihilfenr());
		final Counters counters = Mockito.mock(Counters.class);
		Mockito.doReturn(MANDATOR).when(counters).mandator();
		Mockito.doReturn(counters).when(idMappingRepository).findCounters(MANDATOR);
		final Map<Long, Date> beginDates = new HashMap<>();
		final Date contractDate = new Date();
		beginDates.put(mapping.getBeihilfenr(), contractDate);
		Mockito.doReturn(beginDates).when(stammRepository).beginDates();
		final SepaBankVerbindung sepaBankVerbindung = SepaBankVerbindungBuilder.builder().build();
		Mockito.doReturn(Arrays.asList(sepaBankVerbindung)).when(stammRepository).findSepaBank(mapping.getBeihilfenr());
	
	}

	@Test
	void importDeathPartner() {
		partnerService.importPartners(MANDATOR, true);
	}

	
}
