package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.Address;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFacts;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.Drittempfaenger;

@Rule(name = "partnerRecipient", priority = 0)
public class RecipientPartnerRule {
	
	
	private final PartnerFactory partnerFactory;
	RecipientPartnerRule(final PartnerFactory partnerFactory) {
		this.partnerFactory = partnerFactory;
	}

	

	@Condition
	public boolean evaluate(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping) {
		return StringUtils.hasText(idMapping.getRecipient());
	}
	
	@Action(order = 1)
	public final void assignPartner(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.RECIPIENT) final Optional<Drittempfaenger> drittEmpfaenger,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {
		
			if(! drittEmpfaenger.isPresent() ) {
				return;
			}
		
			final Drittempfaenger recipient = drittEmpfaenger.get();
			final PartnerCore partnerCore = partnerFactory.newPartnerCore();
			
			partnerCore.setDefaultAddress("1");
		
			partnerCore.setMandator(idMapping.getMandator());
			partnerCore.setPartnersNr(idMapping.getRecipient());
			partnerCore.setProcessnr(idMapping.getProcessNumber());
			partnerCore.setSalutation(salutationAndSex(recipient));
			partnerCore.setSex(salutationAndSex(recipient));
			partnerCore.setUserid(idMapping.getMigrationUser());
			partnerCore.setDop(contractDate);
			partnerCore.setInd(contractDate);
			
			partnerCore.setFirstName(recipient.getVorname());
			
			partnerCore.setNameAddition(recipient.getZusatzname1());
			partnerCore.setNameAddition2(recipient.getZusatzname2());
			partnerCore.setSecondName(recipient.getName());
			partnerCore.setTitle(notNull(recipient.getTitel()));
			results.add(partnerCore);
		
	}
	
	@Action(order = 2)
	public final void assignPartnerRole(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.RECIPIENT) final Optional<Drittempfaenger> drittEmpfaenger,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {
		
			if( ! drittEmpfaenger.isPresent()) {
				return;
			}
		
			final PartnersRole paPartnersRole = partnerFactory.newPartnersRole();
			paPartnersRole.setMandator(idMapping.getMandator());
			paPartnersRole.setProcessnr(idMapping.getProcessNumber());
			paPartnersRole.setDop(contractDate);
			paPartnersRole.setInd(contractDate);
			paPartnersRole.setRole("PA");
			paPartnersRole.setLeftSide("" + idMapping.getContractNumber());
			paPartnersRole.setRightSide(idMapping.getRecipient());
			paPartnersRole.setAddressNr("1");
			paPartnersRole.setExternKey(""+idMapping.getBeihilfenr());
			
			results.add(paPartnersRole);
			
			if( notNull(drittEmpfaenger.get().getVollmacht()).equalsIgnoreCase("J") ) {
				final PartnersRole vpPartnersRole = partnerFactory.copy(paPartnersRole);
				vpPartnersRole.setRole("VP");
				vpPartnersRole.setAddressNr(null);
				results.add(vpPartnersRole);
			}
		
	}
	
	
	@Action(order = 3)
	public final void assignAddress(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.RECIPIENT) final Optional<Drittempfaenger> drittEmpfaenger,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {
		
		if( ! drittEmpfaenger.isPresent()) {
			return;
		}
		final Address address = partnerFactory.newAddress();
		
		final Drittempfaenger recipient = drittEmpfaenger.get();
		address.setCity1(recipient.getOrt());
		address.setDop(contractDate);
		address.setInd(contractDate);
		address.setPartnersNr(idMapping.getRecipient());
		address.setPostcode(recipient.getPlz());
		address.setProcessnr(idMapping.getProcessNumber());
		address.setStreet(recipient.getStrasse());
		address.setUserid(idMapping.getMigrationUser());
		address.setMandator(idMapping.getMandator());
		address.setCountry(notNull(recipient.getLaenderKennz()));
		results.add(address);
	}
	
	private Long salutationAndSex(final Drittempfaenger drittempfaenger) {
		if (!StringUtils.hasText(drittempfaenger.getGeschlecht())) {
			return 0L;
		}
		if (drittempfaenger.getGeschlecht().equalsIgnoreCase("w")) {
			return 2L;
		}

		if (drittempfaenger.getGeschlecht().equalsIgnoreCase("m")) {
			return 1L;
		}

		return 0L;
	}
	
	private String notNull(final String text) {
		if (text == null) {
			return PartnerFactory.BLANK;
		}
		if (text.isEmpty()) {
			return PartnerFactory.BLANK;
		}
		return text;
	}
	
}
