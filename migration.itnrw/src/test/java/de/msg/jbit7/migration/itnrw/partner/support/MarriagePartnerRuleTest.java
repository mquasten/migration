package de.msg.jbit7.migration.itnrw.partner.support;

import static de.msg.jbit7.migration.itnrw.util.TestUtil.assertEqualsRequired;
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
import de.msg.jbit7.migration.itnrw.mapping.IdMappingBuilder;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;


public class MarriagePartnerRuleTest {
	
	
	private final Ehegatte ehegatte = new Ehegatte();
	private final  Date contractDate = date(1831, 6, 13);
	private final IdMapping idMapping = IdMappingBuilder.builder().withMarriagePartner().build();
	private final StammImpl stamm = new StammImpl();
	private final DefaultConversionService conversionService = new DefaultConversionService();
	private final PartnerFactory partnerFactory = new PartnerFactory();
	private final MarriagePartnerRule marriagePartnerRule = new MarriagePartnerRule(partnerFactory, conversionService);
	@BeforeEach
	void setup() {
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
		
		assertEquals(2, results.size());
		
		final PartnerCore partnerCore = (PartnerCore) results.get(0);
		
		assertEqualsRequired(idMapping.getMandator(), partnerCore.getMandator());
		assertEqualsRequired("0", partnerCore.getDatastate());
		assertEqualsRequired(idMapping.getProcessNumber(), partnerCore.getProcessnr());
		assertEqualsRequired(Long.valueOf(1), partnerCore.getHistnr());
		assertEqualsRequired(contractDate, partnerCore.getDop());
		assertEqualsRequired(contractDate, partnerCore.getInd());
		assertNull(partnerCore.getDor());
		assertEqualsRequired(Long.valueOf(0L), partnerCore.getTerminationflag());
		assertEqualsRequired(idMapping.getMarriagePartnerNr(), partnerCore.getPartnersNr());
		assertEqualsRequired(Long.valueOf(1L), partnerCore.getLegalPerson());
		assertEqualsRequired(ehegatte.getVornameEhe(), partnerCore.getFirstName());
		assertEqualsRequired(stamm.getName(), partnerCore.getSecondName());
		assertEqualsRequired(contractDate, partnerCore.getDateOfBirth());
		assertEqualsRequired(Long.valueOf(0L), partnerCore.getSex());
		assertEqualsRequired("DE", partnerCore.getNationality());
		assertNull(partnerCore.getNationality2());
		assertNull(partnerCore.getNationality3());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getProfession());
		assertEqualsRequired(PartnerRule.UNDEFINED, partnerCore.getActivityState());
	
		assertEqualsRequired("de", partnerCore.getLanguageCorrespondence());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getVipFlag());
		assertEqualsRequired(Long.valueOf(0l), partnerCore.getPartnerState());
		assertNull(partnerCore.getNotice());
		assertNull(partnerCore.getNameAddition());
		assertNull(partnerCore.getNameAddition2());
		assertNull(partnerCore.getNameAddition3());
		assertNull(partnerCore.getDefaultAddress());
		assertNull(partnerCore.getDefaultBank());
		assertNull(partnerCore.getDefaultCommunication());
		assertEqualsRequired(Long.valueOf(2), partnerCore.getMaritalStatus());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getPlaceOfBirth());
	
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getExtCustomerNumber());
		assertEqualsRequired(Long.valueOf(0L), partnerCore.getNumberChildren());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getAdvertising());
		assertNull(partnerCore.getReasonForChange());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getEmployer());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getSalutation());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getHealthInsuranceNumber());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getCitizenNumber());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getIdDocumentType());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getIdDocumentNr());
		assertNull(partnerCore.getIdDocumentIssuedDate());
		assertNull(partnerCore.getIdDocumentExpiryDate());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getIdDocumentAuthority());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getIdDocumentAuthorityCountry());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getTenant());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getBasicType());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getFirstSecondaryType());
		assertNull(partnerCore.getCciNumber());
		assertNull(partnerCore.getSector());
		assertNull(partnerCore.getDenomination());
		assertEquals(PartnerFactory.BLANK, partnerCore.getPersonnelNr());
		assertEquals(idMapping.getMigrationUser(), partnerCore.getUserid());
		
		assertNull(partnerCore.getManagement());
		assertNull(partnerCore.getCancellation());	
		assertNull(partnerCore.getCancellationDate());	
		assertEquals(Long.valueOf(0), partnerCore.getDispatchType());
		assertNull(partnerCore.getDateOfDeath());
		assertNull(partnerCore.getTitleOfNobility());
		assertNull(partnerCore.getHonoraryTitle());
		assertNull(partnerCore.getNamePrefix());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getPepFlag());
		assertEqualsRequired(Long.valueOf(0L), partnerCore.getEuSanctionFlag());
		
		
		
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getTitle());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEqualsRequired(Long.valueOf(0), partnerCore.getSecondSecondaryType());
		
		assertEqualsRequired(PartnerFactory.BLANK, partnerCore.getBirthName());
		
		final PartnersRole partnersRole = (PartnersRole) results.get(1);
		
		assertEqualsRequired(idMapping.getMandator(), partnersRole.getMandator());
		assertEqualsRequired("0", partnersRole.getDatastate());
		assertEqualsRequired(idMapping.getProcessNumber(), partnersRole.getProcessnr());
		assertEqualsRequired(Long.valueOf(1), partnerCore.getHistnr());
		assertNull(partnersRole.getRprocessnr());
		assertEqualsRequired(contractDate, partnersRole.getDop());
		assertNull(partnersRole.getDor());
		assertEqualsRequired(contractDate, partnersRole.getInd());
		assertEqualsRequired(Long.valueOf(0L), partnersRole.getTerminationflag());
		assertEqualsRequired("IP", partnersRole.getRole());
		assertEqualsRequired(Long.valueOf(1), partnersRole.getOrderNrRole());
		
		assertEqualsRequired(""+idMapping.getContractNumber(), partnersRole.getLeftSide());
		assertEqualsRequired("2", partnersRole.getOrderNrLeftSide());
		assertEqualsRequired(idMapping.getMarriagePartnerNr(), partnersRole.getRightSide());
		assertNull(partnersRole.getAddressNr());
		assertNull(partnersRole.getBankNr());
		assertNull(partnersRole.getCommunicationRoleKey());
		assertEqualsRequired("BB" + idMapping.getContractNumber(), partnersRole.getExternKey());
		assertEqualsRequired(Long.valueOf(1L), partnersRole.getRoleState());
		assertEqualsRequired(Long.valueOf(1L),  partnersRole.getRiskCarrier());
	}

	
	private static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
}
