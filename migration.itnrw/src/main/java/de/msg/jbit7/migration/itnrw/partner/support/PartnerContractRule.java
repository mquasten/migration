package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.Date;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerFacts;


@Rule(name = "partnerContract", priority=0)
public class PartnerContractRule {
	
	private static final String BLANK = " ";
	final static Logger LOGGER = LoggerFactory.getLogger(PartnerContractRule.class);
	
	@Condition
	 public boolean evaluate() {
		return true;
	}
	
	
	
	
	@Action(order=1)
	public final void assignNewContract( @Fact(PartnerFacts.ID_MAPPING)  final IdMapping idMapping,  @Fact(PartnerFacts.CONTRACT_DATE)  final Date contractDate, @Fact(PartnerFacts.CONTRACT)  final PMContract contract) {
	
		
		
		contract.setBeginOfContract(contractDate);
		if( idMapping.getCollectiveContractNumbers().length > 0) {
			contract.setCollectiveContractNumber(idMapping.getCollectiveContractNumbers()[0]);
		} else {
			LOGGER.warn("BeihildeNr: " + idMapping.getBeihilfenr() + " didn't have a CollectiveContractNumber.");
		
		}
		
		contract.setContractNumber(idMapping.getContractNumber());
		contract.setContractType(5L);
		contract.setDatastate("0");
		contract.setDop(contractDate);

		contract.setDor(null);
		contract.setHistnr(1L);
		contract.setInd(contractDate);
		contract.setInternalNumberCollContract(null);
		contract.setMandator(idMapping.getMandator());
		contract.setMemberOfStaff(0L);
		contract.setPolicyConfirmationFlag(0L);
		contract.setPolicyNumber("BB" + idMapping.getContractNumber());
		contract.setPostingText1(BLANK);
		contract.setPostingText2(BLANK);
		contract.setPostingText3(BLANK);
		contract.setPrionr(800L);
		contract.setProcessnr(idMapping.getProcessNumber());
		contract.setReasonForChange(100L);
		contract.setRiskCarrier(1L);
		contract.setRprocessnr(null);
		contract.setTerminationDate(null);
		contract.setTerminationflag(0L);
		
	}

}
