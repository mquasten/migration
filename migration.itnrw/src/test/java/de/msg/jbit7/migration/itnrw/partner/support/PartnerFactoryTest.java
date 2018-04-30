package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.util.TestUtil;

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
		assertEquals(PartnerFactory.BLANK, partnerCore.getPlaceOfBirth());

		assertEquals(PartnerFactory.BLANK, partnerCore.getExtCustomerNumber());
		assertEquals(Long.valueOf(0L), partnerCore.getNumberChildren());
		assertEquals(Long.valueOf(0), partnerCore.getAdvertising());
		assertNull(partnerCore.getReasonForChange());
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
		assertNull(partnerCore.getCciNumber());
		assertNull(partnerCore.getSector());
		assertNull(partnerCore.getDenomination());
		assertEquals(PartnerFactory.BLANK, partnerCore.getPersonnelNr());

		assertNull(partnerCore.getManagement());
		assertNull(partnerCore.getCancellation());
		assertNull(partnerCore.getCancellationDate());
		assertEquals(Long.valueOf(0), partnerCore.getDispatchType());

		assertNull(partnerCore.getTitleOfNobility());
		assertNull(partnerCore.getHonoraryTitle());
		assertNull(partnerCore.getNamePrefix());
		assertEquals(Long.valueOf(0), partnerCore.getPepFlag());
		assertEquals(Long.valueOf(0L), partnerCore.getEuSanctionFlag());

		assertEquals(PartnerFactory.BLANK, partnerCore.getTitle());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEquals(PartnerFactory.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEquals(Long.valueOf(0), partnerCore.getSecondSecondaryType());

		assertEquals(PartnerFactory.BLANK, partnerCore.getBirthName());

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
	
	@Test
	void newContract() {
		final PMContract pmContract =  partnerFactory.newContract();
		assertEquals(Long.valueOf(5L), pmContract.getContractType());
		assertEquals("0", pmContract.getDatastate());
		assertNull(pmContract.getDor());
		assertEquals(Long.valueOf(1L), pmContract.getHistnr());
		assertNull(pmContract.getInternalNumberCollContract());
		assertEquals(Long.valueOf(0L), pmContract.getMemberOfStaff());
		assertEquals(Long.valueOf(0l), pmContract.getPolicyConfirmationFlag());
		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText1());
		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText2());
		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText2());
		assertEquals(Long.valueOf(800L), pmContract.getPrionr());
		assertEquals(Long.valueOf(100L), pmContract.getReasonForChange());
		assertEquals(Long.valueOf(1L), pmContract.getRiskCarrier());
		assertNull(pmContract.getRprocessnr());
		assertNull(pmContract.getTerminationDate());
		assertEquals(Long.valueOf(0L), pmContract.getTerminationflag());
	}
	
	@Test
	void copy() {
		final PMContract source = new PMContract();
		TestUtil.assignValuesToBean(source);
		final PMContract target = partnerFactory.copy(source);
		
		assertEqualsNotNull(source.getBeginOfContract(), target.getBeginOfContract());
		assertEqualsNotNull(source.getCollectiveContractNumber(), target.getCollectiveContractNumber());
		assertEqualsNotNull(source.getContractNumber(), target.getContractNumber());
		assertEqualsNotNull(source.getContractType(), target.getContractType());
		assertEqualsNotNull(source.getDatastate(), target.getDatastate());
		assertEqualsNotNull(source.getDop(), target.getDop());
		assertEqualsNotNull(source.getDor(), target.getDor());
		assertEqualsNotNull(source.getHistnr(), target.getHistnr());
		assertEqualsNotNull(source.getInd(), target.getInd());
		assertEqualsNotNull(source.getInternalNumberCollContract(), target.getInternalNumberCollContract());
		assertEqualsNotNull(source.getMandator(), target.getMandator());
		assertEqualsNotNull(source.getMemberOfStaff(), target.getMemberOfStaff());
		assertEqualsNotNull(source.getPolicyConfirmationFlag(), target.getPolicyConfirmationFlag());
		assertEqualsNotNull(source.getPolicyNumber(), target.getPolicyNumber());
		assertEqualsNotNull(source.getPostingText1(), target.getPostingText1());
		assertEqualsNotNull(source.getPostingText2(), target.getPostingText2());
		assertEqualsNotNull(source.getPostingText3(), target.getPostingText3());
		assertEqualsNotNull(source.getPrionr(), target.getPrionr());
		assertEqualsNotNull(source.getProcessnr(), target.getProcessnr());
		assertEqualsNotNull(source.getReasonForChange(), target.getReasonForChange());
		assertEqualsNotNull(source.getRiskCarrier(), target.getRiskCarrier());
		assertEqualsNotNull(source.getRprocessnr(), target.getRprocessnr());
		assertEqualsNotNull(source.getTerminationDate(), target.getTerminationDate());
		assertEqualsNotNull(source.getTerminationflag(), target.getTerminationflag());
	}
	
	
	
	void assertEqualsNotNull(final Object expected, final Object actual) {
		assertNotNull(expected);
		assertEquals(expected, actual);
	}

	
}
