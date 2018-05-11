package de.msg.jbit7.migration.itnrw.mapping.support;

import static de.msg.jbit7.migration.itnrw.util.TestUtil.assertEqualsRequired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.stamm.HiAntragsteller;
import de.msg.jbit7.migration.itnrw.stamm.HiAntragstellerBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.util.TestUtil;

class IdGenerationOwnerRuleTest {
	
	private static final Long COLLECTIVE_CONTRACT_SCHOOL = 4001L;
	private static final Long COLLECTIVE_CONTRACT_OFFICE = 4002L;

	private static final Long CONTRACT_NUMBER = 3001L;

	private static final Long PROCESS_NUMBER = 2001L;

	private static final String PARTNER_NUMBER = "1001";

	private static final Long MANDATOR = 4711L;

	final DefaultConversionService conversionService = new DefaultConversionService();
	
	private IdGenerationOwnerRule idGenerationOwnerRule = new IdGenerationOwnerRule(conversionService);
	
	private StammImpl stamm = StammBuilder.builder().build();
	
	private Counters counters = Mockito.mock(Counters.class);
	
	private HiAntragsteller hiAntragssteller = HiAntragstellerBuilder.builder().withBeihilfenr(stamm.getBeihilfenr()).withBeginn(new Date()).withWertId(18L).withWert("AUS").build();
			
			
	
	@BeforeEach
	void setup() {
		Mockito.when(counters.mandator()).thenReturn(MANDATOR);
		Mockito.when(counters.nextPartnerNumber()).thenReturn(PARTNER_NUMBER);
		Mockito.when(counters.nextProcessNumber()).thenReturn(PROCESS_NUMBER);
		Mockito.when(counters.nextContactNumber()).thenReturn(CONTRACT_NUMBER);
		Mockito.when(counters.nextCollectiveContractNumberSchool(stamm.getSchulnummer())).thenReturn(COLLECTIVE_CONTRACT_SCHOOL);
		Mockito.when(counters.nextCollectiveContractNumberOffice(stamm.getDienststelle())).thenReturn(COLLECTIVE_CONTRACT_OFFICE);
		conversionService.addConverter( Long.class, Date.class, new SimpleLongToDateConverter());
	}
	
	@Test
	void assignValues() {
		IdMapping mapping = new IdMapping();
		idGenerationOwnerRule.assignValues(stamm, Optional.of(hiAntragssteller), mapping, counters);
		
		assertEqualsRequired(TestUtil.toDate(hiAntragssteller.getBeginnDatum()), mapping.getLastStateDate());
		assertEqualsRequired(hiAntragssteller.getWertChar(), mapping.getLastState());
		assertEquals(MANDATOR, mapping.getMandator());
		assertEqualsRequired(stamm.getBeihilfenr(), mapping.getBeihilfenr());
		assertEquals(PARTNER_NUMBER, mapping.getPartnerNr());
		assertEquals(PROCESS_NUMBER, mapping.getProcessNumber());
		assertEquals(CONTRACT_NUMBER, mapping.getContractNumber());
		assertEquals(2,  mapping.getCollectiveContractNumbers().length);
		assertEquals(COLLECTIVE_CONTRACT_SCHOOL, mapping.getCollectiveContractNumbers()[0]);
		assertEquals(COLLECTIVE_CONTRACT_OFFICE, mapping.getCollectiveContractNumbers()[1]);
		assertEqualsRequired(stamm.getDienststelle(), mapping.getDienststelle());
		assertEqualsRequired(stamm.getSchulnummer(), mapping.getSchulnummer());
	}
	
	@Test
	void migrationRequiredDeathBefore13Months() {
		final StammImpl stamm = StammBuilder.builder().withSterbeDatum(TestUtil.daysBack(new Date(), 400)).build();
		assertFalse(idGenerationOwnerRule.migrationRequired(stamm, Optional.empty()));
	}
	
	@Test
	void migrationRequiredDeathBefore12Months() {
		final StammImpl stamm = StammBuilder.builder().withSterbeDatum(TestUtil.daysBack(new Date(), 365)).build();
		assertTrue(idGenerationOwnerRule.migrationRequired(stamm,  Optional.empty()));
	}
	
	@Test
	void migrationRequiredAlive() {
		final StammImpl stamm = StammBuilder.builder().build();
		assertTrue(idGenerationOwnerRule.migrationRequired(stamm,  Optional.empty()));
	}
	
	@Test
	void migrationRequiredEndBefore13Months() {
		final HiAntragsteller hiAntragssteller = HiAntragstellerBuilder.builder().withBeginn(TestUtil.daysBack(new Date(), 400)).withWert("END").build();
		final StammImpl stamm = StammBuilder.builder().build();
		assertFalse(idGenerationOwnerRule.migrationRequired(stamm,  Optional.of(hiAntragssteller)));
		
	}
	
	@Test
	void migrationRequiredAusBefore13Months() {
		final HiAntragsteller hiAntragssteller = HiAntragstellerBuilder.builder().withBeginn(TestUtil.daysBack(new Date(), 400)).withWert("AUS").build();
		final StammImpl stamm = StammBuilder.builder().build();
		assertFalse(idGenerationOwnerRule.migrationRequired(stamm,  Optional.of(hiAntragssteller)));
		
	}
	
	@Test
	void migrationRequiredLfdBefore13Months() {
		final HiAntragsteller hiAntragssteller = HiAntragstellerBuilder.builder().withBeginn(TestUtil.daysBack(new Date(), 400)).withWert("LFD").build();
		final StammImpl stamm = StammBuilder.builder().build();
		assertTrue(idGenerationOwnerRule.migrationRequired(stamm,  Optional.of(hiAntragssteller)));
	}
	
	@Test
	void migrationRequiredWrongDate() {
		final HiAntragsteller hiAntragssteller =  HiAntragstellerBuilder.builder().withBeginn(4711L).withWert("END").build();
		final StammImpl stamm = StammBuilder.builder().build();
		assertTrue(idGenerationOwnerRule.migrationRequired(stamm,  Optional.of(hiAntragssteller)));
	}

}
