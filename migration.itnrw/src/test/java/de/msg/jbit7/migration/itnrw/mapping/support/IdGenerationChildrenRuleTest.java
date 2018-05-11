package de.msg.jbit7.migration.itnrw.mapping.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.KindInfoBuilder;

public class IdGenerationChildrenRuleTest {

	
	private static final String SECOND_PARTNER_NUMBER = "1002";

	private static final String FIRST_PARTNER_NUMBER = "1001";

	private final IdGenerationChildrenRule idGenerationChildrenRule = new IdGenerationChildrenRule();
	
	private final KindInfo first = KindInfoBuilder.builder().withLfdKind(1L).build();
	private final KindInfo second = KindInfoBuilder.builder().withLfdKind(2L).build();
	
	private final Counters counters = Mockito.mock(Counters.class);
	
	@BeforeEach
	void setup() {
		Mockito.when(counters.nextPartnerNumber()).thenReturn(FIRST_PARTNER_NUMBER , SECOND_PARTNER_NUMBER);
	}
	
	@Test
	void existing() {
		assertTrue(idGenerationChildrenRule.existing(Arrays.asList(first, second)));
	}
	@Test
	void existingNoChildren() {
		assertFalse(idGenerationChildrenRule.existing(Collections.emptyList()));
	}
	
	@Test
	void  assignValues() {
		final IdMapping mapping = new IdMapping();
		idGenerationChildrenRule.assignValues(mapping, counters, Arrays.asList(first, second));
		
		assertEquals(2, mapping.getChildrenPartnerNr().length);
		assertEquals(2, mapping.getChildrenNr().length);
		
		assertEquals(FIRST_PARTNER_NUMBER, mapping.getChildrenPartnerNr()[0]);
		assertEquals(SECOND_PARTNER_NUMBER, mapping.getChildrenPartnerNr()[1]);
		
		assertEquals(first.getLfdKind(), mapping.getChildrenNr()[0]);
		assertEquals(second.getLfdKind(), mapping.getChildrenNr()[1]);
	}
}
