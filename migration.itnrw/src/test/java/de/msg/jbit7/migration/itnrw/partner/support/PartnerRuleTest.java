package de.msg.jbit7.migration.itnrw.partner.support;

import static de.msg.jbit7.migration.itnrw.util.TestUtil.assertEqualsRequired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.IntrospectionException;
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
import org.springframework.dao.support.DataAccessUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingBuilder;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.Address;
import de.msg.jbit7.migration.itnrw.partner.Bank;
import de.msg.jbit7.migration.itnrw.partner.Communication;
import de.msg.jbit7.migration.itnrw.partner.CommunicationRole;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindungBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.util.TestUtil;

class PartnerRuleTest {

	

	private final IdMapping idMapping = IdMappingBuilder.builder().withMarriagePartner().withChildren(2).build();

	private final StammImpl stamm =  StammBuilder.builder().withFemaleGender().withBeihilfenr(idMapping.getBeihilfenr()).build();


	private final SepaBankVerbindung sepaBankVerbindung = SepaBankVerbindungBuilder.builder().build();
	private final static Date CONTRACT_DATE = date(1898, 12, 26);
	
	private final DefaultConversionService conversionService =  new DefaultConversionService();
	private final PartnerFactory partnerFactory = new PartnerFactory();
	private final PartnerRule partnerRule = new PartnerRule(partnerFactory,conversionService);
	@BeforeEach
	void setup() throws IntrospectionException {
	
	//	sepaBankVerbindung.setIban("12345DE12345");
	//	sepaBankVerbindung.setNameBank("EineBank");
	//	sepaBankVerbindung.setBic("BIC");
	//	sepaBankVerbindung.setIban("IBAN");
		
		
		
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
	}

	@Test
	void evaluate() {
		assertTrue(partnerRule.evaluate());
	}

	@Test
	void assignPartner() {

		final Collection<Object> results = new ArrayList<>();
		partnerRule.assignPartner(idMapping, stamm, Arrays.asList(sepaBankVerbindung), CONTRACT_DATE, results);

		final PartnerCore partnerCore = (PartnerCore) DataAccessUtils.requiredSingleResult(results);
		assertNewPartner(partnerCore);
		

	}

	private void assertNewPartner(final PartnerCore partnerCore) {
		assertEquals(PartnerRule.UNDEFINED, partnerCore.getActivityState());
		assertEquals(Long.valueOf(0L), partnerCore.getAdvertising());
		assertEquals(Long.valueOf(0L), partnerCore.getBasicType());
		assertEquals(stamm.getGeburtsname(), partnerCore.getBirthName());
		assertNull(partnerCore.getCancellation());
		assertNull(partnerCore.getCciNumber());
		assertEquals(PartnerFactory.BLANK, partnerCore.getCitizenNumber());
		assertEquals("0", partnerCore.getDatastate());
		
		assertEquals(TestUtil.toDate(stamm.getGebDatum()), partnerCore.getDateOfBirth());
		
		assertEquals("1", partnerCore.getDefaultAddress());
		assertEquals("1", partnerCore.getDefaultBank());
		assertEquals("1", partnerCore.getDefaultCommunication());
		assertNull(partnerCore.getDenomination());
		assertEquals(Long.valueOf(0L), partnerCore.getDispatchType());
		
		assertEquals(PartnerFactory.BLANK, partnerCore.getEmployer());
		assertEquals(Long.valueOf(0L), partnerCore.getEuSanctionFlag());
		assertEquals(PartnerFactory.BLANK, partnerCore.getExtCustomerNumber());
		assertEquals(stamm.getVorname(), partnerCore.getFirstName());
		assertEquals(Long.valueOf(0L), partnerCore.getFirstSecondaryType());
		assertEquals(PartnerFactory.BLANK, partnerCore.getHealthInsuranceNumber());
		
		assertNull(partnerCore.getHonoraryTitle());
		assertEquals(PartnerFactory.BLANK, partnerCore.getIdDocumentAuthority());
		assertEquals(PartnerFactory.BLANK, partnerCore.getIdDocumentAuthorityCountry());
		assertNull(partnerCore.getIdDocumentExpiryDate());
		assertNull(partnerCore.getIdDocumentIssuedDate());
		assertEquals(PartnerFactory.BLANK, partnerCore.getIdDocumentNr());
		assertEquals(Long.valueOf(0L), partnerCore.getIdDocumentType());
	
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
		assertEquals(PartnerFactory.BLANK, partnerCore.getPersonnelNr());
		assertEquals(PartnerFactory.BLANK, partnerCore.getPlaceOfBirth());
		assertEquals(idMapping.getProcessNumber(), partnerCore.getProcessnr());
		assertEquals(Long.valueOf(0L), partnerCore.getProfession());
		
		assertEquals(Long.valueOf(2L), partnerCore.getSalutation());
		assertEquals(stamm.getName(), partnerCore.getSecondName());
		assertEquals(Long.valueOf(0L), partnerCore.getSecondSecondaryType());
		assertNull(partnerCore.getSector());
		assertEquals(Long.valueOf(2L), partnerCore.getSex());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEquals(Long.valueOf(0L), partnerCore.getTenant());
		
		assertEquals(stamm.getTitel(), partnerCore.getTitle());
		assertNull(partnerCore.getTitleOfNobility());
		assertEquals(idMapping.getMigrationUser(), partnerCore.getUserid());
		assertEquals(Long.valueOf(0), partnerCore.getVipFlag());
		
		
		
		assertNull(partnerCore.getDateOfDeath());
		assertEquals(CONTRACT_DATE, partnerCore.getDop());
		assertEquals(Long.valueOf(1L), partnerCore.getHistnr());
		assertEquals(CONTRACT_DATE, partnerCore.getInd());
		assertNull(partnerCore.getReasonForChange());
		assertEquals(Long.valueOf(0L), partnerCore.getTerminationflag());
	}

