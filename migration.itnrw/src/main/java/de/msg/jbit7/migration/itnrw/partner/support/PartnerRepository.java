package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import de.msg.jbit7.migration.itnrw.partner.PMContract;

import de.msg.jbit7.migration.itnrw.util.BeanUtil;

@Repository
public class PartnerRepository {
	

	
	

	private static final String MANDATOR_NAME = "mandator";
	private static final String DELELETE_CONTRACT_BY_MANDATOR_SQL = String.format("DELETE FROM PM_CONTRACT WHERE MANDATOR = :%s", MANDATOR_NAME);

	private final NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	final String INSERT_PM_CONTRACT = "INSERT INTO PM_CONTRACT (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PRIONR,CONTRACT_NUMBER,REASON_FOR_CHANGE,BEGIN_OF_CONTRACT,TERMINATION_DATE,RISK_CARRIER,POLICY_NUMBER,MEMBER_OF_STAFF,COLLECTIVE_CONTRACT_NUMBER,INTERNAL_NUMBER_COLL_CONTRACT,POLICY_CONFIRMATION_FLAG,CONTRACT_TYPE,POSTING_TEXT_1,POSTING_TEXT_2,POSTING_TEXT_3) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:prionr,:contractNumber,:reasonForChange,:beginOfContract,:terminationDate,:riskCarrier,:policyNumber,:memberOfStaff,:collectiveContractNumber,:internalNumberCollContract,:policyConfirmationFlag,:contractType,:postingText1,:postingText2,:postingText3)";

	
	
	@Autowired
	PartnerRepository(final NamedParameterJdbcOperations namedParameterJdbcOperations) {
		this.namedParameterJdbcOperations=namedParameterJdbcOperations;
	}
	
	public final List<PMContract> findAllContracts() {
		final String sql = "SELECT * from PM_CONTRACT";
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(PMContract.class));
	}
	
	
	public final void persist(final PMContract contract ) {
		final Map<String, Object> values = BeanUtil.toMap(contract);
		namedParameterJdbcOperations.update(INSERT_PM_CONTRACT, values);
	}

	public final void cleanMandator(final long mandator) {
		final Map<String,Long> parameters = new HashMap<>();
		parameters.put(MANDATOR_NAME, mandator);
		namedParameterJdbcOperations.update(DELELETE_CONTRACT_BY_MANDATOR_SQL , parameters);
	}

}
