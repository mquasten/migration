package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;

import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFacts;
import de.msg.jbit7.migration.itnrw.partner.PartnerFamilyFacts.FamilyMembers;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "partnerHistory", priority = Integer.MAX_VALUE)
public class PartnerTerminatedRule {

	private final ConversionService conversionService;

	private final PartnerFactory partnerFactory;

	public PartnerTerminatedRule(final PartnerFactory partnerFactory, final ConversionService conversionService) {
		this.conversionService = conversionService;
		this.partnerFactory = partnerFactory;
	}

	@Condition
	public final boolean evaluate(@Fact(PartnerFacts.STAMM) StammImpl stamm) {
		return true;
	}

	@Action(order = 1)
	public final void terminateContract(@Fact(PartnerFacts.STAMM) StammImpl stamm,
			@Fact(PartnerFacts.RESULTS_CONTRACT_SPEL) Collection<PMContract> contracts,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {
		Assert.isTrue(contracts.size() <= 1, "Max. 1 contract can exist.");
		contracts.forEach(contract -> newTerminatedContract(stamm, contract).ifPresent(value -> results.add(value)));
	}

	private Optional<PMContract> newTerminatedContract(final StammImpl stamm, final PMContract pmContract) {
		Assert.isTrue(pmContract.getHistnr() == 1L, "Histnr. 1 expected.");
		if (conversionService.convert(stamm.getSterbedatum(), Date.class) != null) {
			final Date endDate = conversionService.convert(stamm.getSterbedatum(), Date.class);
			final PMContract result = partnerFactory.copy(pmContract);
			result.setDop(endDate);
			result.setInd(nextDay(endDate));
			result.setReasonForChange(900L);
			result.setPrionr(900L);
			result.setTerminationDate(nextDay(endDate));
			result.setHistnr(Long.valueOf(2));
			result.setTerminationflag(1L);
			return Optional.of(result);
		}
		return Optional.empty();
	}

	@Action(order = 2)
	public final void terminatePartner(@Fact(value = "terminationDates$") FamilyMembers familyMembers,
			@Fact(PartnerFacts.RESULTS_PARTNERS_CORE_SPEL) Collection<PartnerCore> partnersCore,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {
		partnersCore.forEach(
				partner -> newTerminatedPartner(familyMembers, partner).ifPresent(value -> results.add(value)));
	}

	private Optional<PartnerCore> newTerminatedPartner(final FamilyMembers familyMembers,
			final PartnerCore partnerCore) {
		Assert.isTrue(partnerCore.getHistnr() == 1L, "Histnr. 1 expected.");
		final Optional<Date> dateOfDeath = familyMembers.get(partnerCore.getPartnersNr());

		if (dateOfDeath.isPresent()) {

			final PartnerCore deathPartner = terminatedPartner(partnerCore, nextDay(dateOfDeath.get()),
					dateOfDeath.get());
			deathPartner.setDateOfDeath(dateOfDeath.get());
			return Optional.of(deathPartner);
		}
		return Optional.empty();
	}

	private PartnerCore terminatedPartner(final PartnerCore partnerCore, final Date ind, final Date dop) {
		final PartnerCore deathPartner = partnerFactory.copy(partnerCore);
		deathPartner.setDop(dop);
		deathPartner.setInd(ind);
		deathPartner.setReasonForChange(900L);
		deathPartner.setTerminationflag(1L);
		deathPartner.setHistnr(2L);
		return deathPartner;
	}

	@Action(order = 3)
	public final void terminatePartnersRole(@Fact(value = "terminationDates$") FamilyMembers familyMembers,
			@Fact(PartnerFacts.RESULTS_PARTNERS_ROLE_SPEL) Collection<PartnersRole> partnersroles,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {
		partnersroles
				.forEach(role -> newTerminatedPartnerRole(familyMembers, role).ifPresent(value -> results.add(value)));
	}

	private Optional<PartnersRole> newTerminatedPartnerRole(final FamilyMembers familyMembers,
			final PartnersRole role) {
		final Optional<Date> dateOfDeath = familyMembers.get(role.getRightSide());
		if (dateOfDeath.isPresent()) {
			return Optional.of(newTerminatedPartnersRole(role, nextDay(dateOfDeath.get()), dateOfDeath.get(), 2));
		}
		return Optional.empty();
	}

	private PartnersRole newTerminatedPartnersRole(final PartnersRole role, final Date ind, final Date dop,
			final long state) {
		final PartnersRole terminatedPartnersRole = partnerFactory.copy(role);
		terminatedPartnersRole.setDop(dop);
		terminatedPartnersRole.setInd(ind);
		terminatedPartnersRole.setTerminationflag(1L);
		terminatedPartnersRole.setRoleState(state);
		terminatedPartnersRole.setHistnr(2L);
		return terminatedPartnersRole;
	}

	private Date nextDay(final Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();

	}

}
