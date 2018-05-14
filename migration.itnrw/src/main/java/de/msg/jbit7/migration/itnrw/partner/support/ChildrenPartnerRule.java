package de.msg.jbit7.migration.itnrw.partner.support;



import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFamilyFacts;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "childrenPartner", priority = 0)
public class ChildrenPartnerRule {
	
	private final ConversionService conversionService;
	private final PartnerFactory partnerFactory;
	public ChildrenPartnerRule(ConversionService conversionService, PartnerFactory partnerFactory) {
		this.conversionService = conversionService;
		this.partnerFactory = partnerFactory;
	}

	
	
	@Condition
	public boolean evaluate(@Fact(PartnerFamilyFacts.ID_MAPPING) IdMapping idMapping) {
		return idMapping.getChildrenNr().length>0;
	}
	
	@Action(order = 1)
	public final void assignNewPartner(@Fact(PartnerFamilyFacts.ID_MAPPING) IdMapping idMapping,
		
			@Fact(PartnerFamilyFacts.CHILDREN) List<KindInfo> kindInfos ,
			@Fact(PartnerFamilyFacts.STAMM) StammImpl stamm ,
			@Fact(PartnerFamilyFacts.CONTRACT_DATE) Date contractDate ,
			@Fact(PartnerFamilyFacts.RESULTS) Collection<Object> results) {
		
			IntStream.range(0, kindInfos.size()).forEach(i -> {
				
				
				final KindInfo kindInfo = kindInfos.get(i);
				final PartnerCore newPartnerCore = assignChild(idMapping, kindInfo, stamm, contractDate, i);
				
				
				results.add(newPartnerCore);
				
				
			
				
				
			});
		
		
	}
	
	@Action(order = 2)
	public final void assignNewPartnersRole(@Fact(PartnerFamilyFacts.ID_MAPPING) IdMapping idMapping,
		
			@Fact(PartnerFamilyFacts.CHILDREN) List<KindInfo> kindInfos ,
			@Fact(PartnerFamilyFacts.CONTRACT_DATE) Date contractDate ,
			@Fact(PartnerFamilyFacts.RESULTS) Collection<Object> results) {
		
			IntStream.range(0, kindInfos.size()).forEach(i -> results.add(assignChildRole(idMapping, kindInfos.get(i), contractDate, i)));
		
		
	}



	private PartnersRole assignChildRole(final IdMapping idMapping, final KindInfo kindInfo, final Date contractDate, final int i) {
		final PartnersRole partnersRole = partnerFactory.newPartnersRole();
		partnersRole.setMandator(idMapping.getMandator());
		partnersRole.setProcessnr(idMapping.getProcessNumber());
		partnersRole.setDop(contractDate);
		partnersRole.setInd(contractDate);
		partnersRole.setRole("IP");
		partnersRole.setLeftSide(conversionService.convert(idMapping.getContractNumber(), String.class));
		partnersRole.setOrderNrLeftSide(conversionService.convert(StringUtils.hasText(idMapping.getMarriagePartnerNr()) ? 3+i: 2+i, String.class));
		Assert.isTrue(idMapping.getChildrenNr().length > i,String.format("Child %s with index doesn't exist.", i));
		partnersRole.setRightSide(idMapping.getChildrenPartnerNr()[i]);
		partnersRole.setExternKey("" + idMapping.getBeihilfenr());
		return partnersRole;
	}



	private PartnerCore assignChild(final IdMapping idMapping,final KindInfo kindInfo , final StammImpl stamm, final Date contractDate, final int index) {
		final PartnerCore partnerCore = partnerFactory.newPartnerCore();
		partnerCore.setMandator(idMapping.getMandator());
		partnerCore.setProcessnr(idMapping.getProcessNumber());
		partnerCore.setDop(contractDate);
		partnerCore.setInd(contractDate);
		Assert.isTrue(idMapping.getChildrenNr().length > index,String.format("Child %s with index doesn't exist.", index));
		partnerCore.setPartnersNr(idMapping.getChildrenPartnerNr()[index]);
		partnerCore.setFirstName(kindInfo.getVorname());
		partnerCore.setSecondName(StringUtils.hasText(kindInfo.getName()) ? kindInfo.getName(): stamm.getName());
		partnerCore.setDateOfBirth(conversionService.convert(kindInfo.getGebDatum(), Date.class));
		partnerCore.setUserid(idMapping.getMigrationUser());
		
		return partnerCore;
	}

}
