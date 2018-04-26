package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.Collection;
import java.util.Date;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFamilyFacts;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "marriagePartner", priority = 0)
public class MarriagePartnerRule {
	
	private ConversionService conversionService;
	
	public MarriagePartnerRule(final ConversionService conversionService) {
		this.conversionService = conversionService;
	}
	
	@Condition
	public boolean evaluate(@Fact(PartnerFamilyFacts.ID_MAPPING) IdMapping idMapping) {
		return StringUtils.hasText(idMapping.getMarriagePartnerNr());
	}
	
	@Action(order = 1)
	public final void assignNewPartner(@Fact(PartnerFamilyFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFamilyFacts.STAMM) StammImpl stamm,
			@Fact(PartnerFamilyFacts.MARRIAGE_PARTNER) Ehegatte ehegatte ,
			@Fact(PartnerFamilyFacts.CONTRACT_DATE) Date contractDate ,
			@Fact(PartnerFamilyFacts.RESULTS) Collection<Object> results) {
	
		final PartnerCore partnerCore = new PartnerCore();
		partnerCore.setMandator(idMapping.getMandator());
		partnerCore.setDatastate("0");
		partnerCore.setProcessnr(idMapping.getProcessNumber());
		partnerCore.setHistnr(Long.valueOf(1L));
		partnerCore.setDop(contractDate);
		partnerCore.setDor(null);
		partnerCore.setInd(contractDate);
		partnerCore.setTerminationflag(Long.valueOf(0));
		partnerCore.setPartnersNr(idMapping.getMarriagePartnerNr());
		partnerCore.setLegalPerson(Long.valueOf(1L));
		partnerCore.setFirstName(ehegatte.getVornameEhe());
		partnerCore.setSecondName(StringUtils.hasText(ehegatte.getAbwName())? ehegatte.getAbwName() :stamm.getName());
		partnerCore.setDateOfBirth(conversionService.convert(ehegatte.getGebDatumEhe(), Date.class));
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
		partnerCore.setMaritalStatus(2L);
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
		partnerCore.setUserid(idMapping.getMigrationUser());
		partnerCore.setManagement(null);
		partnerCore.setCancellation(null);
		partnerCore.setCancellationDate(null);
		partnerCore.setDispatchType(0L);
		partnerCore.setDateOfDeath(conversionService.convert(ehegatte.getSterbedatum(), Date.class));
		partnerCore.setTitleOfNobility(null);
		partnerCore.setHonoraryTitle(null);
		partnerCore.setNamePrefix(null);
		partnerCore.setPepFlag(0L);
		partnerCore.setEuSanctionFlag(0L);
		partnerCore.setTitle(PartnerRule.BLANK);
		partnerCore.setSocialInsuranceNumber(PartnerRule.BLANK);
		partnerCore.setSocialInsuranceNumberSp(PartnerRule.BLANK);
		partnerCore.setSecondSecondaryType(0L);
		results.add(partnerCore);
		
	}

}