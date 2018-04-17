package de.msg.jbit7.migration.itnrw.partner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
class PartnerServiceIntegrationTest {
	
	@Autowired
	private PartnerService partnerService;
	
	@Test
	final void createPartners() {
		System.out.println(partnerService);
	}

}
