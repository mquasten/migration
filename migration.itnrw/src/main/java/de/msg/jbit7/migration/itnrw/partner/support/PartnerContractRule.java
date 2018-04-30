package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.Collection;
import java.util.Date;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerFacts;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "partnerContract", priority = 0)
public class PartnerContractRule {


	final static Logger LOGGER = LoggerFactory.getLogger(PartnerContractRule.class);
	
	
	private final ConversionService conversionService;
	
	private final PartnerFactory partnerFactory;
	
	public PartnerContractRule( final PartnerFactory partnerFactory, final ConversionService conversionService) {
		this.conversionService = conversionService;
		this.partnerFactory=partnerFactory;
	}

	
	
	

	@Condition
	public boolean evaluate() {
		return true;
	}

	@Action(order = 1)
	public final void assignNewContract(@Fact(PartnerFacts.ID_MAPPING) final IdMapping idMapping,
			@Fact(PartnerFacts.STAMM) final StammImpl stamm,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) final Collection<Object> results) {

		final PMContract contract = partnerFactory.newContract();
		contract.setBeginOfContract(contractDate);
		if (idMapping.getCollectiveContractNumbers().length > 0) {
			contract.setCollectiveContractNumber(idMapping.getCollectiveContractNumbers()[0]);
		} else {
			LOGGER.warn("BeihildeNr: " + idMapping.getBeihilfenr() + " didn't have a CollectiveContractNumber.");

		}

		contract.setContractNumber(idMapping.getContractNumber());
		contract.setDop(contractDate);
		contract.setInd(contractDate);
		contract.setMandator(idMapping.getMandator());
		contract.setPolicyNumber("BB" + idMapping.getContractNumber());
		contract.setProcessnr(idMapping.getProcessNumber());
		
		results.add(contract);
	}

}