	private static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
	
	@Test
	void assignNewAddress() {
		final Collection<Object> results = new ArrayList<>();
		partnerRule.assignNewAddress(idMapping, stamm, CONTRACT_DATE, results);
		
		final Address address = (Address) DataAccessUtils.requiredSingleResult(results);
		
		assertEquals(PartnerFactory.BLANK, address.getAddressAddition1());
		assertEquals(PartnerFactory.BLANK, address.getAddressAddition2());
		assertEquals("1", address.getAddressNr());
		assertEquals(Long.valueOf(0), address.getAddressState());
		assertNull(address.getAddressType());
		assertEquals(stamm.getOrt(), address.getCity1());
		
		assertEquals(PartnerFactory.BLANK, address.getCity2());
		assertEquals(PartnerFactory.BLANK, address.getCoInformation());
		assertEquals(PartnerFactory.BLANK, address.getContact());
		assertEquals("0", address.getDatastate());
		assertEquals(CONTRACT_DATE, address.getDop());
		assertEquals(Long.valueOf(1L), address.getHistnr());
		assertEquals(PartnerFactory.BLANK, address.getHouseNumber());
		assertNull(address.getHouseNumberAddition());
		assertEquals(CONTRACT_DATE,address.getInd());
		assertNull(address.getLatitude());
		assertNull(address.getLongitude());
		assertEquals(Long.valueOf(0l),address.getOutdated());
		assertEquals(idMapping.getPartnerNr(), address.getPartnersNr());
		assertEquals(PartnerFactory.BLANK, address.getPoBox());
		assertEquals(stamm.getPlz(), address.getPostcode());
		assertEquals(idMapping.getProcessNumber(), address.getProcessnr());
		
		assertEquals(Long.valueOf(0), address.getReasonForChange());
		assertEquals(stamm.getStrasseNr(), address.getStreet());
		assertEquals(Long.valueOf(0L), address.getTerminationflag());
		assertEquals(Long.valueOf(1L), address.getValidationState());
		assertEquals(idMapping.getMigrationUser(), address.getUserid());
	}
	
	
	@Test
	void assignNewBank() {
		final Collection<Object> results = new ArrayList<>();
		partnerRule.assignNewBank(idMapping, Arrays.asList(sepaBankVerbindung), CONTRACT_DATE, results);
		
		final Bank bank = (Bank) DataAccessUtils.requiredSingleResult(results);
		
		
	
		assertEquals(PartnerFactory.BLANK, bank.getAccountHolder());
		assertEquals(PartnerFactory.BLANK, bank.getAccountNumber());
		assertEquals(Long.valueOf(0), bank.getAccountType());
		assertEquals(PartnerFactory.BLANK, bank.getBankCode());
		assertEquals(PartnerFactory.BLANK, bank.getBankDistrict());
		
		
		assertEqualsRequired(sepaBankVerbindung.getNameBank(), bank.getBankName());
		assertEquals("1", bank.getBankNr());
		assertEquals(Long.valueOf(1L), bank.getBankState());
		assertEqualsRequired(sepaBankVerbindung.getBic(), bank.getBic());
		assertEquals(PartnerFactory.BLANK, bank.getCountry());
		
		assertEquals(PartnerFactory.BLANK, bank.getCreditCardCompany());
		assertNull(bank.getCreditCardExpiry());
		assertEquals(PartnerFactory.BLANK, bank.getCreditCardNumber());
		assertNull(bank.getCreditCardSecurityCode());
		assertEquals(PartnerFactory.BLANK, bank.getCreditCardType());
		assertNull(bank.getCurrencyOfAccount());
		assertEquals("0", bank.getDatastate());
		assertEquals(PartnerFactory.BLANK, bank.getDistrictBankCode());
		assertEquals(CONTRACT_DATE, bank.getDop());
		assertNull(bank.getDor());
		assertEquals(Long.valueOf(1), bank.getHistnr());
		assertEqualsRequired(sepaBankVerbindung.getIban(), bank.getIban());
		assertEquals(CONTRACT_DATE, bank.getInd());
		assertEquals(idMapping.getMandator(), bank.getMandator());
		assertEquals(Long.valueOf(0), bank.getOutdated());
		assertEquals(idMapping.getPartnerNr(), bank.getPartnersNr());
		assertNull(bank.getPostcode());
		assertEquals(idMapping.getProcessNumber(), bank.getProcessnr());
		assertNull(bank.getReasonForChange());
		assertNull(bank.getRprocessnr());
		assertEquals(Long.valueOf(0l), bank.getTerminationflag());
		assertNull(bank.getTown());
		assertEquals(idMapping.getMigrationUser(), bank.getUserid());
		results.add(bank);
	}
	
	
	@Test
	void assignNewCommunication() {
		final List<Object> results = new ArrayList<>();
		partnerRule.assignNewCommunication(idMapping, stamm, CONTRACT_DATE, results);
		
		
		assertEquals(3, results.size());
		
		final Communication resultEmailPrivate = (Communication) results.get(0) ; 
		assertEmailDefaults(resultEmailPrivate);
		assertEquals(Long.valueOf(3), resultEmailPrivate.getCommunicationType());
		assertEquals(stamm.getEmailPrivat(), resultEmailPrivate.getValue());
		assertEquals("1", resultEmailPrivate.getCommunicationNr());
		
		final Communication resultEmailOffice = (Communication) results.get(1) ; 
		assertEmailDefaults(resultEmailOffice);
		
		assertEquals(Long.valueOf(4), resultEmailOffice.getCommunicationType());
		assertEquals(stamm.getEmailDienst(), resultEmailOffice.getValue());
		assertEquals("2", resultEmailOffice.getCommunicationNr());
		
		final CommunicationRole communicationRole = (CommunicationRole) results.get(2);
		assertEquals(idMapping.getMandator(), communicationRole.getMandator());
		assertEquals("0", communicationRole.getDatastate());
		assertEquals(idMapping.getProcessNumber(), communicationRole.getProcessnr());
		assertEquals(idMapping.getPartnerNr(), "" + communicationRole.getCommunicationKey());
		assertEquals(idMapping.getPartnerNr(), "" + communicationRole.getCommunicationRoleKey());
		assertEquals("1", communicationRole.getCommunicationNr());
		
		
	}

