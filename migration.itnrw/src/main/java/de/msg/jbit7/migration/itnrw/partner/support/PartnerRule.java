package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.Address;
import de.msg.jbit7.migration.itnrw.partner.Bank;
import de.msg.jbit7.migration.itnrw.partner.Communication;
import de.msg.jbit7.migration.itnrw.partner.CommunicationRole;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFacts;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "partner", priority = 0)
public class PartnerRule {

	final static Logger LOGGER = LoggerFactory.getLogger(PartnerRule.class);
	private final ConversionService conversionService;
	private final PartnerFactory partnerFactory;

	static final String UNDEFINED = "NOT_DEFINED";

	public PartnerRule(PartnerFactory partnerFactory, final ConversionService conversionService) {
		this.conversionService = conversionService;
		this.partnerFactory = partnerFactory;
	}

	@Condition
	public boolean evaluate() {
		return true;
	}

	@Action(order = 1)
	public final void assignPartner(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.STAMM) StammImpl stamm,
			@Fact(PartnerFacts.SEPA_BANK) Collection<SepaBankVerbindung> sepaBankVerbindung,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {

		final PartnerCore partnerCore = partnerFactory.newPartnerCore();

		partnerCore.setBirthName(notNull(stamm.getGeburtsname()));

		partnerCore.setDateOfBirth(conversionService.convert(stamm.getGebDatum(), Date.class));
	
		partnerCore.setDefaultAddress("1");
		partnerCore.setDefaultBank(defaultBank(sepaBankVerbindung));
		partnerCore.setDefaultCommunication(defaultCommunication(stamm));

		partnerCore.setDop(contractDate);

		partnerCore.setFirstName(stamm.getVorname());

		partnerCore.setInd(contractDate);

		partnerCore.setMandator(idMapping.getMandator());
		partnerCore.setMaritalStatus(StringUtils.hasText(idMapping.getMarriagePartnerNr()) ? 2L : 1L);

		partnerCore.setNamePrefix(stamm.getZusatz1Name());

		partnerCore.setNotice(stamm.getBemerkung());
		partnerCore.setNumberChildren(
				idMapping.getChildrenNr() != null ? Long.valueOf(idMapping.getChildrenNr().length) : 0L);
		partnerCore.setPartnersNr(idMapping.getPartnerNr());

		partnerCore.setProcessnr(idMapping.getProcessNumber());

		partnerCore.setSalutation(salutationAndSex(stamm));
		partnerCore.setSecondName(stamm.getName());

		partnerCore.setSex(salutationAndSex(stamm));
		
		partnerCore.setTitle(notNull(stamm.getTitel()));

		partnerCore.setUserid(idMapping.getMigrationUser());

		results.add(partnerCore);
		
		
		
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

	private String defaultCommunication(final StammImpl stamm) {
		if (StringUtils.hasText(stamm.getEmailPrivat())) {
			return "1";
		}
		if (StringUtils.hasText(stamm.getEmailDienst())) {
			return "1";
		}

		return "0";

	}

	private Long salutationAndSex(final StammImpl stamm) {
		if (!StringUtils.hasText(stamm.getGeschlecht())) {
			return 0L;
		}
		if (stamm.getGeschlecht().equalsIgnoreCase("w")) {
			return 2L;
		}

		if (stamm.getGeschlecht().equalsIgnoreCase("m")) {
			return 1L;
		}

		return 0L;
	}

	private String defaultBank(Collection<SepaBankVerbindung> sepaBankVerbindungen) {
		return CollectionUtils.isEmpty(sepaBankVerbindungen) ? "0" : "1";
	}

	@Action(order = 2)
	public final void assignNewAddress(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.STAMM) StammImpl stamm, @Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {
		final Address address = partnerFactory.newAddress();
		address.setCity1(stamm.getOrt());
		address.setCountry(stamm.getLaenderKennz());
		address.setDop(contractDate);
		address.setInd(contractDate);
		address.setMandator(idMapping.getMandator());
		address.setPartnersNr(idMapping.getPartnerNr());
		address.setPostcode(stamm.getPlz());
		address.setProcessnr(idMapping.getProcessNumber());
		address.setStreet(stamm.getStrasseNr());
		address.setUserid(idMapping.getMigrationUser());
		
		results.add(address);
	}

	@Action(order = 3)
	public final void assignNewBank(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.SEPA_BANK) List<SepaBankVerbindung> sepaBankVerbindungen,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {

		if (CollectionUtils.isEmpty(sepaBankVerbindungen)) {
			LOGGER.warn(String.format("No BankingAccount found for BeihilfeNr:%s", idMapping.getBeihilfenr()));
			return;
		}

		IntStream.range(0, sepaBankVerbindungen.size())
				.forEach(i -> results.add(toBank(idMapping, contractDate, sepaBankVerbindungen.get(i), i)));

	}

	private Bank toBank(IdMapping idMapping, final Date contractDate, final SepaBankVerbindung sepaBankVerbindung,
			final int i) {
		final Bank bank = new Bank();
		bank.setAccountHolder(PartnerFactory.BLANK);
		bank.setAccountNumber(PartnerFactory.BLANK);
		bank.setAccountType(0L);
		bank.setBankCode(PartnerFactory.BLANK);
		bank.setBankDistrict(PartnerFactory.BLANK);
		bank.setBankName(notNull(sepaBankVerbindung.getNameBank()));
		bank.setBankNr(String.valueOf(i + 1));
		bank.setBankState(1L);
		bank.setBic(notNull(sepaBankVerbindung.getBic()));
		bank.setCountry(PartnerFactory.BLANK);
		bank.setCreditCardCompany(PartnerFactory.BLANK);
		bank.setCreditCardExpiry(null);
		bank.setCreditCardNumber(PartnerFactory.BLANK);
		bank.setCreditCardSecurityCode(null);
		bank.setCreditCardType(PartnerFactory.BLANK);
		bank.setCurrencyOfAccount(null);
		bank.setDatastate("0");
		bank.setDistrictBankCode(PartnerFactory.BLANK);
		bank.setDop(contractDate);
		bank.setDor(null);
		bank.setHistnr(1L);
		bank.setIban(sepaBankVerbindung.getIban());
		bank.setInd(contractDate);
		bank.setMandator(idMapping.getMandator());
		bank.setOutdated(0L);
		bank.setPartnersNr(idMapping.getPartnerNr());
		bank.setPostcode(null);
		bank.setProcessnr(idMapping.getProcessNumber());
		bank.setReasonForChange(null);
		bank.setRprocessnr(null);
		bank.setStreetAndNo(null);
		bank.setTerminationflag(0L);
		bank.setTown(null);
		bank.setUserid(idMapping.getMigrationUser());
		return bank;
	}

	@Action(order = 4)
	public final void assignNewCommunication(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.STAMM) StammImpl stamm, @Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {

		long communicationNumber = 1;
		if (StringUtils.hasText(stamm.getEmailPrivat())) {
			results.add(newCommunication(idMapping, stamm.getEmailPrivat(), contractDate, communicationNumber, 3));
			communicationNumber++;
		}

		if (StringUtils.hasText(stamm.getEmailDienst())) {
			results.add(newCommunication(idMapping, stamm.getEmailDienst(), contractDate, communicationNumber, 4));
		}

		if (communicationNumber > 1) {
			final CommunicationRole communicationRole = new CommunicationRole();

			communicationRole.setMandator(idMapping.getMandator());
			communicationRole.setDatastate("0");
			communicationRole.setProcessnr(idMapping.getProcessNumber());
			communicationRole.setCommunicationKey(Long.valueOf(idMapping.getPartnerNr()));
			communicationRole.setCommunicationRoleKey(Long.valueOf(idMapping.getPartnerNr()));
			communicationRole.setCommunicationNr("1");
			results.add(communicationRole);
		}

	}

	private Communication newCommunication(final IdMapping idMapping, final String value, final Date contractDate,
			final long communicationNr, final long type) {
		final Communication emailPrivate = new Communication();
		emailPrivate.setMandator(idMapping.getMandator());
		emailPrivate.setDatastate("0");
		emailPrivate.setProcessnr(idMapping.getProcessNumber());
		emailPrivate.setHistnr(1L);
		emailPrivate.setDop(contractDate);
		emailPrivate.setDor(null);
		emailPrivate.setInd(contractDate);
		emailPrivate.setTerminationflag(0L);
		emailPrivate.setPartnersNr(idMapping.getPartnerNr());
		emailPrivate.setCommunicationNr("" + communicationNr);
		emailPrivate.setCommunicationType(type);
		emailPrivate.setValue(value);
		emailPrivate.setReasonForChange(null);
		emailPrivate.setAvailability(null);
		emailPrivate.setUserid(idMapping.getMigrationUser());
		emailPrivate.setOutdated(0L);
		emailPrivate.setUsageAgreement(0L);
		return emailPrivate;
	}

	@Action(order = 5)
	public final void assignPartnersRole(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.STAMM) StammImpl stamm,
			@Fact(PartnerFacts.SEPA_BANK) List<SepaBankVerbindung> sepaBankVerbindungen,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.RESULTS) Collection<Object> results) {

		final String bankNr = sepaBankVerbindungen.size() > 0 ? "1" : null;
		final Long communicationRoleKey = StringUtils.hasText(stamm.getEmailDienst())
				| StringUtils.hasText(stamm.getEmailPrivat()) ? Long.valueOf(idMapping.getPartnerNr()) : null;

		final PartnersRole partnersRolePH = newPartnerRole(idMapping, contractDate);
		partnersRolePH.setAddressNr("1");
		partnersRolePH.setBankNr(bankNr);
		partnersRolePH.setCommunicationRoleKey(communicationRoleKey);
		partnersRolePH.setRole("PH");

		final PartnersRole partnersRoleIp = newPartnerRole(idMapping, contractDate);
		
		partnersRoleIp.setRole("IP");
		results.add(partnersRolePH);
		results.add(partnersRoleIp);
		
		
	

	}

	private PartnersRole newPartnerRole(final IdMapping idMapping, final Date contractDate) {
		final PartnersRole partnersRole = partnerFactory.newPartnersRole();
		partnersRole.setMandator(idMapping.getMandator());
		partnersRole.setProcessnr(idMapping.getProcessNumber());
		partnersRole.setDop(contractDate);
		partnersRole.setInd(contractDate);
		partnersRole.setLeftSide("" + idMapping.getContractNumber());
		partnersRole.setRightSide(idMapping.getPartnerNr());
		partnersRole.setExternKey( "" + idMapping.getBeihilfenr());

		return partnersRole;
	}

}
