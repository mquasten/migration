package de.msg.jbit7.migration.itnrw.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import de.msg.jbit7.migration.itnrw.mapping.support.CatchExceptionRuleListener;
import de.msg.jbit7.migration.itnrw.mapping.support.Counters;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;


@Service
abstract class IdGenerationService {

	
	private final StammRepository stammRepository;
	
	private final IdMappingRepository idMappingRepository;
	final static Logger LOGGER = LoggerFactory.getLogger(IdGenerationService.class);
	
	private final Rules rules;

	@Autowired
	IdGenerationService(final StammRepository stammRepository, final IdMappingRepository idMappingRepository, final Rules rules) {
		this.stammRepository = stammRepository;
		this.idMappingRepository=idMappingRepository;
		this.rules=rules;
	}
	
	
	public  void createIds(final long mandator, final boolean deleteFirst) {
		
		if( deleteFirst) {
			idMappingRepository.delete();
		}
	
		final Counters counters = idMappingRepository.findCounters(mandator);
		
		final Map<Long, Ehegatte> marriagePartners = stammRepository.findAllEhegatte().stream().collect(Collectors.toMap(Ehegatte::getBeihilfenr, ehegatte -> ehegatte));
		final  Map<Long,List<KindInfo>> children = findAllChildren();
			
			
		
		
		stammRepository.findAll().forEach(stamm -> {
			
			final IdMapping idMapping = new IdMapping();
			final Facts  facts = new Facts();
			facts.put(IdGenerationFacts.OWNER, stamm);
			facts.put(IdGenerationFacts.COUNTERS,  counters);
			facts.put(IdGenerationFacts.ID_MAPPING,  idMapping);
		
			facts.put(IdGenerationFacts.MARRIAGE_PARTNERS,  marriagePartners);
			facts.put(IdGenerationFacts.CHILDREN,  children);
			final DefaultRulesEngine rulesEngine = rulesEngine();
			final CatchExceptionRuleListener ruleListener = ruleListener();
			rulesEngine.registerRuleListener(ruleListener);
			
			rulesEngine.fire(rules, facts);
			
			if( ruleListener.hasErrors()) {
				ruleListener.exceptions().forEach(ex -> LOGGER.error("Exceptions Beihilfenr:" + stamm.getBeihilfenr(), ex));
			} else {
				LOGGER.info("Regeln erfolgreich verarbeitet Beihilfenr: " + stamm.getBeihilfenr());
			}
			
			
			idMappingRepository.persist(idMapping);
			
			
			
			
		});
	}


	private Map<Long,List<KindInfo>>  findAllChildren() {
		final Map<Long,List<KindInfo>> children = new HashMap<>();
		stammRepository.findAllChildren().forEach(kindInfo -> {
			if( ! children.containsKey(kindInfo.getBeihilfenr())) {
			    	children.put(kindInfo.getBeihilfenr(), new ArrayList<KindInfo>());
			}
			children.get(kindInfo.getBeihilfenr()).add(kindInfo);
		});
		return children;
	}

	
	
	public Map<Long, IdMapping> findAll() {
		return idMappingRepository.findAll().stream().collect(Collectors.toMap(idMapping -> idMapping.getBeihilfenr(), idMapping -> idMapping));
	}
	

	@Lookup
	abstract DefaultRulesEngine rulesEngine();
	
	@Lookup
	abstract CatchExceptionRuleListener ruleListener();

}
