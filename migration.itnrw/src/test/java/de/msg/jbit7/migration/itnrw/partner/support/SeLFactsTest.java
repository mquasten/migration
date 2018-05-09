package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingBuilder;
import de.msg.jbit7.migration.itnrw.mapping.support.CatchExceptionRuleListener;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFacts;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.partner.SpelFacts;
import de.msg.jbit7.migration.itnrw.stamm.StammBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class SeLFactsTest {
	
	

	private final StammImpl stamm =  StammBuilder.builder().withSterbeDatum().build();

	private DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
	
	private final DefaultConversionService conversionService =  new DefaultConversionService();
	private final PartnerFactory partnerFactory = new PartnerFactory();
	
	private final PartnerTerminatedRule partnerTerminatedRule = new PartnerTerminatedRule(partnerFactory, conversionService);
	
	private final Facts facts = new SpelFacts(Arrays.asList(new FamilyMemberTerminationDatesByPartnerNumberConverter(conversionService))); 
	
	private final List<Object> results = new ArrayList<>();
	
	private final PMContract firstContract = partnerFactory.newContract();
	private final PartnerCore firstPartnerCore = partnerFactory.newPartnerCore();
	
	private final  PartnersRole firstPartnersRole = partnerFactory.newPartnersRole();
	
	private final IdMapping mapping = IdMappingBuilder.builder().build();
	
	@BeforeEach
	final void setup() {
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
	
		firstPartnerCore.setPartnersNr(mapping.getPartnerNr());
		firstPartnersRole.setRightSide(mapping.getPartnerNr());
		results.add(firstContract);
		
		results.add(firstPartnerCore);
		
		results.add(firstPartnersRole);
		facts.put(PartnerFacts.STAMM, stamm);
	
		facts.put(PartnerFacts.ID_MAPPING, mapping);
		
		facts.put(PartnerFacts.RESULTS, results );
	
	}
	
	
	@Test
	final void process() {
		final CatchExceptionRuleListener ruleListener = processRulesEngine();
		ruleListener.exceptions().forEach(e -> e.printStackTrace());
		assertFalse(ruleListener.hasErrors());
	
		assertEquals(6, results.size());
		
		final PMContract pmContract = (PMContract) results.get(3);
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), pmContract.getDop());
		
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()+1), pmContract.getInd());
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()+1), pmContract.getTerminationDate());
		assertEquals(Long.valueOf(2), pmContract.getHistnr());
		assertEquals(Long.valueOf(900), pmContract.getReasonForChange());
		assertEquals(Long.valueOf(1), pmContract.getTerminationflag());
		
		final PartnerCore partnersCore = (PartnerCore) results.get(4);
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), partnersCore.getDop());
		
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()+1), partnersCore.getInd());
		assertEquals(Long.valueOf(900L), partnersCore.getReasonForChange());
		assertEquals(Long.valueOf(1L), partnersCore.getTerminationflag());
		assertEquals(Long.valueOf(2L), partnersCore.getHistnr());
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), partnersCore.getDateOfDeath());
		
		final PartnersRole partnersRole = (PartnersRole) results.get(5);
		
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), partnersRole.getDop());
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()+1), partnersRole.getInd());
		assertEquals(Long.valueOf(1), partnersRole.getTerminationflag());
		assertEquals(Long.valueOf(2), partnersRole.getRoleState());
		assertEquals(Long.valueOf(2), partnersRole.getHistnr());
		
	}


	private CatchExceptionRuleListener processRulesEngine() {
		final Rules rules = new Rules(partnerTerminatedRule);
		CatchExceptionRuleListener ruleListener = new CatchExceptionRuleListener();
		rulesEngine.registerRuleListener(ruleListener);
		rulesEngine.fire(rules, facts);
		return ruleListener;
	}

}