	private void assertEmailDefaults(final Communication resultEmailPrivate) {
		assertEquals(idMapping.getMandator(), resultEmailPrivate.getMandator());
		assertEquals("0", resultEmailPrivate.getDatastate());
		assertEquals(idMapping.getProcessNumber(), resultEmailPrivate.getProcessnr());
		assertEquals(Long.valueOf(1), resultEmailPrivate.getHistnr());
		
		assertEquals(CONTRACT_DATE, resultEmailPrivate.getDop());
		assertNull(resultEmailPrivate.getDor());
		assertEquals(CONTRACT_DATE, resultEmailPrivate.getInd());
		assertEquals(Long.valueOf(0), resultEmailPrivate.getTerminationflag());
		assertEquals(idMapping.getPartnerNr(), resultEmailPrivate.getPartnersNr());
		
		
		assertNull(resultEmailPrivate.getReasonForChange());
		assertNull(resultEmailPrivate.getAvailability());
		assertEquals(idMapping.getMigrationUser(), resultEmailPrivate.getUserid());
		assertEquals(Long.valueOf(0), resultEmailPrivate.getOutdated());
		assertEquals(Long.valueOf(0), resultEmailPrivate.getUsageAgreement());
	}
	
	
	@Test
	void  assignNewPartnersRole() {
		final List<Object> results = new ArrayList<>();
		partnerRule.assignPartnersRole(idMapping, stamm, Arrays.asList(sepaBankVerbindung), CONTRACT_DATE, results);
		
		assertEquals(2, results.size());
		
		final PartnersRole partnersRolePH = (PartnersRole) results.get(0);
		assertPartnersRolePH(partnersRolePH);
		
		final PartnersRole partnersRoleIp = (PartnersRole) results.get(1);
		
		assertPartnersRoleIp(partnersRoleIp);
	}

