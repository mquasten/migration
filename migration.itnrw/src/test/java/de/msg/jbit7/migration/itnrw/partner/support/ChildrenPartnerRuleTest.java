package de.msg.jbit7.migration.itnrw.partner.support;

import static de.msg.jbit7.migration.itnrw.util.TestUtil.assertEqualsRequired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.StammBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;


class ChildrenPartnerRuleTest {

	private final KindInfo kindInfo = new KindInfo();
	private final StammImpl stamm = StammBuilder.builder().build();
	private final List<KindInfo> children = Arrays.asList(kindInfo);
	private final Date contractDate = date(1831, 6, 13);
	private final IdMapping idMapping = new IdMapping();

	private final DefaultConversionService conversionService = new DefaultConversionService();
	private final PartnerFactory partnerFactory = new PartnerFactory();

	private final ChildrenPartnerRule childrenPartnerRule = new ChildrenPartnerRule(conversionService, partnerFactory);

	private static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}

	@BeforeEach
	void setup() {
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
		idMapping.setChildrenNr(new Long[] { 1L });
		idMapping.setMandator(4711L);
		idMapping.setProcessNumber(67890L);
		idMapping.setChildrenPartnerNr(new String[] { "8490" });
		idMapping.setMigrationUser("MigUser");
		idMapping.setMarriagePartnerNr("73111");
		idMapping.setContractNumber(19680528L);
		kindInfo.setVorname("James Clerk");
		//stamm.setName("Maxwell");
		kindInfo.setGebDatum(18310613L);
		//kindInfo.setSterbedatum(18791105L);
	}

	@Test
	void evalualte() {
		assertTrue(childrenPartnerRule.evaluate(idMapping));
	}

	@Test
	void evalualteNoChildren() {
		idMapping.setChildrenNr(new Long[] {});
		assertFalse(childrenPartnerRule.evaluate(idMapping));
	}

	@Test
	void assignNewPartner() {
		final Collection<Object> results = new ArrayList<>();
		childrenPartnerRule.assignNewPartner(idMapping, children, stamm, contractDate, results);

		assertEquals(1, results.size());
		final PartnerCore partnerCore = (PartnerCore) results.iterator().next();
		assertPartnerCore(partnerCore);
		
	}

	private void assertPartnerCore(final PartnerCore partnerCore) {
		
		assertEqualsRequired(idMapping.getMandator(), partnerCore.getMandator());
		assertEquals("0", partnerCore.getDatastate());
		assertEqualsRequired(idMapping.getProcessNumber(), partnerCore.getProcessnr());
		assertNull(partnerCore.getRprocessnr());
		assertNull(partnerCore.getDor());
		assertEqualsRequired(idMapping.getChildrenPartnerNr()[0], partnerCore.getPartnersNr());
		assertEquals(Long.valueOf(1), partnerCore.getLegalPerson());
		assertEqualsRequired(kindInfo.getVorname(), partnerCore.getFirstName());
		assertEqualsRequired(stamm.getName(), partnerCore.getSecondName());
		assertEqualsRequired(contractDate, partnerCore.getDateOfBirth());
		assertEquals(Long.valueOf(0L), partnerCore.getSex());
		assertEquals("DE", partnerCore.getNationality());
		assertNull(partnerCore.getNationality2());
		assertNull(partnerCore.getNationality3());
		assertEquals(Long.valueOf(0), partnerCore.getProfession());
		assertEquals(PartnerRule.UNDEFINED, partnerCore.getActivityState());
		assertEquals(PartnerFactory.BLANK, partnerCore.getTitle());
		assertEquals("de", partnerCore.getLanguageCorrespondence());
		assertEquals(Long.valueOf(0), partnerCore.getVipFlag());
		assertEquals(Long.valueOf(0), partnerCore.getPartnerState());
		assertNull(partnerCore.getNotice());
		assertNull(partnerCore.getNameAddition());
		assertNull(partnerCore.getNameAddition2());
		assertNull(partnerCore.getDefaultAddress());
		assertNull(partnerCore.getDefaultBank());
		assertNull(partnerCore.getDefaultCommunication());
		assertEquals(Long.valueOf(0), partnerCore.getMaritalStatus());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEquals(PartnerFactory.BLANK, partnerCore.getPlaceOfBirth());
		assertEquals(PartnerFactory.BLANK, partnerCore.getBirthName());
		assertEquals(PartnerFactory.BLANK, partnerCore.getExtCustomerNumber());
		assertEquals(Long.valueOf(0), partnerCore.getNumberChildren());
		assertEquals(Long.valueOf(0), partnerCore.getAdvertising());
		assertEquals(PartnerFactory.BLANK, partnerCore.getEmployer());
		assertEquals(Long.valueOf(0), partnerCore.getSalutation());
		assertEquals(PartnerFactory.BLANK, partnerCore.getHealthInsuranceNumber());
		assertEquals(PartnerFactory.BLANK, partnerCore.getCitizenNumber());
		assertEquals(Long.valueOf(0), partnerCore.getIdDocumentType());
		assertEquals(PartnerFactory.BLANK, partnerCore.getIdDocumentNr());
		assertNull(partnerCore.getIdDocumentIssuedDate());
		assertNull(partnerCore.getIdDocumentExpiryDate());
		assertEquals(PartnerFactory.BLANK, partnerCore.getIdDocumentAuthority());
		assertEquals(PartnerFactory.BLANK, partnerCore.getIdDocumentAuthorityCountry());
		assertEquals(Long.valueOf(0), partnerCore.getTenant());
		assertEquals(Long.valueOf(0), partnerCore.getBasicType());
		assertEquals(Long.valueOf(0), partnerCore.getFirstSecondaryType());
		assertEquals(Long.valueOf(0), partnerCore.getSecondSecondaryType());
		assertNull(partnerCore.getCciNumber());
		assertNull(partnerCore.getSector());
		assertNull(partnerCore.getDenomination());
		assertEquals(PartnerFactory.BLANK, partnerCore.getPersonnelNr());
		assertEquals(idMapping.getMigrationUser(), partnerCore.getUserid());
		assertNull(partnerCore.getNameAddition3());
		assertNull(partnerCore.getManagement());
		assertNull(partnerCore.getCancellation());
		assertNull(partnerCore.getCancellationDate());
		assertEquals(Long.valueOf(0), partnerCore.getDispatchType());
		assertNull(partnerCore.getDateOfDeath()  );
		assertNull(partnerCore.getTitleOfNobility());
		assertNull(partnerCore.getHonoraryTitle());
		assertNull(partnerCore.getNamePrefix());
		assertEquals(Long.valueOf(0), partnerCore.getPepFlag());
		assertEquals(Long.valueOf(0), partnerCore.getEuSanctionFlag());
		
		
		assertEquals(Long.valueOf(1), partnerCore.getHistnr());
		assertEqualsRequired(contractDate, partnerCore.getDop());
		assertEqualsRequired(contractDate, partnerCore.getInd());
		assertEquals(Long.valueOf(0), partnerCore.getTerminationflag());
		assertNull(partnerCore.getReasonForChange());
	}
	
	@Test
	void assignNewPartnersRole() {
		final Collection<Object> results = new ArrayList<>();
		childrenPartnerRule.assignNewPartnersRole(idMapping, children,contractDate, results);
		
		assertEquals(1, results.size());
		final PartnersRole partnersRole = (PartnersRole) results.iterator().next();
		
		assertEqualsRequired(idMapping.getMandator(), partnersRole.getMandator());
		assertEquals("0", partnersRole.getDatastate());
		assertEqualsRequired(idMapping.getProcessNumber(), partnersRole.getProcessnr());
		assertEquals(Long.valueOf(1), partnersRole.getHistnr());
		assertNull(partnersRole.getRprocessnr());
		assertEqualsRequired(contractDate, partnersRole.getDop());
		assertNull(partnersRole.getDor());
		assertEqualsRequired(contractDate, partnersRole.getInd());
		assertEquals(Long.valueOf(0l), partnersRole.getTerminationflag());
		assertEquals("IP", partnersRole.getRole());
		assertEquals(Long.valueOf(1), partnersRole.getOrderNrRole());
		assertEqualsRequired(""+idMapping.getContractNumber(), partnersRole.getLeftSide());
		assertEquals("3", partnersRole.getOrderNrLeftSide());
		assertEqualsRequired(idMapping.getChildrenPartnerNr()[0], partnersRole.getRightSide());
		assertNull(partnersRole.getAddressNr());
		assertNull(partnersRole.getBankNr());
		assertNull(partnersRole.getCommunicationRoleKey());
		assertEqualsRequired("BB" + idMapping.getContractNumber(), partnersRole.getExternKey());
		assertEquals(Long.valueOf(1L), partnersRole.getRoleState());
		assertEquals(Long.valueOf(1L), partnersRole.getRiskCarrier());
	}
	
	
}
