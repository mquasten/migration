package de.msg.jbit7.migration.itnrw.partner;

import static de.msg.jbit7.migration.itnrw.util.TestUtil.assertEqualsRequired;
import static de.msg.jbit7.migration.itnrw.util.TestUtil.assertSameDay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import de.msg.jbit7.migration.itnrw.util.TestUtil;

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
	private final IdMapping mapping = IdMappingBuilder.builder().withMandator(MANDATOR).build();
	
	private final Date contractDate = new Date(0);
	
	final StammImpl stamm = StammBuilder.builder().withBeihilfenr(mapping.getBeihilfenr()).withSterbeDatum()
			.build();
	
	
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

		
		Mockito.doReturn(Arrays.asList(mapping)).when(idMappingRepository).findAll();
		

		Mockito.doReturn(stamm).when(stammRepository).findStamm(mapping.getBeihilfenr());
		final Counters counters = Mockito.mock(Counters.class);
		Mockito.doReturn(MANDATOR).when(counters).mandator();
		Mockito.doReturn(counters).when(idMappingRepository).findCounters(MANDATOR);
		final Map<Long, Date> beginDates = new HashMap<>();
		beginDates.put(mapping.getBeihilfenr(), contractDate);
		Mockito.doReturn(beginDates).when(stammRepository).beginDates();
		final SepaBankVerbindung sepaBankVerbindung = SepaBankVerbindungBuilder.builder().build();
		Mockito.doReturn(Arrays.asList(sepaBankVerbindung)).when(stammRepository).findSepaBank(mapping.getBeihilfenr());
	
	}

	@Test
	void importDeathPartner() {
		partnerService.importPartners(MANDATOR, true);
		final List<PartnerCore> partners = partnerRepository.findPartnerHists(mapping.getPartnerNr());
		
		assertEquals(2, partners.size());
		final PartnerCore firstPartnerCore = partners.get(0);
		
		assertSameDay(contractDate, firstPartnerCore.getInd());
		assertSameDay(contractDate, firstPartnerCore.getDop());
		assertEquals(Long.valueOf(1), firstPartnerCore.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), firstPartnerCore.getProcessnr());
		assertEqualsRequired(mapping.getPartnerNr(), firstPartnerCore.getPartnersNr());
		assertEquals(Long.valueOf(0), firstPartnerCore.getTerminationflag());
		assertNull(firstPartnerCore.getDateOfDeath());
		
		final PartnerCore terminatedPartnerCore = partners.get(1);
		
		
		assertSameDay(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())), terminatedPartnerCore.getInd());
		
		assertSameDay(TestUtil.toDate(stamm.getSterbedatum()), terminatedPartnerCore.getDop());
		assertEquals(Long.valueOf(2), terminatedPartnerCore.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), terminatedPartnerCore.getProcessnr());
		assertEqualsRequired(mapping.getPartnerNr(), terminatedPartnerCore.getPartnersNr());
		assertEquals(Long.valueOf(1), terminatedPartnerCore.getTerminationflag());
		assertSameDay(TestUtil.toDate(stamm.getSterbedatum()),terminatedPartnerCore.getDateOfDeath()); 
		
		
		final List<PartnersRole> partnersRoles = partnerRepository.findPartnersRoleHists(mapping.getPartnerNr());
		assertEquals(4, partnersRoles.size());
		
		final PartnersRole firstPartnersRoleIP =  partnersRoles.get(0);
		assertEquals("IP", firstPartnersRoleIP.getRole());
		
		assertFirstRole(firstPartnersRoleIP);
	
		
		final PartnersRole terminatedPartnersRoleIP =  partnersRoles.get(1);
		assertEquals("IP", firstPartnersRoleIP.getRole());
		assertTerminatedRole(terminatedPartnersRoleIP);
		
		
		final PartnersRole firstPartnersRolePH =  partnersRoles.get(2);
		assertEquals("PH", firstPartnersRolePH.getRole());
		assertFirstRole(firstPartnersRolePH);
		
		final PartnersRole terminatedPartnersRolePH =  partnersRoles.get(3);
		assertEquals("PH", terminatedPartnersRolePH.getRole());
		assertTerminatedRole(terminatedPartnersRolePH);
		
		final List<PMContract> contracts = partnerRepository.findContractHists(mapping.getContractNumber());
		
		assertEquals(2, contracts.size());
		
		final PMContract firstContract = contracts.get(0);
		assertSameDay(contractDate, firstContract.getInd());
		assertSameDay(contractDate, firstContract.getDop());
		assertEquals(Long.valueOf(1), firstContract.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), firstContract.getProcessnr());
		assertEqualsRequired(mapping.getContractNumber(), firstContract.getContractNumber());
		assertEquals(Long.valueOf(0), firstContract.getTerminationflag());
		assertNull(firstContract.getTerminationDate());
		
		final PMContract terminatedContract = contracts.get(1);
		assertSameDay(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())), terminatedContract.getInd());
		assertSameDay(TestUtil.toDate(stamm.getSterbedatum()), terminatedContract.getDop());
		assertEquals(Long.valueOf(2), terminatedContract.getHistnr());
		assertEqualsRequired(mapping.getContractNumber(), terminatedContract.getContractNumber());
		assertEqualsRequired(mapping.getProcessNumber(), terminatedContract.getProcessnr());
		assertEquals(Long.valueOf(1), terminatedContract.getTerminationflag());
		assertSameDay(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())),terminatedContract.getTerminationDate()); 
		
	}

	private void assertTerminatedRole(final PartnersRole terminatedPartnersRoleIP) {
		assertSameDay(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())), terminatedPartnersRoleIP.getInd());
		
		assertSameDay(TestUtil.toDate(stamm.getSterbedatum()), terminatedPartnersRoleIP.getDop());
		assertEquals(Long.valueOf(2), terminatedPartnersRoleIP.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), terminatedPartnersRoleIP.getProcessnr());
		assertEqualsRequired(mapping.getPartnerNr(), terminatedPartnersRoleIP.getRightSide());
		assertEquals(Long.valueOf(1), terminatedPartnersRoleIP.getTerminationflag());
	}

	private void assertFirstRole(final PartnersRole firstPartnersRoleIP) {
		assertSameDay(contractDate, firstPartnersRoleIP.getInd());
		assertSameDay(contractDate, firstPartnersRoleIP.getDop());
		assertEquals(Long.valueOf(1), firstPartnersRoleIP.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), firstPartnersRoleIP.getProcessnr());
		assertEqualsRequired(mapping.getPartnerNr(), firstPartnersRoleIP.getRightSide());
		assertEquals(Long.valueOf(0), firstPartnersRoleIP.getTerminationflag());
	}

	
}