	private void assertPartnersRoleIp(final PartnersRole partnersRoleIp) {
		assertEquals(idMapping.getMandator(), partnersRoleIp.getMandator());
		assertEquals("0", partnersRoleIp.getDatastate());
		assertEquals(idMapping.getProcessNumber(),  partnersRoleIp.getProcessnr());
		assertEquals(Long.valueOf(1), partnersRoleIp.getHistnr());
		assertNull(partnersRoleIp.getRprocessnr());
		assertEquals(CONTRACT_DATE, partnersRoleIp.getDop());
		assertNull(partnersRoleIp.getDor());
		assertEquals(CONTRACT_DATE, partnersRoleIp.getInd());
		assertEquals(Long.valueOf(0), partnersRoleIp.getTerminationflag());
		assertEquals("IP", partnersRoleIp.getRole());
		assertEquals(Long.valueOf(1L), partnersRoleIp.getOrderNrRole());
		assertEquals("" + idMapping.getContractNumber(), partnersRoleIp.getLeftSide());
		assertEquals("1", partnersRoleIp.getOrderNrLeftSide());
		assertEquals(idMapping.getPartnerNr(), partnersRoleIp.getRightSide());
		assertNull(partnersRoleIp.getAddressNr());
		assertNull(partnersRoleIp.getBankNr());
		
		assertNull(partnersRoleIp.getCommunicationRoleKey());
		assertEquals("BB" + idMapping.getContractNumber() , partnersRoleIp.getExternKey());
		assertEquals(Long.valueOf(1), partnersRoleIp.getRoleState());
		assertEquals(Long.valueOf(1), partnersRoleIp.getRiskCarrier());
	}

	private void assertPartnersRolePH(final PartnersRole partnersRole) {
		assertEquals(idMapping.getMandator(), partnersRole.getMandator());
		assertEquals("0", partnersRole.getDatastate());
		assertEquals(idMapping.getProcessNumber(), partnersRole.getProcessnr());
		assertEquals(Long.valueOf(1), partnersRole.getHistnr());
		assertNull(partnersRole.getRprocessnr());
		assertEquals(CONTRACT_DATE, partnersRole.getDop());
		assertNull(partnersRole.getDor());
		assertEquals(CONTRACT_DATE, partnersRole.getInd());
		assertEquals(Long.valueOf(0), partnersRole.getTerminationflag());
		assertEquals("PH", partnersRole.getRole());
		assertEquals(Long.valueOf(1L), partnersRole.getOrderNrRole());
		assertEquals("" + idMapping.getContractNumber(), partnersRole.getLeftSide());
		assertEquals("1", partnersRole.getOrderNrLeftSide());
		assertEquals(idMapping.getPartnerNr(), partnersRole.getRightSide());
		assertEquals("1", partnersRole.getAddressNr());
		assertEquals("1", partnersRole.getBankNr());
		assertEquals(idMapping.getPartnerNr(), "" +partnersRole.getCommunicationRoleKey());
		
		assertEquals("BB" + idMapping.getContractNumber() , partnersRole.getExternKey());
		assertEquals(Long.valueOf(1), partnersRole.getRoleState());
		assertEquals(Long.valueOf(1), partnersRole.getRiskCarrier());
	}
	
	
	@Test
	void assignPartnerDead() {
		stamm.setSterbedatum(19340704L);
		
		final List<Object> results = new ArrayList<>();
		partnerRule.assignPartner(idMapping, stamm, Arrays.asList(sepaBankVerbindung), CONTRACT_DATE, results);

		assertNewPartner((PartnerCore)  results.get(0));
		
		
	}

	
	
}
