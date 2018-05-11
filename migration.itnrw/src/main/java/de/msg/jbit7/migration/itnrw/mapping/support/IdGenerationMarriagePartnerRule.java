package de.msg.jbit7.migration.itnrw.mapping.support;


import java.util.Optional;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import de.msg.jbit7.migration.itnrw.mapping.IdGenerationFacts;
import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
@Rule(name="Ehegatte", priority= 10)
public class IdGenerationMarriagePartnerRule {
	
	@Condition
	 public boolean married(@Fact(IdGenerationFacts.MARRIAGE_PARTNER) final Optional<Ehegatte> marriagePartners) {
		return marriagePartners.isPresent() ;
	 }
	
	@Action(order=0)
	 public void assignValues(@Fact(IdGenerationFacts.ID_MAPPING) IdMapping idMapping,  @Fact(IdGenerationFacts.COUNTERS) final Counters counters) {
		idMapping.setMarriagePartnerNr(counters.nextPartnerNumber());
		
	}

}
