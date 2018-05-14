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

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingBuilder;
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
	
	
	
	private final Collection<Object> results = new ArrayList<>();
	
	private final PMContract contract = partnerFactory.newContract();
	
	private final PartnerCore partnerCore = partnerFactory.newPartnerCore();
	
	
	
	private final PartnersRole partnersRole = partnerFactory.newPartnersRole();
	
	@BeforeEach
	void setup() {
		contract.setContractNumber(TestUtil.randomLong());
		partnerCore.setPartnersNr(TestUtil.randomString());
		partnersRole.setRightSide(partnerCore.getPartnersNr());
		partnersRole.setLeftSide("" + contract.getContractNumber());
		
		conversionService.addConverter(Long.class, Date.class, new SimpleLongToDateConverter());
	}
	
	@Test
	public final void evaluate() {
		assertTrue(partnerTerminatedRule.evaluate());
	}
	
	
	@Test
	public final void  terminateContractDeath() {
		final StammImpl stamm  =  new StammBuilder().withSterbeDatum().build();
		final IdMapping mapping = IdMappingBuilder.builder().withBeihilfeNr(stamm.getBeihilfenr()).build();
		partnerTerminatedRule.terminateContract(stamm, mapping, Arrays.asList(contract), results);
		
		assertEquals(1, results.size());
		
		final PMContract result = (PMContract) results.stream().findAny().get();
		
		assertTerminatedContract(result, TestUtil.toDate(stamm.getSterbedatum()));
		
	}
	
	@Test
	public final void  terminateContractEnd() {
		
		final StammImpl stamm  =  new StammBuilder().build();
		final IdMapping mapping = IdMappingBuilder.builder().withBeihilfeNr(stamm.getBeihilfenr()).withLastState("END").build();
		partnerTerminatedRule.terminateContract(stamm, mapping, Arrays.asList(contract), results);
		
		assertEquals(1, results.size());
		
		final PMContract result = (PMContract) results.stream().findAny().get();
		
		assertTerminatedContract(result, mapping.getLastStateDate());
	}
	
	@Test
	public final void  terminateContractAus() {
		
		final StammImpl stamm  =  new StammBuilder().build();
		final IdMapping mapping = IdMappingBuilder.builder().withBeihilfeNr(stamm.getBeihilfenr()).withLastState("AUS").build();
		partnerTerminatedRule.terminateContract(stamm, mapping, Arrays.asList(contract), results);
		
		assertEquals(1, results.size());
		
		final PMContract result = (PMContract) results.stream().findAny().get();
		
		assertTerminatedContract(result, mapping.getLastStateDate());
	}
	
	@Test
	public final void  terminateContractLfd() {
		
		final StammImpl stamm  =  new StammBuilder().build();
		final IdMapping mapping = IdMappingBuilder.builder().withBeihilfeNr(stamm.getBeihilfenr()).withLastState("LFD").build();
		partnerTerminatedRule.terminateContract(stamm, mapping, Arrays.asList(contract), results);
		
		assertEquals(0, results.size());
		
		
	}


	private void assertTerminatedContract(final PMContract result, final Date end) {
		assertEquals(contract.getContractNumber(), result.getContractNumber());
		assertEquals(end, result.getDop());
		assertEquals(TestUtil.nextDay(end), result.getInd());
		assertEquals(Long.valueOf(900), result.getReasonForChange());
		assertEquals(TestUtil.nextDay(end), result.getTerminationDate());
		assertEquals(Long.valueOf(1), result.getTerminationflag());
		assertEquals(Long.valueOf(2L), result.getHistnr());
	}
	

	@Test
	public final void terminateVNDeath() {
		final StammImpl stamm  =  new StammBuilder().withSterbeDatum().build();
		final FamilyMembers familyMembers = new FamilyMembers();
		familyMembers.put(partnerCore.getPartnersNr(), TestUtil.toDate(stamm.getSterbedatum()));
		partnerTerminatedRule.terminatePartner(familyMembers, Arrays.asList(partnerCore), results);
		assertEquals(1, results.size());
		final PartnerCore result = (PartnerCore) results.stream().findAny().get();
		
		assertTerminatedPartner(result, TestUtil.toDate(stamm.getSterbedatum()), true);
	}

	@Test
	public final void terminateEndAus() {
		final Date terminationdate = new Date();
		
		final FamilyMembers familyMembers = new FamilyMembers();
		familyMembers.assignTerminationdateIfNotExists(terminationdate);
		
		partnerTerminatedRule.terminatePartner(familyMembers, Arrays.asList(partnerCore), results);
		assertEquals(1, results.size());
		
		final PartnerCore result = (PartnerCore) results.stream().findAny().get();
		assertTerminatedPartner(result, terminationdate, false);
	}
	
	private void assertTerminatedPartner(final PartnerCore result, final Date end, final boolean isDeath) {
		assertEquals(partnerCore.getPartnersNr(), result.getPartnersNr());
		assertEquals(end, result.getDop());
		assertEquals(TestUtil.nextDay(end), result.getInd());
		assertEquals(Long.valueOf(900), result.getReasonForChange());
		assertEquals(Long.valueOf(1), result.getTerminationflag());
		assertEquals(Long.valueOf(2L), result.getHistnr());
		
		if( isDeath) {
			assertEquals(end, result.getDateOfDeath());
		}
	}
	
	@Test
	public final void terminatePartnersRoleDeath() {
		final StammImpl stamm  =  new StammBuilder().withSterbeDatum().build();
		final FamilyMembers familyMembers = new FamilyMembers();
		familyMembers.put(partnerCore.getPartnersNr(), TestUtil.toDate(stamm.getSterbedatum()));
		partnerTerminatedRule.terminatePartnersRole(familyMembers, Arrays.asList(partnersRole), results);
		
		assertEquals(1, results.size());
		final PartnersRole result =  (PartnersRole) results.stream().findAny().get();
		
		assertTerminedRole(result, TestUtil.toDate(stamm.getSterbedatum()));
		
	}
	
	@Test
	public final void terminatePartnersRoleEndAus() {
		final FamilyMembers familyMembers = new FamilyMembers();
		final Date terminationdate = new Date();
		familyMembers.assignTerminationdateIfNotExists(terminationdate);
		partnerTerminatedRule.terminatePartnersRole(familyMembers, Arrays.asList(partnersRole), results);
		
		assertEquals(1, results.size());
		final PartnersRole result =  (PartnersRole) results.stream().findAny().get();
		assertTerminedRole(result, terminationdate);
	}

	private void assertTerminedRole(final PartnersRole result, final Date end) {
		assertEquals(partnersRole.getRightSide(), result.getRightSide());
		assertEquals(partnersRole.getLeftSide(), result.getLeftSide());
		assertEquals(end, result.getDop());
		assertEquals(TestUtil.nextDay(end), result.getInd());
		assertEquals(Long.valueOf(1), result.getTerminationflag());
		assertEquals(Long.valueOf(2L), result.getHistnr());
	}
	
}
