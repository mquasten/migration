package de.msg.jbit7.migration.itnrw.mapping;





import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class IdMappingBuilder {
	private final IdMapping idMapping = new IdMapping();
	private final Collection<String> children = new ArrayList<>();
	
	private final List<Long> collectiveContracts = new ArrayList<>();
	
	
	

	public final IdMappingBuilder withMarriagePartner(final String marriagePartnerNr) {
		requiredGuard(marriagePartnerNr);
		idMapping.setMarriagePartnerNr(marriagePartnerNr);
		return this;
	}

	private void requiredGuard(final String marriagePartnerNr) {
		Assert.notNull(marriagePartnerNr, "Required");
	}
	
	public final IdMappingBuilder withMarriagePartner() {
		return withMarriagePartner("" + TestUtil.randomLong());
	}
	
	public final IdMappingBuilder withContractNumber(final long contractNumber) {
		idMapping.setContractNumber(contractNumber);
		return this;
	}
	public final IdMappingBuilder withBeihilfeNr(final long beihilfenr) {
		idMapping.setBeihilfenr(beihilfenr);
		return this;
	}
	
	public final IdMappingBuilder withProcessNumber(final long processNumber) {
		idMapping.setProcessNumber(processNumber);
		return this;
	}
	
	public final IdMappingBuilder withMandator(final long mandator) {
		idMapping.setMandator(mandator);
		return this;
	}
	
	public final IdMappingBuilder withChildren(final int numberOfChildren) {
		Assert.isTrue(numberOfChildren > 0 , "NumberOfChildren >= 0");
		children.clear();
		children.addAll(IntStream.range(0, numberOfChildren).mapToObj(i -> ""+TestUtil.randomLong()).collect(Collectors.toList()));
		return this;
	}
	
	public final IdMappingBuilder withChildren(final String ... children) {
	    this.children.clear();
	    this.children.addAll(Arrays.asList(children));
	    return this;
	}
	
	public final IdMappingBuilder withLastState(final String lastState, final Date date) {
		idMapping.setLastState(lastState);
		idMapping.setLastStateDate(date);
	    return this;
	}
	
	public final IdMappingBuilder withLastState(final String lastState) {
		idMapping.setLastState(lastState);
		idMapping.setLastStateDate(TestUtil.randomDate());
	    return this;
	}
	
	
	
	public final IdMapping build() {
		
		if( collectiveContracts.size() == 0) {
			collectiveContracts.add(TestUtil.randomLong());
		}
		
		idMapping.setCollectiveContractNumbers(new Long[collectiveContracts.size()]);
		
		
		
		idMapping.setChildrenPartnerNr(children.toArray(new String[children.size()]));
		final List<Long> childrenNumbers =IntStream.range(0, children.size()).mapToLong(i -> Long.valueOf(i)).boxed().collect(Collectors.toList());
		idMapping.setChildrenNr(childrenNumbers.toArray(new Long[childrenNumbers.size()]));
		if( StringUtils.isEmpty(idMapping.getMarriagePartnerNr())){
			idMapping.setMarriagePartnerNr("");
		}
		if( StringUtils.isEmpty(idMapping.getPartnerNr() )) {
			idMapping.setPartnerNr("" + TestUtil.randomLong());
		}
		
		if( StringUtils.isEmpty(idMapping.getMigrationUser())){
			idMapping.setMigrationUser(TestUtil.randomString().substring(0, 20));
		}
		
		if( idMapping.getLastState()==null) {
			idMapping.setLastState("LFD");
			idMapping.setLastStateDate(TestUtil.randomDate());
		}
		
		TestUtil.assignValuesToBean(idMapping, field -> {
			if(Modifier.isStatic(field.getModifiers())) {
				return false;
			}
			if( field.getType().isArray()) {
				return false;
			}
			
			field.setAccessible(true);
			
			return ReflectionUtils.getField(field,idMapping) == null;
			
		});
		
		if( StringUtils.isEmpty(idMapping.getMarriagePartnerNr())){
			idMapping.setMarriagePartnerNr(null);
		}
		
		Assert.notNull(idMapping.getContractNumber(), "ContractNumber is required.");
	
		Assert.notNull( idMapping.getCollectiveContractNumbers(), "At least 1 CollectiveContract is required.");
		Assert.isTrue(idMapping.getCollectiveContractNumbers().length >= 1 ,  "At least 1 CollectiveContract is required.");
		Assert.isTrue(idMapping.getCollectiveContractNumbers().length <= 2 ,  "No more than 2 CollectiveContracts allowed");
		Assert.notNull(idMapping.getMandator() , "Mandator is required.");	
		
		Assert.notNull(idMapping.getProcessNumber(), "ProcessNumber is required.");
		Assert.notNull(idMapping.getBeihilfenr() , "Beihilfenr is required.");
		Assert.hasText(idMapping.getMigrationUser() , "MigrationUser is required.");
		
		return idMapping;
	}

	public static final IdMappingBuilder builder() {
		return new IdMappingBuilder();
	}
}
