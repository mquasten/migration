package de.msg.jbit7.migration.itnrw.partner.support;

import org.jeasy.rules.api.Rules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PartnerRulesConfiguration {

	@Bean("partnerRules")
	Rules rulesIdGeneration() {
		final Rules rules = new Rules();
		rules.register(new PartnerContractRule());
		rules.register(new PartnerRule());
		return rules;
	}
}
