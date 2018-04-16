package de.msg.jbit7.migration.itnrw.mapping.support;

import java.util.Map;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import de.msg.jbit7.migration.itnrw.mapping.IdGenerationFacts;
import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
@Rule(name="Ehegatte", priority= 10)
public class IdGenerationMarriagePartnerRule {
	
	@Condition
	 public boolean alive(@Fact(IdGenerationFacts.OWNER) StammImpl owner ,  @Fact(IdGenerationFacts.MARRIAGE_PARTNERS) final Map<Long,Ehegatte> marriagePartners) {
		return marriagePartners.containsKey(owner.getBeihilfenr()) ;
	 }
	
	@Action(order=0)
	 public void assignValues(@Fact(IdGenerationFacts.ID_MAPPING) IdMapping idMapping,  @Fact(IdGenerationFacts.COUNTERS) final Counters counters) {
		idMapping.setMarriagePartnerNr(counters.nextPartnerNumber());
		
	}

}
