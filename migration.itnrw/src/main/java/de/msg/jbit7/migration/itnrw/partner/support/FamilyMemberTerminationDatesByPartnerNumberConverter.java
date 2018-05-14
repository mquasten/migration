package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.PartnerFamilyFacts;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

public class FamilyMemberTerminationDatesByPartnerNumberConverter implements Converter<Map<String,Object>, PartnerFamilyFacts.FamilyMembers> {

	
	private final ConversionService conversionService;
	
	public FamilyMemberTerminationDatesByPartnerNumberConverter(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Override
	public PartnerFamilyFacts.FamilyMembers convert(Map<String, Object> facts) {
		final PartnerFamilyFacts.FamilyMembers  members = new PartnerFamilyFacts.FamilyMembers();
		final IdMapping mapping = (IdMapping) facts.get(PartnerFamilyFacts.ID_MAPPING);
		Assert.notNull(mapping, "Mapping must be aware.");
		
		if( mapping.getLastState().equals("END") || mapping.getLastState().equals("AUS")) {
			members.assignTerminationdateIfNotExists(mapping.getLastStateDate());
		}
		
		final StammImpl stamm = (StammImpl) facts.get(PartnerFamilyFacts.STAMM);
		if(( stamm != null))  {	
			addDateOfDeathIfExists(members, mapping.getPartnerNr(), stamm.getSterbedatum());
			assignTerminationdateDeath(members, mapping, stamm);
		}
		final Ehegatte ehegatte = (Ehegatte) facts.get(PartnerFamilyFacts.MARRIAGE_PARTNER);
		if(( ehegatte != null)&&(mapping.getMarriagePartnerNr()!=null))  {
			addDateOfDeathIfExists(members, mapping.getMarriagePartnerNr(), ehegatte.getSterbedatum());
		}
		
		@SuppressWarnings("unchecked")
		final Collection<KindInfo> kindInfos = facts.get(PartnerFamilyFacts.CHILDREN) != null ? (List<KindInfo>) facts.get(PartnerFamilyFacts.CHILDREN) : Arrays.asList();
	
		final Map<Long,KindInfo> children = kindInfos.stream().collect(Collectors.toMap(child -> child.getLfdKind(), child -> child));
		
		IntStream.range(0, children.size()).forEach(i   -> { 
			final String partnerNumber = mapping.getChildrenPartnerNr()[i];
			addDateOfDeathIfExists(members, partnerNumber, children.get(mapping.getChildrenNr()[i]).getSterbedatum());
		
		});
		
		
		
		return members;
	}

	private void assignTerminationdateDeath(final PartnerFamilyFacts.FamilyMembers members, final IdMapping mapping, final StammImpl stamm) {
		final Date dateOfDeath = conversionService.convert(stamm.getSterbedatum(), Date.class);
		if( dateOfDeath == null) {
			return;
			
		}
		members.assignTerminationdateIfNotExists(mapping.getLastStateDate());
	}

	private void addDateOfDeathIfExists(final PartnerFamilyFacts.FamilyMembers members, final String partnerNumber, final Long  dateOfDeathAsLong) {
		final Date dateOfDeath = conversionService.convert(dateOfDeathAsLong, Date.class);
		if(dateOfDeath == null) {
			return;
		}
		members.put(partnerNumber, dateOfDeath);
	
	}

	@Override
	public String toString() {
		return "terminationDates";
	}

	
	
}
