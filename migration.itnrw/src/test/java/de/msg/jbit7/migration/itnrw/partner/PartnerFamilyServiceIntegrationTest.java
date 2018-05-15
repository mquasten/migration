package de.msg.jbit7.migration.itnrw.partner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
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
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.support.StammRepository;

@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
public class PartnerFamilyServiceIntegrationTest {
	
	
	private final long MANDATOR = 4711L;
	private final String USER = "Migration";
	
	@Autowired
	private PartnerFamilyService partnerFamilyService;
	
	@Autowired
	private IdGenerationService idGenerationService;
	
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private StammRepository stammRepository;
	
	
	@BeforeEach
	final void setup() {
		idGenerationService.createIds(MANDATOR, true, USER);
		
		

	}
	
	@Test
	final void createPartners() {
		partnerRepository.cleanMandator(MANDATOR);
		partnerFamilyService.createPartners(MANDATOR);
		
		final Map<String,PartnerCore> partners = partnerRepository.findPartners(MANDATOR).stream().collect(Collectors.toMap(p -> p.getPartnersNr(), p -> p));
		final Collection<IdMapping> idMappings = idGenerationService.findAll(MANDATOR).values();
		final Collection<IdMapping> idMappingMarriageWithPartner=idMappings.stream().filter(mapping -> StringUtils.hasText(mapping.getMarriagePartnerNr())).collect(Collectors.toList());
		assertEhegatten(partners, idMappingMarriageWithPartner);
		
		
		final Map<String, PartnersRole> partnersRoles = partnerRepository.findPartnersRoles(MANDATOR).stream().collect(Collectors.toMap(role -> role.getRightSide(), role -> role));
		
		assertPartnerRoleEhegatte(idMappingMarriageWithPartner, partnersRoles);	
		
		
		final Map<String,KindInfo> kindInfos = stammRepository.findAllChildren().stream().collect(Collectors.toMap(kindInfo -> kindInfo.getBeihilfenr() + "-" + kindInfo.getLfdKind(), kindInfo -> kindInfo));
		final Collection<IdMapping> idMappingWithChildren = idMappings.stream().filter(mapping -> mapping.getChildrenNr().length > 0).collect(Collectors.toList());
		
		final int counters[] = {0};
		idMappingWithChildren.forEach(mapping ->IntStream.range(0, mapping.getChildrenNr().length).forEach(index -> {
				final String partnerNumber = mapping.getChildrenPartnerNr()[index];
				final PartnerCore partnerCore = partners.get(partnerNumber);
				assertNotNull(partnerCore);
				assertEquals(mapping.getProcessNumber(), partnerCore.getProcessnr());
				final Long kindNr = mapping.getChildrenNr()[index];
				final KindInfo kindInfo = kindInfos.get(mapping.getBeihilfenr() + "-" + kindNr);
				assertNotNull(kindInfo);
				assertEquals(mapping.getBeihilfenr(), kindInfo.getBeihilfenr());
				assertEquals(kindNr, kindInfo.getLfdKind());
				
				final PartnersRole partnersRole = partnersRoles.get(partnerNumber);
				assertNotNull(partnersRole);
				assertEquals(mapping.getProcessNumber(), partnersRole.getProcessnr());
				
				final Integer expectedOrderNr =StringUtils.hasText(mapping.getMarriagePartnerNr()) ? 3+index :2+index;
				assertEquals(expectedOrderNr,  Integer.valueOf(partnersRole.getOrderNrLeftSide()));
				counters[0]++;
			}));
		assertEquals(kindInfos.size(), counters[0]);
		
	}

	private void assertPartnerRoleEhegatte(final Collection<IdMapping> idMappingMarriageWithPartner,
			final Map<String, PartnersRole> partnersRoles) {
		idMappingMarriageWithPartner.forEach(mapping -> {
			final PartnersRole role = partnersRoles.get(mapping.getMarriagePartnerNr());
			assertNotNull(role);
			assertEquals(""+ mapping.getContractNumber(),role.getLeftSide());
			assertEquals(mapping.getProcessNumber(), role.getProcessnr());
		});
	}

	private void assertEhegatten(Map<String, PartnerCore> partners, final Collection<IdMapping> idMappingMarriageWithPartner) {
	
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
