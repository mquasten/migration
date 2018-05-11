package de.msg.jbit7.migration.itnrw.mapping.support;

import java.time.LocalDate;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
import de.msg.jbit7.migration.itnrw.stamm.HiAntragsteller;
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
	public boolean migrationRequired(@Fact(IdGenerationFacts.OWNER) final StammImpl owner, @Fact(IdGenerationFacts.LAST_STATUS) Optional<HiAntragsteller> hiAntragsteller) {
		final Optional<Date> dateOfDeath = Optional.ofNullable(conversionService.convert(owner.getSterbedatum(), Date.class));
		 LocalDate now = LocalDate.now();
		if( dateOfDeath.isPresent()) {
			final LocalDate startDate =  dateOfDeath.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		  
		    if( ChronoUnit.MONTHS.between(startDate, now) >= 13 ) {
		    	return false;
		    }
			
		}
		
		final String lastState = lastState(hiAntragsteller);
	
		if( lastState.equalsIgnoreCase("END") || lastState.equalsIgnoreCase("AUS") ) {
			final LocalDate exDate = lastDate(hiAntragsteller,  20991231L).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 if( ChronoUnit.MONTHS.between(exDate, now) >= 13 ) {
				 return false;
			 }
		}
		
		return true;

	}

	

	@Action(order = 0)
	public void assignValues(@Fact(IdGenerationFacts.OWNER) final StammImpl owner,@Fact(IdGenerationFacts.LAST_STATUS) final Optional<HiAntragsteller> hiAntragsteller, @Fact(IdGenerationFacts.ID_MAPPING) final IdMapping idMapping, @Fact(IdGenerationFacts.COUNTERS) final Counters counters) {
		idMapping.setLastState(lastState(hiAntragsteller));
		idMapping.setLastStateDate(lastDate(hiAntragsteller, 19000101L));
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

		

	}

	private Date lastDate(final Optional<HiAntragsteller> hiAntragsteller, final long nvlBeginDate) {
		final Date defaultBeginDate = conversionService.convert(nvlBeginDate, Date.class);
		if ( ! hiAntragsteller.isPresent() ) {
		    return defaultBeginDate;	
		}
		final Date date =  conversionService.convert(hiAntragsteller.get().getBeginnDatum(), Date.class);
		return date == null ? defaultBeginDate: date;
	}
	
	private String lastState(final Optional<HiAntragsteller> hiAntragsteller) {
		if ( ! hiAntragsteller.isPresent() ) {
		    return UNKOWN_STATE;	
		}
		
		return StringUtils.hasText(hiAntragsteller.get().getWertChar()) ? hiAntragsteller.get().getWertChar().toUpperCase() : UNKOWN_STATE;
	}

}
