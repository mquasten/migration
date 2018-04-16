package de.msg.jbit7.migration.itnrw.partner;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})
@Disabled
class PartnerRepositoryIntegrationTest {
	
	@Autowired
	private  PartnerRepository partnerRepository;
	
	@Test
	void findAll() {
		System.out.println(partnerRepository.findAll().size());
	}

}
