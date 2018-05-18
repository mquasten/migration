package de.msg.jbit7.migration.itnrw.partner.support;

import org.jeasy.rules.api.Rules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;

@Configuration
@PropertySource("classpath:migration.properties")
class PartnerRulesConfiguration {

	@Bean("partnerRules")
	Rules partnerRules(final PartnerFactory partnerFactory, final ConversionService conversionService) {
		final Rules rules = new Rules();
		rules.register(new PartnerContractRule(partnerFactory));
		rules.register(new PartnerRule(partnerFactory, conversionService));
		rules.register(new RecipientPartnerRule(partnerFactory));
		rules.register(new PartnerTerminatedRule(partnerFactory, conversionService));
		return rules;
	}
	
	@Bean("partnerFamilyRules")
	Rules partnerFamilyRules(final PartnerFactory partnerFactory, final ConversionService conversionService) {
		final Rules rules = new Rules();
		rules.register(new MarriagePartnerRule(partnerFactory, conversionService));
		rules.register(new ChildrenPartnerRule(conversionService, partnerFactory));
		rules.register(new PartnerTerminatedRule(partnerFactory, conversionService));
		return rules;
	}
	
	@Bean
	PartnerFactory partnerFactory  () {
		return new PartnerFactory();
	}
	
	@Bean
	FamilyMemberTerminationDatesByPartnerNumberConverter familyMemberTerminationDatesByPartnerNumberConverter (final ConversionService conversionService) {
		return new FamilyMemberTerminationDatesByPartnerNumberConverter(conversionService);
		
	}
	
}
