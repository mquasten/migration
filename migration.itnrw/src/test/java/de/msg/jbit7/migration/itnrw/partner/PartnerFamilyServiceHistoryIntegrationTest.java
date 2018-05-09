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
import de.msg.jbit7.migration.itnrw.partner.support.FamilyMemberTerminationDatesByPartnerNumberConverter;
import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.EhegatteBuilder;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.StammBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;
import de.msg.jbit7.migration.itnrw.util.TestUtil;
@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
public class PartnerFamilyServiceHistoryIntegrationTest {
	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	@Qualifier("partnerFamilyRules")
	private Rules rules;

	@Autowired
	private FamilyMemberTerminationDatesByPartnerNumberConverter converter;

	@Autowired
	private DefaultRulesEngine rulesEngine;

	@Autowired
	private CatchExceptionRuleListener ruleListener;
	
	private final IdMappingRepository idMappingRepository = Mockito.mock(IdMappingRepository.class);
	
	private final StammRepository stammRepository = Mockito.mock(StammRepository.class);
	

	private PartnerFamilyService partnerFamilyService;
	
	private final long MANDATOR = 4711L;
	
	private Date  contractDate = new Date(0); 
		
	@BeforeEach
	void setup() {
		partnerRepository.cleanMandator(MANDATOR);
		
		partnerFamilyService = new PartnerFamilyService(idMappingRepository, stammRepository ,partnerRepository, rules, converter  ) {
			@Override
			DefaultRulesEngine rulesEngine() {
				return rulesEngine;
			}

			@Override
			CatchExceptionRuleListener ruleListener() {
				return ruleListener;
			}};
	}
	
	@Test
	void createPartnersMarriagePartnerDeath() {
		IdMapping mapping = IdMappingBuilder.builder().withMandator(MANDATOR).withMarriagePartner().build();
		final StammImpl stamm = StammBuilder.builder().build();
		final Ehegatte ehegatte = EhegatteBuilder.builder().withBeihilfenr(mapping.getBeihilfenr()).withSterbeDatum().build();
		Mockito.doReturn(Arrays.asList(mapping)).when(idMappingRepository).findAll();
		Mockito.doReturn(stamm).when(stammRepository).findStamm(mapping.getBeihilfenr());
		final Map<Long, Date> beginDates = new HashMap<>();
		beginDates.put(mapping.getBeihilfenr(), contractDate);
		Mockito.doReturn(Arrays.asList(ehegatte)).when(stammRepository).findAllEhegatte();
		
		Mockito.doReturn(beginDates).when(stammRepository).beginDates();
		
		partnerFamilyService.createPartners(MANDATOR);
		
		final List<PartnerCore> partners = partnerRepository.findPartnerHists(mapping.getMarriagePartnerNr());
		assertEquals(2, partners.size());
		
		assertFirstPartner(mapping, mapping.getMarriagePartnerNr(), partners.get(0));
		assertTerminatedPartner(mapping, mapping.getMarriagePartnerNr(), ehegatte.getSterbedatum(), partners.get(1));
		
		final List<PartnersRole> roles = partnerRepository.findPartnersRoleHists(mapping.getMarriagePartnerNr());
		assertEquals(2, roles.size());
		
		assertFirstRole(mapping,mapping.getMarriagePartnerNr(), roles.get(0));
		
		assertTerminatedRole(mapping, mapping.getMarriagePartnerNr(), ehegatte.getSterbedatum(), roles.get(1));
	}

