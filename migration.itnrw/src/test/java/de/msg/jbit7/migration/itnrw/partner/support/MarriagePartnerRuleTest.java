package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

public class MarriagePartnerRuleTest {
	
	
	private final Ehegatte ehegatte = new Ehegatte();
	private final  Date contractDate = date(1831, 6, 13);
	private final IdMapping idMapping = new IdMapping();
	private final StammImpl stamm = new StammImpl();
	private final DefaultConversionService conversionService = new DefaultConversionService();
	private final MarriagePartnerRule marriagePartnerRule = new MarriagePartnerRule(conversionService);
	@BeforeEach
	void setup() {
		idMapping.setMandator(4711L);
		idMapping.setMarriagePartnerNr("1234");
		idMapping.setProcessNumber(4711L);
		idMapping.setMarriagePartnerNr("7890");
		idMapping.setMigrationUser("MigUser");
		
		ehegatte.setVornameEhe("James Clerk");
		ehegatte.setGebDatumEhe(18310613L);
		
		ehegatte.setSterbedatum(18791105L);
		
		stamm.setName("Maxwell");
		
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
	}
	
	@Test
	final void evaluate() {
		assertTrue(marriagePartnerRule.evaluate(idMapping));
	}
	
	@Test
	final void assignNewPartner() {
		final List<Object> results = new ArrayList<>();
		marriagePartnerRule.assignNewPartner(idMapping, stamm, ehegatte, contractDate, results);
		
		final PartnerCore partnerCore = (PartnerCore) results.get(0);
		assertEquals(idMapping.getMandator(), partnerCore.getMandator());
		assertEquals(1, results.size());
		assertEquals("0", partnerCore.getDatastate());
		assertEquals(idMapping.getProcessNumber(), partnerCore.getProcessnr());
		assertEquals(Long.valueOf(1), partnerCore.getHistnr());
		assertEquals(contractDate, partnerCore.getDop());
		assertEquals(contractDate, partnerCore.getInd());
		assertNull(partnerCore.getDor());
		assertEquals(Long.valueOf(0L), partnerCore.getTerminationflag());
		assertEquals(idMapping.getMarriagePartnerNr(), partnerCore.getPartnersNr());
		assertEquals(Long.valueOf(1L), partnerCore.getLegalPerson());
		assertEquals(ehegatte.getVornameEhe(), partnerCore.getFirstName());
		assertEquals(stamm.getName(), partnerCore.getSecondName());
		assertEquals(contractDate, partnerCore.getDateOfBirth());
		assertEquals(Long.valueOf(0L), partnerCore.getSex());
		assertEquals("DE", partnerCore.getNationality());
		assertNull(partnerCore.getNationality2());
		assertNull(partnerCore.getNationality3());
		assertEquals(Long.valueOf(0), partnerCore.getProfession());
		assertEquals(PartnerRule.UNDEFINED, partnerCore.getActivityState());
	
		assertEquals("de", partnerCore.getLanguageCorrespondence());
		assertEquals(Long.valueOf(0), partnerCore.getVipFlag());
		assertEquals(Long.valueOf(0l), partnerCore.getPartnerState());
		assertNull(partnerCore.getNotice());
		assertNull(partnerCore.getNameAddition());
		assertNull(partnerCore.getNameAddition2());
		assertNull(partnerCore.getNameAddition3());
		assertNull(partnerCore.getDefaultAddress());
		assertNull(partnerCore.getDefaultBank());
		assertNull(partnerCore.getDefaultCommunication());
		assertEquals(Long.valueOf(2), partnerCore.getMaritalStatus());
		assertEquals(PartnerRule.BLANK, partnerCore.getPlaceOfBirth());
	
		assertEquals(PartnerRule.BLANK, partnerCore.getExtCustomerNumber());
		assertEquals(Long.valueOf(0L), partnerCore.getNumberChildren());
		assertEquals(Long.valueOf(0), partnerCore.getAdvertising());
		assertNull(partnerCore.getReasonForChange());
		assertEquals(PartnerRule.BLANK, partnerCore.getEmployer());
		assertEquals(Long.valueOf(0), partnerCore.getSalutation());
		assertEquals(PartnerRule.BLANK, partnerCore.getHealthInsuranceNumber());
		assertEquals(PartnerRule.BLANK, partnerCore.getCitizenNumber());
		assertEquals(Long.valueOf(0), partnerCore.getIdDocumentType());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentNr());
		assertNull(partnerCore.getIdDocumentIssuedDate());
		assertNull(partnerCore.getIdDocumentExpiryDate());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentAuthority());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentAuthorityCountry());
		assertEquals(Long.valueOf(0), partnerCore.getTenant());
		assertEquals(Long.valueOf(0), partnerCore.getBasicType());
		assertEquals(Long.valueOf(0), partnerCore.getFirstSecondaryType());
		assertNull(partnerCore.getCciNumber());
		assertNull(partnerCore.getSector());
		assertNull(partnerCore.getDenomination());
		assertEquals(PartnerRule.BLANK, partnerCore.getPersonnelNr());
		assertEquals(idMapping.getMigrationUser(), partnerCore.getUserid());
		
		assertNull(partnerCore.getManagement());
		assertNull(partnerCore.getCancellation());	
		assertNull(partnerCore.getCancellationDate());	
		assertEquals(Long.valueOf(0), partnerCore.getDispatchType());
		assertEquals(date(1879, 11, 5), partnerCore.getDateOfDeath());
		assertNull(partnerCore.getTitleOfNobility());
		assertNull(partnerCore.getHonoraryTitle());
		assertNull(partnerCore.getNamePrefix());
		assertEquals(Long.valueOf(0), partnerCore.getPepFlag());
		assertEquals(Long.valueOf(0L), partnerCore.getEuSanctionFlag());
		
		
		
		assertEquals(PartnerRule.BLANK, partnerCore.getTitle());
		assertEquals(PartnerRule.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEquals(PartnerRule.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEquals(Long.valueOf(0), partnerCore.getSecondSecondaryType());
		
		assertEquals(PartnerRule.BLANK, partnerCore.getBirthName());
		
	}

	
	private static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
}