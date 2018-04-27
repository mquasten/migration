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
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "marriagePartner", priority = 0)
public class MarriagePartnerRule {
	
	private final ConversionService conversionService;
	private final PartnerFactory partnerFactory;
	
	public MarriagePartnerRule(final PartnerFactory partnerFactory, final ConversionService conversionService) {
		this.partnerFactory=partnerFactory;
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
	
		final PartnerCore partnerCore = newPartnerCoreIP(idMapping, stamm, ehegatte, contractDate);
		
		final PartnersRole partnersRole = partnerFactory.newPartnersRole();
		
		partnersRole.setMandator(idMapping.getMandator());
		partnersRole.setProcessnr(idMapping.getProcessNumber());
		partnersRole.setDop(contractDate);
		partnersRole.setInd(contractDate);
		partnersRole.setRole("IP");
		partnersRole.setLeftSide(conversionService.convert(idMapping.getContractNumber(), String.class));
		partnersRole.setOrderNrLeftSide("2");
		partnersRole.setRightSide(idMapping.getMarriagePartnerNr());
		partnersRole.setExternKey("BB" + idMapping.getContractNumber());
		results.add(partnerCore);
		results.add(partnersRole);
		
		
	}

	private PartnerCore newPartnerCoreIP(IdMapping idMapping, StammImpl stamm, Ehegatte ehegatte, Date contractDate) {
		final PartnerCore partnerCore = partnerFactory.newPartnerCore();
		partnerCore.setMandator(idMapping.getMandator());
		partnerCore.setProcessnr(idMapping.getProcessNumber());
		partnerCore.setDop(contractDate);
		partnerCore.setInd(contractDate);
		partnerCore.setPartnersNr(idMapping.getMarriagePartnerNr());
		partnerCore.setFirstName(ehegatte.getVornameEhe());
		partnerCore.setSecondName(StringUtils.hasText(ehegatte.getAbwName())? ehegatte.getAbwName() :stamm.getName());
		partnerCore.setDateOfBirth(conversionService.convert(ehegatte.getGebDatumEhe(), Date.class));
		partnerCore.setMaritalStatus(2L);
		partnerCore.setUserid(idMapping.getMigrationUser());
		partnerCore.setDateOfDeath(conversionService.convert(ehegatte.getSterbedatum(), Date.class));
		return partnerCore;
	}

}