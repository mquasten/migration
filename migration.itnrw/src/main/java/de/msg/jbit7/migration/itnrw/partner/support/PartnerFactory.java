package de.msg.jbit7.migration.itnrw.partner.support;

import org.springframework.beans.BeanUtils;

import de.msg.jbit7.migration.itnrw.partner.Address;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;

class PartnerFactory {

	static final String BLANK = " ";

	public final PartnerCore newPartnerCore() {
		final PartnerCore partnerCore = new PartnerCore();

		partnerCore.setDatastate("0");

		partnerCore.setHistnr(Long.valueOf(1L));

		partnerCore.setDor(null);

		partnerCore.setTerminationflag(Long.valueOf(0));

		partnerCore.setLegalPerson(Long.valueOf(1L));

		partnerCore.setSex(0L);
		partnerCore.setNationality("DE");
		partnerCore.setNationality2(null);
		partnerCore.setNationality3(null);
		partnerCore.setProfession(0L);
		partnerCore.setActivityState(PartnerRule.UNDEFINED);
		partnerCore.setTitle(null);
		partnerCore.setLanguageCorrespondence("de");
		partnerCore.setVipFlag(0L);
		partnerCore.setPartnerState(0L);
		partnerCore.setFirstNameCan("?");
		partnerCore.setFirstNamePhon("?");
		partnerCore.setSecondNameCan("?");
		partnerCore.setSecondNamePhon("?");
		partnerCore.setNotice(null);
		partnerCore.setNameAddition(null);
		partnerCore.setNameAddition2(null);
		partnerCore.setNameAddition3(null);
		partnerCore.setDefaultAddress(null);
		partnerCore.setDefaultBank(null);
		partnerCore.setDefaultCommunication(null);
		partnerCore.setMaritalStatus(0L);
		partnerCore.setSocialInsuranceNumber(null);
		partnerCore.setPlaceOfBirth(BLANK);
		partnerCore.setBirthName(BLANK);
		partnerCore.setExtCustomerNumber(BLANK);
		partnerCore.setNumberChildren(Long.valueOf(0));
		partnerCore.setAdvertising(0L);
		partnerCore.setReasonForChange(null);
		partnerCore.setEmployer(BLANK);
		partnerCore.setSalutation(0L);
		partnerCore.setHealthInsuranceNumber(BLANK);
		partnerCore.setCitizenNumber(BLANK);
		partnerCore.setIdDocumentType(0L);
		partnerCore.setIdDocumentNr(BLANK);
		partnerCore.setIdDocumentIssuedDate(null);
		partnerCore.setIdDocumentExpiryDate(null);
		partnerCore.setIdDocumentAuthority(BLANK);
		partnerCore.setIdDocumentAuthorityCountry(BLANK);
		partnerCore.setTenant(0L);
		partnerCore.setBasicType(0L);
		partnerCore.setFirstSecondaryType(0L);
		partnerCore.setCciNumber(null);
		partnerCore.setSector(null);
		partnerCore.setDenomination(null);
		partnerCore.setPersonnelNr(BLANK);
		partnerCore.setManagement(null);
		partnerCore.setCancellation(null);
		partnerCore.setCancellationDate(null);
		partnerCore.setDispatchType(0L);

		partnerCore.setTitleOfNobility(null);
		partnerCore.setHonoraryTitle(null);
		partnerCore.setNamePrefix(null);
		partnerCore.setPepFlag(0L);
		partnerCore.setEuSanctionFlag(0L);
		partnerCore.setTitle(BLANK);
		partnerCore.setSocialInsuranceNumber(BLANK);
		partnerCore.setSocialInsuranceNumberSp(BLANK);
		partnerCore.setSecondSecondaryType(0L);
		return partnerCore;
	}

	public final PartnersRole newPartnersRole() {
		final PartnersRole partnersRole = new PartnersRole();

		partnersRole.setDatastate("0");

		partnersRole.setHistnr(1L);
		partnersRole.setRprocessnr(null);

		partnersRole.setDor(null);

		partnersRole.setTerminationflag(0L);
		partnersRole.setOrderNrRole(1L);

		partnersRole.setOrderNrLeftSide("1");
		partnersRole.setRoleState(1L);
		partnersRole.setRiskCarrier(1L);
		return partnersRole;
	}

	public final PMContract newContract() {

		final PMContract contract = new PMContract();
		contract.setContractType(5L);
		contract.setDatastate("0");
		contract.setDor(null);
		contract.setHistnr(1L);
		contract.setInternalNumberCollContract(null);
		contract.setMemberOfStaff(0L);
		contract.setPolicyConfirmationFlag(0L);
		contract.setPostingText1(BLANK);
		contract.setPostingText2(BLANK);
		contract.setPostingText3(BLANK);
		contract.setPrionr(800L);
		contract.setReasonForChange(100L);
		contract.setRiskCarrier(1L);
		contract.setRprocessnr(null);
		contract.setTerminationDate(null);
		contract.setTerminationflag(0L);
		return contract;

	}

	public final Address newAddress() {
		final Address address = new Address();
		address.setAddressAddition1(PartnerFactory.BLANK);
		address.setAddressAddition2(PartnerFactory.BLANK);
		address.setAddressNr("1");
		address.setAddressState(0L);
		address.setAddressType(null);
		address.setCity2(PartnerFactory.BLANK);
		address.setCityCan("?");
		address.setCityPhon("?");
		address.setCoInformation(PartnerFactory.BLANK);
		address.setContact(PartnerFactory.BLANK);
		address.setDatastate("0");
		address.setHistnr(1L);
		address.setHouseNumber(PartnerFactory.BLANK);
		address.setHouseNumberAddition(null);
		address.setLatitude(null);
		address.setLongitude(null);
		address.setOutdated(0L);
		address.setPoBox(PartnerFactory.BLANK);
		address.setProximateTown(null);
		address.setReasonForChange(0L);
		address.setRprocessnr(null);
		address.setTerminationflag(0L);
		address.setValidationState(1L);
		return address;

	}

	public final <T> T copy(final T source) {
		@SuppressWarnings("unchecked")
		final T target = (T) BeanUtils.instantiateClass(source.getClass());
		BeanUtils.copyProperties(source, target);
		return target;
	}

}
