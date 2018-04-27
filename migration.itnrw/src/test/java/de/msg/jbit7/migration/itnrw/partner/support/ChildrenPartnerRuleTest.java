package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

class ChildrenPartnerRuleTest {

	private final KindInfo kindInfo = new KindInfo();
	private final StammImpl stamm = new StammImpl();
	private final List<KindInfo> children = Arrays.asList(kindInfo);
	private final Date contractDate = date(1831, 6, 13);
	private final IdMapping idMapping = new IdMapping();

	private final DefaultConversionService conversionService = new DefaultConversionService();
	private final PartnerFactory partnerFactory = new PartnerFactory();

	private final ChildrenPartnerRule childrenPartnerRule = new ChildrenPartnerRule(conversionService, partnerFactory);

	private static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}

	@BeforeEach
	void setup() {
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
		idMapping.setChildrenNr(new Long[] { 1L });
		idMapping.setMandator(4711L);
		idMapping.setProcessNumber(67890L);
		idMapping.setChildrenPartnerNr(new String[] { "8490" });
		idMapping.setMigrationUser("MigUser");
		kindInfo.setVorname("James Clerk");
		stamm.setName("Maxwell");
		kindInfo.setGebDatum(18310613L);
		kindInfo.setSterbedatum(18791105L);
	}

	@Test
	void evalualte() {
		assertTrue(childrenPartnerRule.evaluate(idMapping));
	}

	@Test
	void evalualteNoChildren() {
		idMapping.setChildrenNr(new Long[] {});
		assertFalse(childrenPartnerRule.evaluate(idMapping));
	}

	@Test
	void assignNewPartner() {
		final Collection<Object> results = new ArrayList<>();
		childrenPartnerRule.assignNewPartner(idMapping, children, stamm, contractDate, results);

		assertEquals(1, results.size());
		final PartnerCore partnerCore = (PartnerCore) results.iterator().next();
		assertEquals(idMapping.getMandator(), partnerCore.getMandator());
		assertEquals("0", partnerCore.getDatastate());
		assertEquals(idMapping.getProcessNumber(), partnerCore.getProcessnr());
		assertEquals(Long.valueOf(1), partnerCore.getHistnr());
		assertNull(partnerCore.getRprocessnr());
		assertEquals(contractDate, partnerCore.getDop());
		assertNull(partnerCore.getDor());
		assertEquals(contractDate, partnerCore.getInd());
		assertEquals(Long.valueOf(0), partnerCore.getTerminationflag());
		assertEquals(idMapping.getChildrenPartnerNr()[0], partnerCore.getPartnersNr());
		assertEquals(Long.valueOf(1), partnerCore.getLegalPerson());
		assertEquals(kindInfo.getVorname(), partnerCore.getFirstName());
		assertEquals(stamm.getName(), partnerCore.getSecondName());
		assertEquals(contractDate, partnerCore.getDateOfBirth());
		assertEquals(Long.valueOf(0L), partnerCore.getSex());
		assertEquals("DE", partnerCore.getNationality());
		assertNull(partnerCore.getNationality2());
		assertNull(partnerCore.getNationality3());
		assertEquals(Long.valueOf(0), partnerCore.getProfession());
		assertEquals(PartnerRule.UNDEFINED, partnerCore.getActivityState());
		assertEquals(PartnerRule.BLANK, partnerCore.getTitle());
		assertEquals("de", partnerCore.getLanguageCorrespondence());
		assertEquals(Long.valueOf(0), partnerCore.getVipFlag());
		assertEquals(Long.valueOf(0), partnerCore.getPartnerState());
		assertNull(partnerCore.getNotice());
		assertNull(partnerCore.getNameAddition());
		assertNull(partnerCore.getNameAddition2());
		assertNull(partnerCore.getDefaultAddress());
		assertNull(partnerCore.getDefaultBank());
		assertNull(partnerCore.getDefaultCommunication());
		assertEquals(Long.valueOf(0), partnerCore.getMaritalStatus());
		assertEquals(PartnerRule.BLANK, partnerCore.getSocialInsuranceNumber());
		assertEquals(PartnerRule.BLANK, partnerCore.getSocialInsuranceNumberSp());
		assertEquals(PartnerRule.BLANK, partnerCore.getPlaceOfBirth());
		assertEquals(PartnerRule.BLANK, partnerCore.getBirthName());
		assertEquals(PartnerRule.BLANK, partnerCore.getExtCustomerNumber());
		assertEquals(Long.valueOf(0), partnerCore.getNumberChildren());
		assertEquals(Long.valueOf(0), partnerCore.getAdvertising());
		assertNull(partnerCore.getReasonForChange());
		assertEquals(PartnerRule.BLANK, partnerCore.getEmployer());
		assertEquals(Long.valueOf(0), partnerCore.getSalutation());
		assertEquals(PartnerRule.BLANK, partnerCore.getHealthInsuranceNumber());
		assertEquals(PartnerRule.BLANK, partnerCore.getCitizenNumber());
		assertEquals(Long.valueOf(0), partnerCore.getIdDocumentType());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentNr());
		assertNull(partnerCore.getIdDocumentIssuedDate());
		assertNull(partnerCore.getIdDocumentExpiryDate());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentAuthority());
		assertEquals(PartnerRule.BLANK, partnerCore.getIdDocumentAuthorityCountry());
		assertEquals(Long.valueOf(0), partnerCore.getTenant());
		assertEquals(Long.valueOf(0), partnerCore.getBasicType());
		assertEquals(Long.valueOf(0), partnerCore.getFirstSecondaryType());
		assertEquals(Long.valueOf(0), partnerCore.getSecondSecondaryType());
		assertNull(partnerCore.getCciNumber());
		assertNull(partnerCore.getSector());
		assertNull(partnerCore.getDenomination());
		assertEquals(PartnerRule.BLANK, partnerCore.getPersonnelNr());
		assertEquals(idMapping.getMigrationUser(), partnerCore.getUserid());
		assertNull(partnerCore.getNameAddition3());
		assertNull(partnerCore.getManagement());
		assertNull(partnerCore.getCancellation());
		assertNull(partnerCore.getCancellationDate());
		assertEquals(Long.valueOf(0), partnerCore.getDispatchType());
		assertEquals(date(1879,11,5), partnerCore.getDateOfDeath()  );
		assertNull(partnerCore.getTitleOfNobility());
		assertNull(partnerCore.getHonoraryTitle());
		assertNull(partnerCore.getNamePrefix());
		assertEquals(Long.valueOf(0), partnerCore.getPepFlag());
		assertEquals(Long.valueOf(0), partnerCore.getEuSanctionFlag());
	}
}
