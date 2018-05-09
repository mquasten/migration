package de.msg.jbit7.migration.itnrw.partner.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import de.msg.jbit7.migration.itnrw.mapping.support.SimpleLongToDateConverter;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnerFamilyFacts.FamilyMembers;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.stamm.StammBuilder;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;
import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class PartnerTerminatedRuleTest {
	
	private final PartnerFactory partnerFactory = new PartnerFactory();
	private final DefaultConversionService conversionService = new DefaultConversionService();
	
	
	private final PartnerTerminatedRule partnerTerminatedRule = new PartnerTerminatedRule(partnerFactory, conversionService);
	
	private final StammImpl stamm  =  new StammBuilder().withSterbeDatum().build();
	
	private final Collection<Object> results = new ArrayList<>();
	
	private final PMContract contract = partnerFactory.newContract();
	
	private final PartnerCore partnerCore = partnerFactory.newPartnerCore();
	private final FamilyMembers familyMembers = new FamilyMembers();
	
	
	private final PartnersRole partnersRole = partnerFactory.newPartnersRole();
	
	@BeforeEach
	void setup() {
		contract.setContractNumber(TestUtil.randomLong());
		partnerCore.setPartnersNr(TestUtil.randomString());
		partnersRole.setRightSide(partnerCore.getPartnersNr());
		partnersRole.setLeftSide("" + contract.getContractNumber());
		familyMembers.put(partnerCore.getPartnersNr(), TestUtil.toDate(stamm.getSterbedatum()));
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
	}
	
	@Test
	public final void evaluate() {
		assertTrue(partnerTerminatedRule.evaluate());
	}
	
	
	@Test
	public final void  terminateContract() {
		partnerTerminatedRule.terminateContract(stamm,Arrays.asList(contract), results);
		
		assertEquals(1, results.size());
		
		final PMContract result = (PMContract) results.stream().findAny().get();
		
		assertTerminatedContract(result);
		
	}

	private void assertTerminatedContract(final PMContract result) {
		assertEquals(contract.getContractNumber(), result.getContractNumber());
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), result.getDop());
		assertEquals(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())), result.getInd());
		assertEquals(Long.valueOf(900), result.getReasonForChange());
		assertEquals(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())), result.getTerminationDate());
		assertEquals(Long.valueOf(1), result.getTerminationflag());
		assertEquals(Long.valueOf(2L), result.getHistnr());
	}
	

	@Test
	public final void terminatePartner() {
		partnerTerminatedRule.terminatePartner(familyMembers, Arrays.asList(partnerCore), results);
		assertEquals(1, results.size());
		final PartnerCore result = (PartnerCore) results.stream().findAny().get();
		
		assertTerminatedPartner(result);
	}

	private void assertTerminatedPartner(final PartnerCore result) {
		assertEquals(partnerCore.getPartnersNr(), result.getPartnersNr());
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), result.getDop());
		assertEquals(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())), result.getInd());
		assertEquals(Long.valueOf(900), result.getReasonForChange());
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), result.getDateOfDeath());
		assertEquals(Long.valueOf(1), result.getTerminationflag());
		assertEquals(Long.valueOf(2L), result.getHistnr());
	}
	
	@Test
	public final void terminatePartnersRole() {
		partnerTerminatedRule.terminatePartnersRole(familyMembers, Arrays.asList(partnersRole), results);
		
		assertEquals(1, results.size());
		final PartnersRole result =  (PartnersRole) results.stream().findAny().get();
		
		assertTerminedRole(result);
		
	}

	private void assertTerminedRole(final PartnersRole result) {
		assertEquals(partnersRole.getRightSide(), result.getRightSide());
		assertEquals(partnersRole.getLeftSide(), result.getLeftSide());
		assertEquals(TestUtil.toDate(stamm.getSterbedatum()), result.getDop());
		assertEquals(TestUtil.nextDay(TestUtil.toDate(stamm.getSterbedatum())), result.getInd());
		assertEquals(Long.valueOf(1), result.getTerminationflag());
		assertEquals(Long.valueOf(2L), result.getHistnr());
	}
	
}
