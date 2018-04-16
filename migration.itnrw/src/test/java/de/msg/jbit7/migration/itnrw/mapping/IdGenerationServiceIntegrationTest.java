package de.msg.jbit7.migration.itnrw.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.support.IdGenerationOwnerRule;
import de.msg.jbit7.migration.itnrw.mapping.support.IdMappingRepository;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;

@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})

class IdGenerationServiceIntegrationTest {
	
	private static final long MANDATOR = 4711L;

	@Autowired
	private IdGenerationService idGenerationService; 
	
	@Autowired
	private IdMappingRepository idMappingRepository;
	@Autowired
	private StammRepository  stammRepository;
	
	private StartValues startValues; 
	
	IdGenerationOwnerRule idGenerationOwnerRule = new IdGenerationOwnerRule();
	
	@BeforeEach
	void setup() {
		startValues= idMappingRepository.findCounters(MANDATOR).startValues();
	}
	
	@Test
	void createIds() {
		final  Map<Long, IdMapping> idMappingMap =idGenerationService.findAll();
		
		final List<StammImpl> owners = stammRepository.findAll().stream().filter(owner -> idGenerationOwnerRule.alive(owner) ).collect(Collectors.toList()) ; 
		assertTrue(owners.size() > 0 );
		
		final long married = idMappingMap.values().stream().filter(ipMapping -> ipMapping.getMarriagePartnerNr() != null).count();
		final long childs = idMappingMap.values().stream().mapToLong(ipMapping -> ipMapping.getChildrenNr().length).sum();
	
		final long offices = owners.stream().filter(owner -> owner.getDienststelle()!=null).map(owner -> owner.getDienststelle()).distinct().count();
		final long schools = owners.stream().filter(owner -> owner.getSchulnummer()!=null).map(owner -> owner.getSchulnummer()).distinct().count();
		
		owners.forEach(owner -> {
			
			
			final IdMapping idMapping = idMappingMap.get(owner.getBeihilfenr());
			assertNotNull(idMapping);
			assertEquals(MANDATOR, (long) idMapping.getMandator());
			assertNotNull(idMapping.getPartnerNr());
			assertTrue(Long.valueOf(idMapping.getContractNumber()) > startValues.getContractNumber());
			assertTrue(Long.valueOf(idMapping.getContractNumber()) < owners.size() + startValues.getContractNumber()+1);
			
			assertTrue(Long.valueOf(idMapping.getProcessNumber()) > startValues.getProcessNumber());
			assertTrue(Long.valueOf(idMapping.getProcessNumber()) < owners.size() + startValues.getProcessNumber()+1);
			
			assertTrue(Long.valueOf(idMapping.getPartnerNr()) > startValues.getPartnerNumber());
			
			assertTrue(Long.valueOf(idMapping.getPartnerNr()) < owners.size() +  married + childs + startValues.getPartnerNumber()+1);
			
			if ( idMapping.getMarriagePartnerNr() != null) {
				assertTrue(Long.valueOf(idMapping.getMarriagePartnerNr()) > startValues.getPartnerNumber());
				assertTrue(Long.valueOf(idMapping.getMarriagePartnerNr()) < owners.size() +  married + childs + startValues.getPartnerNumber()+1);
			} 
			
			Arrays.asList(idMapping.getChildrenPartnerNr()) .forEach( partnerNr -> {
				assertTrue(Long.valueOf(partnerNr) > startValues.getPartnerNumber());
				assertTrue(Long.valueOf(partnerNr) < owners.size() +  married + childs + startValues.getPartnerNumber()+1);
				
			} );
			
			assertEquals(idMapping.getChildrenNr().length, idMapping.getChildrenPartnerNr().length);
			
			if( StringUtils.hasText(owner.getSchulnummer())){
				assertEquals(2, idMapping.getCollectiveContractNumbers().length);
			
				assertEquals(owner.getSchulnummer(), idMapping.getSchulnummer());
				assertEquals(owner.getDienststelle(), idMapping.getDienststelle());
				
				assertTrue(Long.valueOf(idMapping.getCollectiveContractNumbers()[0]) > startValues.getPartnerNumber());
				assertTrue(Long.valueOf(idMapping.getCollectiveContractNumbers()[0]) < offices+schools + startValues.getCollectiveContractNumber()+1);
				
				assertTrue(Long.valueOf(idMapping.getCollectiveContractNumbers()[1]) > startValues.getPartnerNumber());
				assertTrue(Long.valueOf(idMapping.getCollectiveContractNumbers()[1]) < offices+schools + startValues.getCollectiveContractNumber()+1);
				
			} else if (StringUtils.hasText(idMapping.getDienststelle())) {
				assertEquals(1, idMapping.getCollectiveContractNumbers().length);
				
				assertNull(owner.getSchulnummer());
				assertEquals(owner.getDienststelle(), idMapping.getDienststelle());
				assertTrue(Long.valueOf(idMapping.getCollectiveContractNumbers()[0]) > startValues.getPartnerNumber());
				assertTrue(Long.valueOf(idMapping.getCollectiveContractNumbers()[0]) < offices+schools + startValues.getCollectiveContractNumber()+1);
			} else {
				assertEquals(0, idMapping.getCollectiveContractNumbers().length);
			}
		});
		
		
	}

}
