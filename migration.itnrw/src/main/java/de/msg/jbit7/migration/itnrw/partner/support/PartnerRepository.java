package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import de.msg.jbit7.migration.itnrw.partner.Address;
import de.msg.jbit7.migration.itnrw.partner.Bank;
import de.msg.jbit7.migration.itnrw.partner.Communication;
import de.msg.jbit7.migration.itnrw.partner.CommunicationRole;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.partner.PartnersRole;
import de.msg.jbit7.migration.itnrw.util.BeanUtil;

@Repository
public class PartnerRepository {

	private static final String CONTRACT_NUMBER_PARAMETER = "ContractNr";
	private static final String PARTNERS_NUMBER_PARAMETER = "partnersNr";
	private static final String MANDATOR_NAME = "mandator";
	private static final String DELETE_CONTRACT_BY_MANDATOR_SQL = String
			.format("DELETE FROM PM_CONTRACT WHERE MANDATOR = :%s", MANDATOR_NAME);
	private static final String DELETE_PARTNER_BY_MANDATOR_SQL = String
			.format("DELETE FROM PARTNERS_CORE WHERE MANDATOR = :%s", MANDATOR_NAME);
	
	private static final String DELETE_ADDRESS_BY_MANDATOR_SQL = String
			.format("DELETE FROM ADDRESS WHERE MANDATOR = :%s", MANDATOR_NAME);
	
	
	private static final String DELETE_BANK_BY_MANDATOR_SQL = String
			.format("DELETE FROM BANK WHERE MANDATOR = :%s", MANDATOR_NAME);
	
	
	private static final String DELETE_COMMUNICATION_BY_MANDATOR_SQL = String
			.format("DELETE FROM COMMUNICATION WHERE MANDATOR = :%s", MANDATOR_NAME);
	
	private static final String DELETE_COMMUNICATION_ROLE_BY_MANDATOR_SQL = String
			.format("DELETE FROM COMMUNICATION_ROLE WHERE MANDATOR = :%s", MANDATOR_NAME);
	
	private static final String DELETE_PARTNERS_ROLE_ROLE_BY_MANDATOR_SQL = String
			.format("DELETE FROM PARTNERS_ROLE WHERE MANDATOR = :%s", MANDATOR_NAME);
	
	
	private final NamedParameterJdbcOperations namedParameterJdbcOperations;

	

	
	@Autowired
	PartnerRepository(
			@Qualifier("healthFactoryJDBCTemplate") final NamedParameterJdbcOperations namedParameterJdbcOperations) {
		this.namedParameterJdbcOperations = namedParameterJdbcOperations;
	}

	public final List<PMContract> findContracts(long mandator) {
		final String sql = String.format("SELECT * from PM_CONTRACT where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(PMContract.class));
	}

	
	public final void cleanMandator(final long mandator) {
		final Map<String, Long> parameters = new HashMap<>();
		parameters.put(MANDATOR_NAME, mandator);
		namedParameterJdbcOperations.update(DELETE_CONTRACT_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_PARTNER_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_ADDRESS_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_BANK_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_COMMUNICATION_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_COMMUNICATION_ROLE_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_PARTNERS_ROLE_ROLE_BY_MANDATOR_SQL, parameters);
		
		
	}

	

	public final List<PartnerCore> findPartners(final long mandator) {
		final String sql = String.format("SELECT * from PARTNERS_CORE where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(PartnerCore.class));
	}

	public <T> void   persist(final T entity) {
		final String insert = BeanUtil.insert(entity);
		final Map<String, Object> values = BeanUtil.toMap(entity);
		namedParameterJdbcOperations.update(insert, values);
	}
	
	public final List<Address> findAddresses(final long mandator) {
		final String sql = String.format("SELECT * from ADDRESS where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(Address.class));
	}
	
	public final List<Bank> findBanks(final long mandator) {
		final String sql = String.format("SELECT * from BANK where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(Bank.class));
	}
	
	public final List<Communication> findCommunications(final long mandator) {
		final String sql = String.format("SELECT * from COMMUNICATION where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(Communication.class));
	}
	
	public final List<CommunicationRole> findCommunicationRoles(final long mandator) {
		final String sql = String.format("SELECT * from COMMUNICATION_ROLE where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(CommunicationRole.class));
	}

	public final List<PartnersRole> findPartnersRoles(final long mandator) {
		final String sql = String.format("SELECT * from PARTNERS_ROLE where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(PartnersRole.class));
		
	}
	
	public final List<PartnerCore> findPartnerHists(final String partnerId) {
		final String sql = String.format("SELECT * from PARTNERS_CORE where PARTNERS_NR=:%s order by histnr", PARTNERS_NUMBER_PARAMETER);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put(PARTNERS_NUMBER_PARAMETER, partnerId);
		return namedParameterJdbcOperations.query(sql, parameter, new BeanPropertyRowMapper<>(PartnerCore.class));
	}
	
	public final List<PartnersRole> findPartnersRoleHists(final String partnerId) {
		final String sql = String.format("SELECT * from PARTNERS_ROLE where RIGHT_SIDE=:%s order by role, histnr", PARTNERS_NUMBER_PARAMETER);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put(PARTNERS_NUMBER_PARAMETER, partnerId);
		return namedParameterJdbcOperations.query(sql, parameter, new BeanPropertyRowMapper<>(PartnersRole.class));
	}
	
	public final List<PMContract> findContractHists(final long contractNr) {
		
		
		final String sql = String.format("SELECT * from PM_CONTRACT where CONTRACT_NUMBER=:%s order by histnr", CONTRACT_NUMBER_PARAMETER);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put(CONTRACT_NUMBER_PARAMETER, contractNr);
		return namedParameterJdbcOperations.query(sql, parameter, new BeanPropertyRowMapper<>(PMContract.class));
	}
}
