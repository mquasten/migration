package de.msg.jbit7.migration.itnrw.partner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
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
		partnerRepository.cleanMandator(MANDATOR);
		
		idGenerationService.createIds(MANDATOR, true, USER);
		partnerService.importPartners(MANDATOR, true);
		final Map<Long, IdMapping> idMapping = idMappingRepository.findAll(MANDATOR).stream().collect(Collectors.toMap( mapping -> mapping.getContractNumber(), mapping -> mapping));
		
		Map<String, IdMapping> recipients = idMapping.values().stream().filter(mapping -> mapping.getRecipient()!=null ).collect(Collectors.toMap(mapping -> mapping.getRecipient(), mapping -> mapping));
		
		
		final List<PMContract>  contracts = partnerRepository.findContracts(MANDATOR);
		assertContracts(idMapping, contracts);
		assertEquals(idMapping.size(), contracts.size());
		
		final List<PartnerCore>  partners = partnerRepository.findPartners(MANDATOR);
		
		final Map<String, IdMapping> mappingByPartnerNr = idMapping.values().stream().collect(Collectors.toMap( mapping -> mapping.getPartnerNr(), mapping -> mapping));
		
		assertPartner(partners, mappingByPartnerNr, recipients);
		assertEquals(idMapping.size() + recipients.size(), partners.size());
		
		final List<Address>  addresses = partnerRepository.findAddresses(MANDATOR);
		
		assertEquals(idMapping.size(), addresses.size());
		
		assertAddress(mappingByPartnerNr, addresses);
		
		final List<Bank> banks = partnerRepository.findBanks(MANDATOR);
		final double missingBanks = (banks.size() - idMapping.size()) / (1d*idMapping.size());
		// der relative Fehler sollte 0 sein!, mindestens eine Bank ist doch wohl erforderlich.  
		assertTrue(-0.1d <  missingBanks);
		assertBanks(mappingByPartnerNr, banks);
		
		final List<Communication> communications = partnerRepository.findCommunications(MANDATOR);
		assertCommunications(mappingByPartnerNr, communications);
		
		final List<CommunicationRole> communicationRoles  = partnerRepository.findCommunicationRoles(MANDATOR);
		assertCommunicationRole(mappingByPartnerNr, communicationRoles);
		
		final List<PartnersRole> partnersRole =  partnerRepository.findPartnersRoles(MANDATOR);
		
		final Collection<PartnersRole> rolesPH = partnersRole.stream().filter(role -> role.getRole().equalsIgnoreCase("PH")).collect(Collectors.toList());
		final Map<String,PartnersRole> rolesIP = partnersRole.stream().filter(role -> role.getRole().equalsIgnoreCase("IP")).collect(Collectors.toMap(role -> role.getRightSide(), role -> role ));
		
		assertEquals(idMapping.size(), rolesPH.size());
		assertEquals(idMapping.size(), rolesIP.size());
		
		assertPartnerRole(mappingByPartnerNr, rolesPH, rolesIP);
	}


	private void assertPartnerRole(final Map<String, IdMapping> mappingByPartnerNr,
			final Collection<PartnersRole> rolesPH, final Map<String, PartnersRole> rolesIP) {
		rolesPH.forEach(role -> {
			final IdMapping mapping = mappingByPartnerNr.get( role.getRightSide());
			assertNotNull(mapping);
			assertEquals(mapping.getProcessNumber(), role.getProcessnr());
			assertEquals(mapping.getPartnerNr(), role.getRightSide());
			assertEquals("" + mapping.getContractNumber(), role.getLeftSide());
			final PartnersRole ip = rolesIP.get(mapping.getPartnerNr());
			assertNotNull(ip);
			assertEquals(mapping.getPartnerNr(), ip.getRightSide());
			assertEquals(mapping.getProcessNumber(), ip.getProcessnr());
			assertEquals("" + mapping.getContractNumber(), ip.getLeftSide());
			//assertNotNull(mapping);
		});
	}


	private void assertCommunicationRole(final Map<String, IdMapping> mappingByPartnerNr,
			final List<CommunicationRole> communicationRoles) {
		communicationRoles.forEach(role -> {
			final IdMapping mapping = mappingByPartnerNr.get("" + role.getCommunicationKey());
			assertEquals(mapping.getPartnerNr(), "" + role.getCommunicationKey());
			assertEquals(mapping.getPartnerNr(), "" + role.getCommunicationRoleKey());
			assertEquals(mapping.getProcessNumber(), role.getProcessnr());
			
		});
	}


	private void assertCommunications(final Map<String, IdMapping> mappingByPartnerNr,
			final List<Communication> communications) {
		communications.forEach(communication -> {
			final IdMapping mapping = mappingByPartnerNr.get(communication.getPartnersNr());
			assertNotNull(mapping);
			assertEquals(mapping.getPartnerNr(), communication.getPartnersNr());
			assertTrue(communication.getCommunicationNr().equals("1") || communication.getCommunicationNr().equals("2") );
			assertEquals(mapping.getProcessNumber(), communication.getProcessnr());
			
		});
	}


	private void assertBanks(final Map<String, IdMapping> mappingByPartnerNr, final List<Bank> banks) {
		banks.forEach(bank -> {
			final IdMapping mapping = mappingByPartnerNr.get(bank.getPartnersNr());
			assertNotNull(mapping);
			assertEquals(mapping.getPartnerNr(), bank.getPartnersNr());
			assertEquals(mapping.getProcessNumber(), bank.getProcessnr());
			
		});
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


	private void assertPartner(final List<PartnerCore> partners, final Map<String, IdMapping> mappingByPartnerNr, final Map<String, IdMapping> recipients) {
		partners.forEach(partner -> {
			
			if(  ! recipients.containsKey(partner.getPartnersNr())) {
			final IdMapping mapping = mappingByPartnerNr.get(partner.getPartnersNr());
			assertNotNull(mapping);
			assertEquals(mapping.getPartnerNr(), partner.getPartnersNr());
			assertEquals(mapping.getProcessNumber() , partner.getProcessnr());
			assertEquals(Long.valueOf(mapping.getChildrenNr().length), partner.getNumberChildren());
			assertEquals(USER, partner.getUserid());
			} else {
				final IdMapping mapping = recipients.get(partner.getPartnersNr());
				assertNotNull(mapping);
				assertEquals(mapping.getRecipient(), partner.getPartnersNr());
				assertEquals(mapping.getProcessNumber() , partner.getProcessnr());
				assertEquals(Long.valueOf(0), partner.getNumberChildren());
				assertEquals(USER, partner.getUserid());
			}
			
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
