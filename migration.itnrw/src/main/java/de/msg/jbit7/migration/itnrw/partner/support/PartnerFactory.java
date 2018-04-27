package de.msg.jbit7.migration.itnrw.partner.support;

import de.msg.jbit7.migration.itnrw.partner.PartnerCore;

class PartnerFactory {
	public final PartnerCore newPartnerCore() {
		final PartnerCore partnerCore =  new PartnerCore();
		
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
		partnerCore.setPlaceOfBirth(PartnerRule.BLANK);
		partnerCore.setBirthName(PartnerRule.BLANK);
		partnerCore.setExtCustomerNumber(PartnerRule.BLANK);
		partnerCore.setNumberChildren(Long.valueOf(0));
		partnerCore.setAdvertising(0L);
		partnerCore.setReasonForChange(null);
		partnerCore.setEmployer(PartnerRule.BLANK);
		partnerCore.setSalutation(0L);
		partnerCore.setHealthInsuranceNumber(PartnerRule.BLANK);
		partnerCore.setCitizenNumber(PartnerRule.BLANK);
		partnerCore.setIdDocumentType(0L);
		partnerCore.setIdDocumentNr(PartnerRule.BLANK);
		partnerCore.setIdDocumentIssuedDate(null);
		partnerCore.setIdDocumentExpiryDate(null);
		partnerCore.setIdDocumentAuthority(PartnerRule.BLANK);
		partnerCore.setIdDocumentAuthorityCountry(PartnerRule.BLANK);
		partnerCore.setTenant(0L);
		partnerCore.setBasicType(0L);
		partnerCore.setFirstSecondaryType(0L);
		partnerCore.setCciNumber(null);
		partnerCore.setSector(null);
		partnerCore.setDenomination(null);
		partnerCore.setPersonnelNr(PartnerRule.BLANK);
		partnerCore.setManagement(null);
		partnerCore.setCancellation(null);
		partnerCore.setCancellationDate(null);
		partnerCore.setDispatchType(0L);
		
		partnerCore.setTitleOfNobility(null);
		partnerCore.setHonoraryTitle(null);
		partnerCore.setNamePrefix(null);
		partnerCore.setPepFlag(0L);
		partnerCore.setEuSanctionFlag(0L);
		partnerCore.setTitle(PartnerRule.BLANK);
		partnerCore.setSocialInsuranceNumber(PartnerRule.BLANK);
		partnerCore.setSocialInsuranceNumberSp(PartnerRule.BLANK);
		partnerCore.setSecondSecondaryType(0L);
		return partnerCore;
	}
	
}
