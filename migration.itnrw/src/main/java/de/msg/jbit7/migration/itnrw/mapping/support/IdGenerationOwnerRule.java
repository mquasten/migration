package de.msg.jbit7.migration.itnrw.mapping.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdGenerationFacts;
import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.HiAntragssteller;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "BeihilfeEmpfaenger", priority = Integer.MAX_VALUE)
public class IdGenerationOwnerRule {
	
	private static final String UNKOWN_STATE = "???";
	private ConversionService conversionService;

	
	
	@Autowired
	public IdGenerationOwnerRule(final ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Condition
	public boolean alive(@Fact(IdGenerationFacts.OWNER) final StammImpl owner) {
		return owner.getGeloescht() == 0L;

	}

	@Action(order = 0)
	public void assignValues(@Fact(IdGenerationFacts.OWNER) final StammImpl owner,@Fact(IdGenerationFacts.LAST_STATUS) final Optional<HiAntragssteller> hiAntragsteller, @Fact(IdGenerationFacts.ID_MAPPING) final IdMapping idMapping, @Fact(IdGenerationFacts.COUNTERS) final Counters counters) {
		idMapping.setLastState(lastState(hiAntragsteller));
		idMapping.setLastStateDate(lastDate(hiAntragsteller));
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

	private Date lastDate(final Optional<HiAntragssteller> hiAntragsteller) {
		final Date defaultBeginDate = conversionService.convert(19000101L, Date.class);
		if ( ! hiAntragsteller.isPresent() ) {
		    return defaultBeginDate;	
		}
		final Date date =  conversionService.convert(hiAntragsteller.get().getBeginnDatum(), Date.class);
		return date == null ? defaultBeginDate: date;
	}
	
	private String lastState(final Optional<HiAntragssteller> hiAntragsteller) {
		if ( ! hiAntragsteller.isPresent() ) {
		    return UNKOWN_STATE;	
		}
		
		return StringUtils.hasText(hiAntragsteller.get().getWertChar()) ? hiAntragsteller.get().getWertChar().toUpperCase() : UNKOWN_STATE;
	}

}
