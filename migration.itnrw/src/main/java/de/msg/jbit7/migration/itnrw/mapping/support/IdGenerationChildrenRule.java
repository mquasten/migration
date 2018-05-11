package de.msg.jbit7.migration.itnrw.mapping.support;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import de.msg.jbit7.migration.itnrw.mapping.IdGenerationFacts;
import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;

@Rule(name="KindInfo")
public class IdGenerationChildrenRule {
	
	@Condition
	 public boolean existing(@Fact(IdGenerationFacts.CHILDREN) final Collection<KindInfo> children)  {
		
		return children.size() > 0 ; 
	}
	
	@Action(order=0)
	 public void assignValues(@Fact(IdGenerationFacts.ID_MAPPING) IdMapping idMapping,  @Fact(IdGenerationFacts.COUNTERS) final Counters counters,   @Fact(IdGenerationFacts.CHILDREN) final Collection<KindInfo> children) {
		final List<KindInfo> activeChildren = children.stream().sorted((c1, c2 ) -> (int) Math.signum(c1.getLfdKind().intValue() -c2.getLfdKind().intValue())).collect(Collectors.toList());
		final Long[] activeChildNumbers = new Long[activeChildren.size()];
		final String[] partnerNumbers = new String[activeChildren.size()];
		
		IntStream.range(0, activeChildren.size()).forEach(i -> {
			partnerNumbers[i]=counters.nextPartnerNumber();
			activeChildNumbers[i]=activeChildren.get(i).getLfdKind();
			
		});
		
		idMapping.setChildrenPartnerNr(partnerNumbers);
		idMapping.setChildrenNr(activeChildNumbers);
		
	}

}
