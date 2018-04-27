package de.msg.jbit7.migration.itnrw.partner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.mapping.IdGenerationService;
import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;

@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
public class PartnerFamilyServiceIntegrationTest {
	
	private final long MANDATOR = 4711L;

	
	@Autowired
	private PartnerFamilyService partnerFamilyService;
	
	@Autowired
	private IdGenerationService idGenerationService;
	
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private StammRepository stammRepository;
	
	@Test
	final void createPartners() {
		partnerRepository.cleanMandator(MANDATOR);
		partnerFamilyService.createPartners(MANDATOR);
		
		Map<String,PartnerCore> partners = partnerRepository.findPartners(MANDATOR).stream().collect(Collectors.toMap(p -> p.getPartnersNr(), p -> p));
		final Collection<IdMapping> idMappings = idGenerationService.findAll().values();
		
		assertEhegatten(partners, idMappings);
		
		
	}

	private void assertEhegatten(Map<String, PartnerCore> partners, final Collection<IdMapping> idMappings) {
		final Collection<IdMapping> idMappingMarriageWithPartner=idMappings.stream().filter(mapping -> StringUtils.hasText(mapping.getMarriagePartnerNr())).collect(Collectors.toList());
		final Map<Long,Ehegatte> ehegatten = stammRepository.findAllEhegatte().stream().collect(Collectors.toMap(ehegatte -> ehegatte.getBeihilfenr(), ehegatte -> ehegatte));
		assertEquals(ehegatten.size(), idMappingMarriageWithPartner.size());
		idMappingMarriageWithPartner.forEach(mapping -> {
			final PartnerCore partner = partners.get(mapping.getMarriagePartnerNr());
			assertNotNull(partner);
			assertEquals(mapping.getMarriagePartnerNr(), partner.getPartnersNr());
			assertEquals(mapping.getProcessNumber(), partner.getProcessnr());
			assertTrue(ehegatten.containsKey(mapping.getBeihilfenr()));
		});
	}

}
