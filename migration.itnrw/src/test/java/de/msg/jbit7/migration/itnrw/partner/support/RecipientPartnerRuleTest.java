package de.msg.jbit7.migration.itnrw.partner.support;

import static de.msg.jbit7.migration.itnrw.util.TestUtil.assertEqualsRequired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingBuilder;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.Drittempfaenger;
import de.msg.jbit7.migration.itnrw.stamm.DrittempfaengerBuilder;
import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class RecipientPartnerRuleTest {
	
	private final PartnerFactory partnerFactory = new PartnerFactory();
	
	private final RecipientPartnerRule recipientPartnerRule = new RecipientPartnerRule(partnerFactory);
	
	private final IdMapping mapping = IdMappingBuilder.builder().withRecipient().build();
	
	private final Drittempfaenger drittempfaenger = new DrittempfaengerBuilder().withVollmacht().withFemale().build();
	
	private final static Date contractDate = TestUtil.randomDate();
	
	@BeforeEach
	void setup() {
		
		
	}
	
	@Test
	final void evaluate() {
		assertTrue(recipientPartnerRule.evaluate(mapping));
	}
	
	@Test
	final void evaluateNoRecipient() {
		assertFalse(recipientPartnerRule.evaluate(IdMappingBuilder.builder().build()));
	}
	
	@Test
	final void assignPartner() {
		final Collection<Object> results = new ArrayList<>();
		recipientPartnerRule.assignPartner(mapping, Optional.of(drittempfaenger), contractDate, results);
		
		assertEquals(1, results.size());
		
		PartnerCore result = (PartnerCore) results.stream().findAny().get();
		
		
		
		assertNewPartner(result);
	}
	
	private void assertNewPartner(final PartnerCore partnerCore) {
		assertEquals(PartnerRule.UNDEFINED, partnerCore.getActivityState());
		assertEquals(Long.valueOf(0L), partnerCore.getAdvertising());
		assertEquals(Long.valueOf(0L), partnerCore.getBasicType());
	
		assertNull(partnerCore.getCancellation());
		assertNull(partnerCore.getCciNumber());
		assertEquals(PartnerFactory.BLANK, partnerCore.getCitizenNumber());
		assertEquals("0", partnerCore.getDatastate());
		
		
		
		assertEquals("1", partnerCore.getDefaultAddress());
		assertNull(partnerCore.getDefaultBank());
		assertNull(partnerCore.getDefaultCommunication());
		assertNull(partnerCore.getDenomination());
		assertEquals(Long.valueOf(0L), partnerCore.getDispatchType());
		
		assertEquals(PartnerFactory.BLANK, partnerCore.getEmployer());
		assertEquals(Long.valueOf(0L), partnerCore.getEuSanctionFlag());
		assertEquals(PartnerFactory.BLANK, partnerCore.getExtCustomerNumber());
	
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
		assertEqualsRequired(mapping.getMandator(), partnerCore.getMandator());
		assertEquals(Long.valueOf(0L), partnerCore.getMaritalStatus());
		
		assertNull(partnerCore.getNameAddition3());
		

		assertEquals("DE", partnerCore.getNationality());
		assertNull(partnerCore.getNationality2());
		assertNull(partnerCore.getNationality3());
		
		assertEquals(Long.valueOf(0), partnerCore.getNumberChildren());
		assertEqualsRequired(mapping.getRecipient(), partnerCore.getPartnersNr());
		assertEquals(Long.valueOf(0L), partnerCore.getPartnerState());
		assertEquals(Long.valueOf(0L), partnerCore.getPepFlag());
		assertEquals(PartnerFactory.BLANK, partnerCore.getPersonnelNr());
		assertEquals(PartnerFactory.BLANK, partnerCore.getPlaceOfBirth());
		assertEqualsRequired(mapping.getProcessNumber(), partnerCore.getProcessnr());
		assertEquals(Long.valueOf(0L), partnerCore.getProfession());	
		assertEquals(Long.valueOf(2L), partnerCore.getSalutation());
	
		assertEquals(Long.valueOf(0L), partnerCore.getSecondSecondaryType());
		assertNull(partnerCore.getSector());
		assertEquals(Long.valueOf(2L), partnerCore.getSex());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEquals(Long.valueOf(0L), partnerCore.getTenant());
		
		
		assertNull(partnerCore.getTitleOfNobility());
		assertEqualsRequired(mapping.getMigrationUser(), partnerCore.getUserid());
		assertEquals(Long.valueOf(0), partnerCore.getVipFlag());
		
		
		
		assertNull(partnerCore.getDateOfDeath());
		assertEquals(contractDate, partnerCore.getDop());
		assertEquals(Long.valueOf(1L), partnerCore.getHistnr());
		assertEquals(contractDate, partnerCore.getInd());
		assertNull(partnerCore.getReasonForChange());
		assertEquals(Long.valueOf(0L), partnerCore.getTerminationflag());
		
		
		assertEquals(PartnerFactory.BLANK, partnerCore.getBirthName());
		
		assertNull(partnerCore.getDateOfBirth());
		
		assertEqualsRequired(drittempfaenger.getVorname(), partnerCore.getFirstName());
		
		assertEqualsRequired(drittempfaenger.getZusatzname1(), partnerCore.getNameAddition());
		assertEqualsRequired(drittempfaenger.getZusatzname2(), partnerCore.getNameAddition2());
		
		assertNull(partnerCore.getNotice());
		
		assertEqualsRequired(drittempfaenger.getName(), partnerCore.getSecondName());
		
		assertEqualsRequired(drittempfaenger.getTitel(), partnerCore.getTitle()); 
	}
	
	private void assertPartnersRole(final PartnersRole partnersRole, final boolean isPaRole) {
		final String expectedRole = isPaRole ? "PA" : "VP";
		
		assertEquals(mapping.getMandator(), partnersRole.getMandator());
		assertEquals("0", partnersRole.getDatastate());
		assertEquals(mapping.getProcessNumber(), partnersRole.getProcessnr());
		assertEquals(Long.valueOf(1), partnersRole.getHistnr());
		assertNull(partnersRole.getRprocessnr());
		assertEquals(contractDate, partnersRole.getDop());
		assertNull(partnersRole.getDor());
		assertEquals(contractDate, partnersRole.getInd());
		assertEquals(Long.valueOf(0), partnersRole.getTerminationflag());
		assertEquals(expectedRole, partnersRole.getRole());
		assertEquals(Long.valueOf(1L), partnersRole.getOrderNrRole());
		assertEquals("" + mapping.getContractNumber(), partnersRole.getLeftSide());
		assertEquals("1", partnersRole.getOrderNrLeftSide());
		assertEquals(mapping.getRecipient(), partnersRole.getRightSide());
		if( isPaRole ) {
			assertEquals("1", partnersRole.getAddressNr());
		} else {
			assertNull( partnersRole.getAddressNr());
		}
		assertNull( partnersRole.getBankNr());
		assertNull(partnersRole.getCommunicationRoleKey());
		
		assertEquals("" + mapping.getBeihilfenr(), partnersRole.getExternKey());
		assertEquals(Long.valueOf(1), partnersRole.getRoleState());
		assertEquals(Long.valueOf(1), partnersRole.getRiskCarrier());
	}
	
	@Test
	final void assignPartnersPole() {
		final List<Object> results = new ArrayList<>();
		recipientPartnerRule.assignPartnerRole(mapping, Optional.of(drittempfaenger), contractDate, results);
		
		assertEquals(2, results.size());
		
		assertPartnersRole((PartnersRole) results.get(0), true);
		assertPartnersRole((PartnersRole) results.get(1), false);
	}	
	
	@Test
	final void assignPartnersPoleNoVp() {
		final List<Object> results = new ArrayList<>();
		drittempfaenger.setVollmacht("n");
		
		recipientPartnerRule.assignPartnerRole(mapping, Optional.of(drittempfaenger), contractDate, results);
		
		assertEquals(1, results.size());
		assertPartnersRole((PartnersRole) results.get(0), true);
	
	}

}
