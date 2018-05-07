package de.msg.jbit7.migration.itnrw.partner;

public interface PartnerFacts {
	final static String ID_MAPPING = "idMapping";
	final static String CONTRACT_DATE = "contractDate";

	final static String STAMM = "stamm";

	final static String SEPA_BANK = "sepaBankVerbindung";
	
	
	final static String RESULTS = "results";
	
	final static String RESULTS_PARTNERS_CORE_SPEL = "results$?[#this instanceof T(de.msg.jbit7.migration.itnrw.partner.PartnerCore)]";

	final static String RESULTS_CONTRACT_SPEL = "results$?[#this instanceof T(de.msg.jbit7.migration.itnrw.partner.PMContract)]";

	final static String RESULTS_PARTNERS_ROLE_SPEL = "results$?[#this instanceof T(de.msg.jbit7.migration.itnrw.partner.PartnersRole)]";
}
