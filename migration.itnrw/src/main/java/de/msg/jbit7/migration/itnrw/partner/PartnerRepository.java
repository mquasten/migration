package de.msg.jbit7.migration.itnrw.partner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class PartnerRepository {
	

	private final JdbcOperations jdbcOperations;
	
	@Autowired
	PartnerRepository(@Qualifier("healthFactoryJDBCTemplate") final JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
	
	public final List<PartnerCore> findAll() {
		final String sql = "SELECT * from PARTNERS_CORE";
		return jdbcOperations.query(sql, new BeanPropertyRowMapper<>(PartnerCore.class));
	}

}
