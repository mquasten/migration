package de.msg.jbit7.migration.itnrw.mapping.support;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdGenerationFacts;
import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "BeihilfeEmpfaenger", priority = Integer.MAX_VALUE)
public class IdGenerationOwnerRule {

	@Condition
	public boolean alive(@Fact(IdGenerationFacts.OWNER) final StammImpl owner) {
		return owner.getGeloescht() == 0L;

	}

	@Action(order = 0)
	public void assignValues(@Fact(IdGenerationFacts.OWNER) final StammImpl owner, @Fact(IdGenerationFacts.ID_MAPPING) final IdMapping idMapping, @Fact(IdGenerationFacts.COUNTERS) final Counters counters) {
		idMapping.setMandator(counters.mandator());
		idMapping.setBeihilfenr(owner.getBeihilfenr());
		idMapping.setPartnerNr(counters.nextPartnerNumber());
		idMapping.setProcessNumber(counters.nextProcessNumber());
		idMapping.setContractNumber(counters.nextContactNumber());
		final List<Long> ids = new ArrayList<>();
		if (StringUtils.hasText(owner.getSchulnummer())) {
			idMapping.setSchulnummer(owner.getSchulnummer());
			ids.add(counters.nextCollectiveContractNumberSchool(owner.getSchulnummer()));
		}

		if (StringUtils.hasText(owner.getDienststelle())) {
			idMapping.setDienststelle(owner.getDienststelle());
			ids.add(counters.nextCollectiveContractNumberOffice(owner.getDienststelle()));
		}

		idMapping.setCollectiveContractNumbers(ids.toArray(new Long[ids.size()]));

		idMapping.setDienststelle(owner.getDienststelle());
		idMapping.setSchulnummer(owner.getSchulnummer());

	}

}
