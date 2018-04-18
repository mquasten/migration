package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.IntrospectionException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

class PartnerRuleTest {

	private final PartnerRule partnerRule = new PartnerRule();

	private final IdMapping idMapping = new IdMapping();

	private final StammImpl stamm = new StammImpl();

	private final PartnerCore partnerCore = new PartnerCore();

	private final SepaBankVerbindung sepaBankVerbindung = new SepaBankVerbindung();
	private final static Date CONTRACT_DATE = date(1898, 12, 26);

	@BeforeEach
	void setup() throws IntrospectionException {
		idMapping.setBeihilfenr(19680528L);
		idMapping.setPartnerNr("1001");
		idMapping.setProcessNumber(4001L);
		idMapping.setMandator(4711L);
		idMapping.setMarriagePartnerNr("1234");
		idMapping.setChildrenNr(new Long[] { 1L, 2L });

		stamm.setBeihilfenr(idMapping.getBeihilfenr());
		stamm.setGeburtsname("Skłodowska");
		stamm.setGebDatum(18671107L);
		stamm.setSterbedatum(19340704L);
		stamm.setVorname("Marie");
		stamm.setZusatz1Name("Dr. sc. nat. ");
		stamm.setGeschlecht("w");
		stamm.setEmailPrivat("marie.currie@234.net");
		sepaBankVerbindung.setIban("12345DE12345");
		stamm.setBemerkung("Nobellpreise für Physik + Chemie");
		stamm.setName("Currie");
		stamm.setTitel("Dr");
	}

	@Test
	void evaluate() {
		assertTrue(partnerRule.evaluate());
	}

	@Test
	void assignNewPartner() {

		partnerRule.assignNewPartner(idMapping, stamm, sepaBankVerbindung, CONTRACT_DATE, partnerCore);

		assertEquals(PartnerRule.UNDEFINED, partnerCore.getActivityState());
		assertEquals(Long.valueOf(0L), partnerCore.getAdvertising());
		assertEquals(Long.valueOf(0L), partnerCore.getBasicType());
		assertEquals(stamm.getGeburtsname(), partnerCore.getBirthName());
		assertNull(partnerCore.getCancellation());
		assertNull(partnerCore.getCciNumber());
		assertEquals(PartnerRule.BLANK, partnerCore.getCitizenNumber());
		assertEquals("0", partnerCore.getDatastate());
		assertEquals(date(1867, 11, 7), partnerCore.getDateOfBirth());
		assertEquals(date(1934, 7, 4), partnerCore.getDateOfDeath());
		assertEquals("1", partnerCore.getDefaultAddress());
		assertEquals("1", partnerCore.getDefaultBank());
		assertEquals("1", partnerCore.getDefaultCommunication());
		assertNull(partnerCore.getDenomination());
		assertEquals(Long.valueOf(0L), partnerCore.getDispatchType());
		assertEquals(CONTRACT_DATE, partnerCore.getDop());
		assertEquals(PartnerRule.BLANK, partnerCore.getEmployer());
		assertEquals(Long.valueOf(0L), partnerCore.getEuSanctionFlag());
		assertEquals(PartnerRule.BLANK, partnerCore.getExtCustomerNumber());
		assertEquals(stamm.getVorname(), partnerCore.getFirstName());
		assertEquals(Long.valueOf(0L), partnerCore.getFirstSecondaryType());
		assertEquals(PartnerRule.BLANK, partnerCore.getHealthInsuranceNumber());
		assertEquals(Long.valueOf(1L), partnerCore.getHistnr());
		assertNull(partnerCore.getHonoraryTitle());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentAuthority());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentAuthorityCountry());
		assertNull(partnerCore.getIdDocumentExpiryDate());
		assertNull(partnerCore.getIdDocumentIssuedDate());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentNr());
		assertEquals(Long.valueOf(0L), partnerCore.getIdDocumentType());
		assertEquals(CONTRACT_DATE, partnerCore.getInd());
		assertEquals("de", partnerCore.getLanguageCorrespondence());
		assertEquals(Long.valueOf(1L), partnerCore.getLegalPerson());
		assertNull(partnerCore.getManagement());
		assertEquals(idMapping.getMandator(), partnerCore.getMandator());
		assertEquals(Long.valueOf(2L), partnerCore.getMaritalStatus());
		assertNull(partnerCore.getNameAddition());
		assertNull(partnerCore.getNameAddition2());
		assertNull(partnerCore.getNameAddition3());
		assertEquals(stamm.getZusatz1Name(), partnerCore.getNamePrefix());

		assertEquals("DE", partnerCore.getNationality());
		assertNull(partnerCore.getNationality2());
		assertNull(partnerCore.getNationality3());
		assertEquals(stamm.getBemerkung(), partnerCore.getNotice());
		assertEquals(Long.valueOf(2), partnerCore.getNumberChildren());
		assertEquals(idMapping.getPartnerNr(), partnerCore.getPartnersNr());
		assertEquals(Long.valueOf(0L), partnerCore.getPartnerState());
		assertEquals(Long.valueOf(0L), partnerCore.getPepFlag());
		assertEquals(PartnerRule.BLANK, partnerCore.getPersonnelNr());
		assertEquals(PartnerRule.BLANK, partnerCore.getPlaceOfBirth());
		assertEquals(idMapping.getProcessNumber(), partnerCore.getProcessnr());
		assertEquals(Long.valueOf(0L), partnerCore.getProfession());
		assertNull(partnerCore.getReasonForChange());
		assertEquals(Long.valueOf(2L), partnerCore.getSalutation());
		assertEquals(stamm.getName(), partnerCore.getSecondName());
		assertEquals(Long.valueOf(0L), partnerCore.getSecondSecondaryType());
		assertNull(partnerCore.getSector());
		assertEquals(Long.valueOf(2L), partnerCore.getSex());
		assertEquals(PartnerRule.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEquals(PartnerRule.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEquals(Long.valueOf(0L), partnerCore.getTenant());
		assertEquals(Long.valueOf(0L), partnerCore.getTerminationflag());
		assertEquals(stamm.getTitel(), partnerCore.getTitle());
		assertNull(partnerCore.getTitleOfNobility());
		assertEquals("MigUser", partnerCore.getUserid());
		assertEquals(Long.valueOf(0), partnerCore.getVipFlag());

	}

	private static Date date(int year, int month, int day) {
		Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
}
