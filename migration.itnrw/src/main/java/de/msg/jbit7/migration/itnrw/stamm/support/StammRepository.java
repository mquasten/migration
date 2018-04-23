package de.msg.jbit7.migration.itnrw.stamm.support;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Repository
public class StammRepository {
	
	
	private static final String BEIHILFE_NR_PARAMETER = "beihilfeNr";
	private static final String SELECT_STAMM = "SELECT * FROM STAMM";
	private final NamedParameterJdbcOperations jdbcOperations;
	
	@Autowired
	StammRepository(@Qualifier("itnrwJDBCTemplate") final NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	
	
	public final List<StammImpl> findAll() {
		final String sql = SELECT_STAMM;
		final BeanPropertyRowMapper<StammImpl> rowMapper = new BeanPropertyRowMapper<>(StammImpl.class);
		return jdbcOperations.query(sql, rowMapper);
	}
	
	public final List<Ehegatte> findAllEhegatte() {
		final String sql = "SELECT * FROM EHEGATTE";
		final BeanPropertyRowMapper<Ehegatte> rowMapper = new BeanPropertyRowMapper<>(Ehegatte.class);
		return jdbcOperations.query(sql, rowMapper);
	}
	
	public final List<KindInfo> findAllChildren() {
		final String sql = "SELECT * FROM KIND_INFO";
		final BeanPropertyRowMapper<KindInfo> rowMapper = new BeanPropertyRowMapper<>(KindInfo.class);
		return jdbcOperations.query(sql, rowMapper);
	}
	
	
	public final Map<Long, Date> beginDates() {
		final Map<Long,Date> beginDates = new HashMap<>();  
		jdbcOperations.query("select beihilfenr , min(to_date(beginn_datum, 'yyyymmdd'))  from hi_antragsteller group by beihilfenr", resultSet -> {
			  beginDates.put(resultSet.getLong(1), resultSet.getDate(2));
		  });
		return beginDates;
	}
	
	public final StammImpl findStamm(final long beihilfeNr) {
		final Map<String, Long> parameter = parameterMapBeihilfeNr(beihilfeNr);
		final List<StammImpl> results = jdbcOperations.query(String.format(SELECT_STAMM + " WHERE BEIHILFENR = :%s" , BEIHILFE_NR_PARAMETER), parameter, new BeanPropertyRowMapper<>(StammImpl.class));
		return DataAccessUtils.requiredSingleResult(results);
		
	}
	
	



	private Map<String, Long> parameterMapBeihilfeNr(final long beihilfeNr) {
		final Map<String,Long> parameter = new HashMap<>();
		parameter.put(BEIHILFE_NR_PARAMETER, beihilfeNr);
		return parameter;
	}
	
	public final List<SepaBankVerbindung> findSepaBank(final long beihilfeNr) {

		final String sql = String.format("select * from SEPA_BANKVERBINDUNG sb  where beihilfenr = :%s and  sb.version = ( select  max(version) from SEPA_BANKVERBINDUNG where beihilfenr = sb.beihilfenr and sb.typ = typ) order by sb.typ  ", BEIHILFE_NR_PARAMETER);
		
		
		return jdbcOperations.query(sql , parameterMapBeihilfeNr(beihilfeNr), new BeanPropertyRowMapper<>(SepaBankVerbindung.class));
	   
		
	}

}
