package de.msg.jbit7.migration.itnrw.partner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.msg.jbit7.migration.itnrw.partner.support.PartnerRepository;

@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
public class PartnerFamilyServiceIntegrationTest {
	
	private final long MANDATOR = 4711L;

	
	@Autowired
	private PartnerFamilyService partnerFamilyService;
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	@Test
	final void createPartners() {
		partnerRepository.cleanMandator(MANDATOR);
		partnerFamilyService.createPartners(MANDATOR);
		
	}

}