	private void assertTerminatedRole(IdMapping mapping,final String partnerNr, final long dateOfDeath, final PartnersRole terminatedRole) {
		assertSameDay(TestUtil.nextDay(TestUtil.toDate(dateOfDeath)), terminatedRole.getInd());
		assertEquals("IP", terminatedRole.getRole());
		assertSameDay(TestUtil.toDate(dateOfDeath), terminatedRole.getDop());
		assertEquals(Long.valueOf(2), terminatedRole.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), terminatedRole.getProcessnr());
		assertEqualsRequired(partnerNr, terminatedRole.getRightSide());
		assertEquals(Long.valueOf(1), terminatedRole.getTerminationflag());
	}

	private void assertFirstRole(IdMapping mapping,final String partnersNumber, final PartnersRole firstRole) {
		assertEquals("IP", firstRole.getRole());
		assertSameDay(contractDate, firstRole.getInd());
		assertSameDay(contractDate, firstRole.getDop());
		assertEquals(Long.valueOf(1), firstRole.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), firstRole.getProcessnr());
		assertEqualsRequired(partnersNumber, firstRole.getRightSide());
		assertEquals(Long.valueOf(0), firstRole.getTerminationflag());
	}

	private void assertTerminatedPartner(IdMapping mapping,final String partnersNumber, final long dateOfDeath, final PartnerCore terminatedPartnerCore) {
		assertSameDay(TestUtil.nextDay(TestUtil.toDate(dateOfDeath)), terminatedPartnerCore.getInd());
		assertSameDay(TestUtil.toDate(dateOfDeath), terminatedPartnerCore.getDop());
		assertEquals(Long.valueOf(2), terminatedPartnerCore.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), terminatedPartnerCore.getProcessnr());
		assertEqualsRequired(partnersNumber, terminatedPartnerCore.getPartnersNr());
		assertEquals(Long.valueOf(1), terminatedPartnerCore.getTerminationflag());
		assertSameDay(TestUtil.toDate(dateOfDeath),terminatedPartnerCore.getDateOfDeath());
	}

	private void assertFirstPartner(IdMapping mapping, final String partnerNumber,final PartnerCore firstPartnerCore) {
		assertSameDay(contractDate, firstPartnerCore.getInd());
		assertSameDay(contractDate, firstPartnerCore.getDop());
		assertEquals(Long.valueOf(1), firstPartnerCore.getHistnr());
		assertEqualsRequired(mapping.getProcessNumber(), firstPartnerCore.getProcessnr());
		assertEqualsRequired(partnerNumber, firstPartnerCore.getPartnersNr());
		assertEquals(Long.valueOf(0), firstPartnerCore.getTerminationflag());
		assertNull(firstPartnerCore.getDateOfDeath());
	}
	
	@Test
	void createPartnersChildDeath() {
		IdMapping mapping = IdMappingBuilder.builder().withMandator(MANDATOR).withChildren(1).build();
		final StammImpl stamm = StammBuilder.builder().build();
		final KindInfo kindInfo = new KindInfo();
		kindInfo.setVorname("Kylie");
		kindInfo.setName("Minogue");
		kindInfo.setLfdKind(mapping.getChildrenNr()[0]);
		kindInfo.setBeihilfenr(mapping.getBeihilfenr());
		kindInfo.setSterbedatum(19991111L);
		
		Mockito.doReturn(Arrays.asList(mapping)).when(idMappingRepository).findAll();
		Mockito.doReturn(stamm).when(stammRepository).findStamm(mapping.getBeihilfenr());
		Mockito.doReturn(Arrays.asList(kindInfo)).when(stammRepository).findChildren(mapping.getBeihilfenr(), mapping.getChildrenNr());
		final Map<Long, Date> beginDates = new HashMap<>();
		beginDates.put(mapping.getBeihilfenr(), contractDate);
		Mockito.doReturn(beginDates).when(stammRepository).beginDates();
		
		partnerFamilyService.createPartners(MANDATOR);
		
		final List<PartnerCore> partners = partnerRepository.findPartnerHists(mapping.getChildrenPartnerNr()[0]);
		assertEquals(2, partners.size());
		assertFirstPartner(mapping, mapping.getChildrenPartnerNr()[0],  partners.get(0));
		assertTerminatedPartner(mapping, mapping.getChildrenPartnerNr()[0], kindInfo.getSterbedatum(), partners.get(1));
		
		final List<PartnersRole> partnersRoles = partnerRepository.findPartnersRoleHists(mapping.getChildrenPartnerNr()[0]);
		assertEquals(2, partnersRoles.size());
		assertFirstRole(mapping, mapping.getChildrenPartnerNr()[0], partnersRoles.get(0));
		assertTerminatedRole(mapping, mapping.getChildrenPartnerNr()[0], kindInfo.getSterbedatum(), partnersRoles.get(1));
	}

	
}
