package de.msg.jbit7.migration.itnrw.partner.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFacts;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Rule(name = "partner", priority = 0)
public class PartnerRule {

	private final ConversionService conversionService = new DefaultConversionService();

	static final String BLANK = " ";
	static final String UNDEFINED = "NOT_DEFINED";

	@Condition
	public boolean evaluate() {
		return true;
	}

	@Action(order = 1)
	public final void assignNewPartner(@Fact(PartnerFacts.ID_MAPPING) IdMapping idMapping,
			@Fact(PartnerFacts.STAMM) StammImpl stamm,
			@Fact(PartnerFacts.SEPA_BANK) SepaBankVerbindung sepaBankVerbindung,
			@Fact(PartnerFacts.CONTRACT_DATE) final Date contractDate,
			@Fact(PartnerFacts.PARTNER) PartnerCore partnerCore) {

		final String toDo = "?";

		partnerCore.setActivityState(UNDEFINED);
		partnerCore.setAdvertising(0L);
		partnerCore.setBasicType(0L);

		partnerCore.setBirthName(notNull(stamm.getGeburtsname()));
		partnerCore.setCancellation(null);
		partnerCore.setCancellationDate(null);
		partnerCore.setCciNumber(null);
		partnerCore.setCitizenNumber(BLANK);
		partnerCore.setDatastate("0");
		partnerCore.setDateOfBirth(toDate(stamm.getGebDatum()));
		partnerCore.setDateOfDeath(toDate(stamm.getSterbedatum()));
		partnerCore.setDefaultAddress(defaultBank(sepaBankVerbindung));
		partnerCore.setDefaultBank(defaultBank(sepaBankVerbindung));
		partnerCore.setDefaultCommunication(defaultCommunication(stamm));
		partnerCore.setDenomination(null);
		partnerCore.setDispatchType(0L);
		partnerCore.setDop(contractDate);
		partnerCore.setDor(null);
		partnerCore.setEmployer(BLANK);
		partnerCore.setEuSanctionFlag(0L);
		partnerCore.setExtCustomerNumber(BLANK);
		partnerCore.setFirstName(stamm.getVorname());

		partnerCore.setFirstNameCan(toDo);
		partnerCore.setFirstNamePhon(toDo);
		partnerCore.setFirstSecondaryType(0L);
		partnerCore.setHealthInsuranceNumber(BLANK);
		partnerCore.setHistnr(1L);
		partnerCore.setHonoraryTitle(null);
		partnerCore.setIdDocumentAuthority(BLANK);
		partnerCore.setIdDocumentAuthorityCountry(BLANK);
		partnerCore.setIdDocumentExpiryDate(null);
		partnerCore.setIdDocumentIssuedDate(null);
		partnerCore.setIdDocumentNr(BLANK);
		partnerCore.setIdDocumentType(0L);
		partnerCore.setInd(contractDate);
		partnerCore.setLanguageCorrespondence("de");
		partnerCore.setLegalPerson(1L);
		partnerCore.setManagement(null);
		partnerCore.setMandator(idMapping.getMandator());
		partnerCore.setMaritalStatus(StringUtils.hasText(idMapping.getMarriagePartnerNr()) ? 2L : 1L);
		partnerCore.setNameAddition(null);
		partnerCore.setNameAddition2(null);
		partnerCore.setNameAddition(null);
		partnerCore.setNamePrefix(stamm.getZusatz1Name());
		partnerCore.setNationality("DE");
		partnerCore.setNationality2(null);
		partnerCore.setNameAddition3(null);
		partnerCore.setNotice(stamm.getBemerkung());
		partnerCore.setNumberChildren(
				idMapping.getChildrenNr() != null ? Long.valueOf(idMapping.getChildrenNr().length) : 0L);
		partnerCore.setPartnersNr(idMapping.getPartnerNr());
		partnerCore.setPartnerState(0L);
		partnerCore.setPepFlag(0L);
		partnerCore.setPersonnelNr(BLANK);
		partnerCore.setPlaceOfBirth(BLANK);
		partnerCore.setProcessnr(idMapping.getProcessNumber());
		partnerCore.setProfession(0L);
		partnerCore.setReasonForChange(null);
		partnerCore.setRprocessnr(null);
		partnerCore.setSalutation(salutationAndSex(stamm));
		partnerCore.setSecondName(stamm.getName());
		partnerCore.setSecondNameCan(toDo);
		partnerCore.setSecondNamePhon(toDo);
		partnerCore.setSecondSecondaryType(0L);
		partnerCore.setSector(null);
		partnerCore.setSex(salutationAndSex(stamm));
		partnerCore.setSocialInsuranceNumber(BLANK);
		partnerCore.setSocialInsuranceNumberSp(BLANK);
		partnerCore.setTenant(0L);
		partnerCore.setTerminationflag(0L);
		partnerCore.setTitle(notNull(stamm.getTitel()));
		partnerCore.setTitleOfNobility(null);
		partnerCore.setUserid("MigUser");
		partnerCore.setVipFlag(0L);

	}

	private String notNull(final String text) {
		if (text == null) {
			return BLANK;
		}
		if (text.isEmpty()) {
			return BLANK;
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

	private String defaultBank(SepaBankVerbindung sepaBankVerbindung) {
		return sepaBankVerbindung != null && StringUtils.hasText(sepaBankVerbindung.getIban()) ? "1" : "0";
	}

	private Date toDate(Long dateAsLong) {
		if (dateAsLong == null) {
			return null;
		}
		final String dateString = conversionService.convert(dateAsLong, String.class);
		if (dateString.length() != 8) {
			return null;
		}

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			return sdf.parse(dateString);
		} catch (final ParseException parseException) {
			return null;
		}

	}

}
