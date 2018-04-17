package de.msg.jbit7.migration.itnrw.partner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingRepository;
import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;
@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
class PartnerServiceIntegrationTest {
	
	private final long MANDATOR = 4711L;
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	@Autowired
	private IdMappingRepository idMappingRepository;
	
	@Test
	final void createPartners() {
		
		partnerService.importPartners(MANDATOR, true);
		final Map<Long, IdMapping> idMapping = idMappingRepository.findAll().stream().collect(Collectors.toMap( mapping -> mapping.getContractNumber(), mapping -> mapping));
		
		final List<PMContract>  contracts = partnerRepository.findAllContracts();
		assertTrue(contracts.size() > 0 );
		contracts.forEach(contract -> {
			assertTrue(idMapping.containsKey(contract.getContractNumber()));
			final IdMapping mapping = idMapping.get(contract.getContractNumber());
			assertEquals(mapping.getContractNumber(), contract.getContractNumber());
			if( mapping.getBeihilfenr() != 999) {
			    assertEquals(mapping.getCollectiveContractNumbers()[0], contract.getCollectiveContractNumber());
			}
			assertEquals(mapping.getMandator(), contract.getMandator());
			
			assertEquals(mapping.getProcessNumber(), contract.getProcessnr());
		});
		
		assertEquals(idMapping.size(), contracts.size());
	}

}
