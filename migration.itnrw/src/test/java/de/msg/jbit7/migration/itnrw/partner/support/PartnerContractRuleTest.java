package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingBuilder;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

public class PartnerContractRuleTest {
	private final PartnerFactory partnerFactory = new PartnerFactory();
	private final DefaultConversionService conversionService = new DefaultConversionService();
	private final PartnerContractRule partnerContractRule = new PartnerContractRule(partnerFactory, conversionService);

	private final IdMapping idMapping = IdMappingBuilder.builder().build();
	

	private final Date contractDate = date(1643,1,  4);

	@BeforeEach
	void setup() {
	
		
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
	}

	@Test
	void evaluate() {
		assertTrue(partnerContractRule.evaluate());
	}

	@Test
	void assignContract() {
		final Collection<Object> results = new ArrayList<>();
		partnerContractRule.assignContract(idMapping, new StammImpl(), contractDate, results);

		assertEquals(1, results.size());

		final PMContract pmContract = (PMContract) results.iterator().next();

		assertNewContract(pmContract, false);

	}

	private void assertNewContract(final PMContract pmContract, final boolean withoutHist) {
		assertEquals(contractDate, pmContract.getBeginOfContract());
		assertEquals(idMapping.getCollectiveContractNumbers()[0], pmContract.getCollectiveContractNumber());
		assertEquals(idMapping.getContractNumber(), pmContract.getContractNumber());

		assertEquals(Long.valueOf(5L), pmContract.getContractType());

		assertEquals("0", pmContract.getDatastate());
		

		assertNull(pmContract.getDor());
		assertNull(pmContract.getInternalNumberCollContract());

		assertEquals(idMapping.getMandator(), pmContract.getMandator());

		assertEquals(Long.valueOf(0L), pmContract.getMemberOfStaff());

		assertEquals(Long.valueOf(0l), pmContract.getPolicyConfirmationFlag());
		assertEquals("BB" + idMapping.getContractNumber(), pmContract.getPolicyNumber());

		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText1());
		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText2());
		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText2());
		
		assertEquals(idMapping.getProcessNumber(), pmContract.getProcessnr());

		
		assertEquals(Long.valueOf(1L), pmContract.getRiskCarrier());

		assertNull(pmContract.getRprocessnr());
	
		if( withoutHist) {
			return;
		}
		
		assertEquals(contractDate, pmContract.getDop());
		assertEquals(Long.valueOf(1L), pmContract.getHistnr());
		assertEquals(contractDate, pmContract.getInd());
		assertEquals(Long.valueOf(800L), pmContract.getPrionr());
		assertEquals(Long.valueOf(100L), pmContract.getReasonForChange());
		assertNull(pmContract.getTerminationDate());
		assertEquals(Long.valueOf(0L), pmContract.getTerminationflag());
	}
	

	@Test
	void assignContractDead() {
		final StammImpl stamm = new StammImpl();
		
		final Date dateOfDeath = date(1727, 3, 31);
		stamm.setSterbedatum(17270331L);
		
		final List<Object> results = new ArrayList<>();
		partnerContractRule.assignContract(idMapping,stamm, contractDate, results);

		assertEquals(2, results.size());
		
		assertNewContract((PMContract) results.get(0), false);
		
		final PMContract terminatedContract = (PMContract) results.get(1);
		
		assertNewContract(terminatedContract, true);
		assertEquals(dateOfDeath, terminatedContract.getDop());
		assertEquals(dateOfDeath, terminatedContract.getInd());
		assertEquals(dateOfDeath, terminatedContract.getTerminationDate());
		assertEquals(Long.valueOf(2), terminatedContract.getHistnr());
		assertEquals(Long.valueOf(900), terminatedContract.getReasonForChange());
		assertEquals(Long.valueOf(900), terminatedContract.getPrionr());
		assertEquals(Long.valueOf(1), terminatedContract.getTerminationflag());
		
		
	}
	
	

	private static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
}
