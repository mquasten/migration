package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;

public class PartnerFactoryTest {

	private final PartnerFactory partnerFactory = new PartnerFactory();

	@Test
	void newPartnerCore() {

		final PartnerCore partnerCore = partnerFactory.newPartnerCore();

		assertEquals("0", partnerCore.getDatastate());

		assertEquals(Long.valueOf(1), partnerCore.getHistnr());

		assertNull(partnerCore.getDor());
		assertEquals(Long.valueOf(0L), partnerCore.getTerminationflag());

		assertEquals(Long.valueOf(1L), partnerCore.getLegalPerson());

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
		assertEquals(Long.valueOf(0), partnerCore.getMaritalStatus());
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

		assertNull(partnerCore.getManagement());
		assertNull(partnerCore.getCancellation());
		assertNull(partnerCore.getCancellationDate());
		assertEquals(Long.valueOf(0), partnerCore.getDispatchType());

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

	@Test
	void newPartnersRole() {

		final PartnersRole partnersRole = partnerFactory.newPartnersRole();

		assertEquals("0", partnersRole.getDatastate());

		assertEquals(Long.valueOf(1), partnersRole.getHistnr());
		assertNull(partnersRole.getRprocessnr());

		assertNull(partnersRole.getDor());

		assertEquals(Long.valueOf(0), partnersRole.getTerminationflag());

		assertEquals(Long.valueOf(1L), partnersRole.getOrderNrRole());

		assertEquals("1", partnersRole.getOrderNrLeftSide());

		assertNull(partnersRole.getAddressNr());
		assertNull(partnersRole.getBankNr());

		assertNull(partnersRole.getCommunicationRoleKey());

		assertEquals(Long.valueOf(1), partnersRole.getRoleState());
		assertEquals(Long.valueOf(1), partnersRole.getRiskCarrier());

	}
}