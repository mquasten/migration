package de.msg.jbit7.migration.itnrw.partner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.msg.jbit7.migration.itnrw.mapping.IdGenerationService;
import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingRepository;
import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;
@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
class PartnerServiceIntegrationTest {
	
	private final long MANDATOR = 4711L;
	private final String USER = "Migration";
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	@Autowired
	private IdMappingRepository idMappingRepository;
	@Autowired
	private IdGenerationService idGenerationService;
	
	@Test
	final void createPartnersContract() {
		idGenerationService.createIds(MANDATOR, true, USER);
		partnerService.importPartners(MANDATOR, true);
		final Map<Long, IdMapping> idMapping = idMappingRepository.findAll().stream().collect(Collectors.toMap( mapping -> mapping.getContractNumber(), mapping -> mapping));
		
		final List<PMContract>  contracts = partnerRepository.findContracts(MANDATOR);
		assertContracts(idMapping, contracts);
		assertEquals(idMapping.size(), contracts.size());
		
		final List<PartnerCore>  partners = partnerRepository.findPartner(MANDATOR);
		
		final Map<String, IdMapping> mappingByPartnerNr = idMapping.values().stream().collect(Collectors.toMap( mapping -> mapping.getPartnerNr(), mapping -> mapping));
		
		assertPartner(partners, mappingByPartnerNr);
		assertEquals(idMapping.size(), partners.size());
		
		final List<Address>  addresses = partnerRepository.findAddresses(MANDATOR);
		
		assertEquals(idMapping.size(), addresses.size());
		
		assertAddress(mappingByPartnerNr, addresses);
	}


	private void assertAddress(final Map<String, IdMapping> mappingByPartnerNr, final List<Address> addresses) {
		addresses.forEach(address -> {
			
			final IdMapping mapping = mappingByPartnerNr.get(address.getPartnersNr());
			assertNotNull(mapping);
			assertEquals(mapping.getPartnerNr(), address.getPartnersNr());
			assertEquals(mapping.getProcessNumber(), address.getProcessnr());
			assertEquals(USER, address.getUserid());
		});
	}


	private void assertPartner(final List<PartnerCore> partners, final Map<String, IdMapping> mappingByPartnerNr) {
		partners.forEach(partner -> {
			final IdMapping mapping = mappingByPartnerNr.get(partner.getPartnersNr());
			assertNotNull(mapping);
			assertEquals(mapping.getPartnerNr(), partner.getPartnersNr());
			assertEquals(mapping.getProcessNumber() , partner.getProcessnr());
			assertEquals(Long.valueOf(mapping.getChildrenNr().length), partner.getNumberChildren());
			assertEquals(USER, partner.getUserid());
			
		});
	}


	private void assertContracts(final Map<Long, IdMapping> idMapping, final List<PMContract> contracts) {
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
	}
	
	
	
}
