package de.msg.jbit7.migration.itnrw.mapping.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.EhegatteBuilder;

class IdGenerationMarriagePartnerRuleTest {

	private static final String PARTNER_NUMBER = "1001";

	private final IdGenerationMarriagePartnerRule idGenerationMarriagePartnerRule = new IdGenerationMarriagePartnerRule();
	
	private final Ehegatte ehegatte = EhegatteBuilder.builder().build();
	
	private final Counters counters = Mockito.mock(Counters.class);
	
	@BeforeEach
	void setup() {
		Mockito.when(counters.nextPartnerNumber()).thenReturn(PARTNER_NUMBER);
	}
	
	@Test
	void married() {
		assertTrue(idGenerationMarriagePartnerRule.married(Optional.of(ehegatte)));
	}
	
	@Test
	void notMarried() {
		assertFalse(idGenerationMarriagePartnerRule.married(Optional.empty()));
	}
	
	@Test
	void assignValues() {
		final IdMapping idMapping = new IdMapping();
		idGenerationMarriagePartnerRule.assignValues(idMapping, counters);
		
		assertEquals(PARTNER_NUMBER, idMapping.getMarriagePartnerNr());
	}
	
}
