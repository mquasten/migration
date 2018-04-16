package de.msg.jbit7.migration.itnrw.mapping.support;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.support.ConditionalRuleGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
@EnableAspectJAutoProxy()
class RulesConfiguration {

	@Bean
	Rules rulesIdGeneration() {

		final ConditionalRuleGroup group = new ConditionalRuleGroup("ConditionalRuleIdGeneration");
		group.addRule(new IdGenerationOwnerRule());
		group.addRule(new IdGenerationMarriagePartnerRule());
		group.addRule(new IdGenerationChildrenRule());

		return new Rules(group);

	}

	@Bean
	@Scope("prototype")
	DefaultRulesEngine rulesEngine() {
		return new DefaultRulesEngine();
	}
	
	@Bean
	@Scope("prototype")
	CatchExceptionRuleListener ruleListener() {
		return new CatchExceptionRuleListener();
	}
	
	@Bean
	ConversionService conversionService() {
		return new DefaultConversionService();
	}

}
