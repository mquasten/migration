package de.msg.jbit7.migration.itnrw.partner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface  PartnerFamilyFacts {

		final static String ID_MAPPING = "idMapping";
		final static String CONTRACT_DATE = "contractDate";		
		final static String RESULTS = "results";
		final static String MARRIAGE_PARTNER = "marriagePartner";
		final static String STAMM = "stamm";
		final static String CHILDREN = "children";
		
		
		public class FamilyMembers {
			private final Map<String, Date> members = new HashMap<>();
			
			
			public void put(final String partnerNumber, final Date dateOfDeath) {
				members.put(partnerNumber, dateOfDeath);
			}
			
			public Optional<Date> get(final String partnerNumber) {
				return (Optional<Date>) Optional.ofNullable(members.get(partnerNumber));
			}
		}


}
