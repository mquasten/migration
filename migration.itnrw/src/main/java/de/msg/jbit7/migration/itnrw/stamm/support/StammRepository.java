package de.msg.jbit7.migration.itnrw.stamm.support;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Repository
public class StammRepository {
	
	
	private final JdbcOperations jdbcOperations;
	
	@Autowired
	StammRepository(@Qualifier("itnrwJDBCTemplate") final JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	
	
	public final List<StammImpl> findAll() {
		final String sql = "SELECT * FROM STAMM";
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

}
