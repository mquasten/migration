package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

public class PartnerContractRuleTest {
	private final PartnerFactory partnerFactory = new PartnerFactory();
	private final DefaultConversionService conversionService = new DefaultConversionService();
	private final PartnerContractRule partnerContractRule = new PartnerContractRule(partnerFactory, conversionService);

	private final IdMapping idMapping = new IdMapping();
	

	private final Date contractDate = date(1642, 12, 25);

	@BeforeEach
	void setup() {
		idMapping.setContractNumber(19680528L);
		idMapping.setCollectiveContractNumbers(new Long[] { 1234L });
		idMapping.setMandator(4711L);
		idMapping.setProcessNumber(987631L);
		
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
	}

	@Test
	void evaluate() {
		assertTrue(partnerContractRule.evaluate());
	}

	@Test
	void assignContract() {
		final Collection<Object> results = new ArrayList<>();
		partnerContractRule.assignNewContract(idMapping, new StammImpl(), contractDate, results);

		assertEquals(1, results.size());

		final PMContract pmContract = (PMContract) results.iterator().next();

		assertEquals(contractDate, pmContract.getBeginOfContract());
		assertEquals(idMapping.getCollectiveContractNumbers()[0], pmContract.getCollectiveContractNumber());
		assertEquals(idMapping.getContractNumber(), pmContract.getContractNumber());

		assertEquals(Long.valueOf(5L), pmContract.getContractType());

		assertEquals("0", pmContract.getDatastate());
		assertEquals(contractDate, pmContract.getDop());

		assertNull(pmContract.getDor());

		assertEquals(Long.valueOf(1L), pmContract.getHistnr());

		assertEquals(contractDate, pmContract.getInd());

		assertNull(pmContract.getInternalNumberCollContract());

		assertEquals(idMapping.getMandator(), pmContract.getMandator());

		assertEquals(Long.valueOf(0L), pmContract.getMemberOfStaff());

		assertEquals(Long.valueOf(0l), pmContract.getPolicyConfirmationFlag());
		assertEquals("BB" + idMapping.getContractNumber(), pmContract.getPolicyNumber());

		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText1());
		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText2());
		assertEquals(PartnerFactory.BLANK, pmContract.getPostingText2());
		assertEquals(Long.valueOf(800L), pmContract.getPrionr());
		assertEquals(idMapping.getProcessNumber(), pmContract.getProcessnr());

		assertEquals(Long.valueOf(100L), pmContract.getReasonForChange());
		assertEquals(Long.valueOf(1L), pmContract.getRiskCarrier());

		assertNull(pmContract.getRprocessnr());
		assertNull(pmContract.getTerminationDate());
		assertEquals(Long.valueOf(0L), pmContract.getTerminationflag());

	}

	private static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
}