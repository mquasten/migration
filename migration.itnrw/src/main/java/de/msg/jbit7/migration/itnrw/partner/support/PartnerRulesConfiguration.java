package de.msg.jbit7.migration.itnrw.partner.support;

import org.jeasy.rules.api.Rules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
class PartnerRulesConfiguration {

	@Bean("partnerRules")
	Rules rulesIdGeneration(ConversionService conversionService) {
		final Rules rules = new Rules();
		rules.register(new PartnerContractRule());
		rules.register(new PartnerRule(conversionService));
		return rules;
	}
	
	
}
