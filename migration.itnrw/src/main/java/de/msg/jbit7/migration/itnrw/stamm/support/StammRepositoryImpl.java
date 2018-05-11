package de.msg.jbit7.migration.itnrw.stamm.support;

import java.util.ArrayList;
import java.util.Arrays;
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
import de.msg.jbit7.migration.itnrw.stamm.HiAntragssteller;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

@Repository
public class StammRepositoryImpl implements StammRepository {

	private static final String BEIHILFE_NR_PARAMETER = "beihilfeNr";
	private static final String KIND_NR_PARMETER = "lfdKind";
	private static final String SELECT_STAMM = "SELECT * FROM STAMM";
	private final NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	StammRepositoryImpl(@Qualifier("itnrwJDBCTemplate") final NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#findAll()
	 */
	@Override
	public final List<StammImpl> findAll() {
		final String sql = SELECT_STAMM;
		final BeanPropertyRowMapper<StammImpl> rowMapper = new BeanPropertyRowMapper<>(StammImpl.class);
		return jdbcOperations.query(sql, rowMapper);
	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#findAllEhegatte()
	 */
	@Override
	public final List<Ehegatte> findAllEhegatte() {
		final String sql = "SELECT * FROM EHEGATTE";
		final BeanPropertyRowMapper<Ehegatte> rowMapper = new BeanPropertyRowMapper<>(Ehegatte.class);
		return jdbcOperations.query(sql, rowMapper);
	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#findAllChildren()
	 */
	@Override
	public final List<KindInfo> findAllChildren() {
		final String sql = "SELECT * FROM KIND_INFO";
		final BeanPropertyRowMapper<KindInfo> rowMapper = new BeanPropertyRowMapper<>(KindInfo.class);
		return jdbcOperations.query(sql, rowMapper);
	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#findChildren(long, java.lang.Long[])
	 */
	@Override
	public final List<KindInfo> findChildren(final long beihilfeNr, Long[] ids) {

		if (ids.length == 0) {
			return new ArrayList<>();
		}
		final String sql = String.format("SELECT * FROM KIND_INFO  WHERE BEIHILFENR = :%s and LFD_KIND in (:%s)",
				BEIHILFE_NR_PARAMETER, KIND_NR_PARMETER);
		;
		final BeanPropertyRowMapper<KindInfo> rowMapper = new BeanPropertyRowMapper<>(KindInfo.class);
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put(BEIHILFE_NR_PARAMETER, beihilfeNr);

		parameters.put(KIND_NR_PARMETER, Arrays.asList(ids));
		return jdbcOperations.query(sql, parameters, rowMapper);

	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#beginDates()
	 */
	@Override
	public final Map<Long, Date> beginDates() {
		final Map<Long, Date> beginDates = new HashMap<>();
		jdbcOperations.query(
				"select beihilfenr , min(to_date(beginn_datum, 'yyyymmdd'))  from hi_antragsteller group by beihilfenr",
				resultSet -> {
					beginDates.put(resultSet.getLong(1), resultSet.getDate(2));
				});
		return beginDates;
	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#findStamm(long)
	 */
	@Override
	public final StammImpl findStamm(final long beihilfeNr) {
		final Map<String, Long> parameter = parameterMapBeihilfeNr(beihilfeNr);
		final List<StammImpl> results = jdbcOperations.query(
				String.format(SELECT_STAMM + " WHERE BEIHILFENR = :%s", BEIHILFE_NR_PARAMETER), parameter,
				new BeanPropertyRowMapper<>(StammImpl.class));
		return DataAccessUtils.requiredSingleResult(results);

	}

	private Map<String, Long> parameterMapBeihilfeNr(final long beihilfeNr) {
		final Map<String, Long> parameter = new HashMap<>();
		parameter.put(BEIHILFE_NR_PARAMETER, beihilfeNr);
		return parameter;
	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#findSepaBank(long)
	 */
	@Override
	public final List<SepaBankVerbindung> findSepaBank(final long beihilfeNr) {

		final String sql = String.format(
				"select * from SEPA_BANKVERBINDUNG sb  where beihilfenr = :%s and  sb.version = ( select  max(version) from SEPA_BANKVERBINDUNG where beihilfenr = sb.beihilfenr and sb.typ = typ) order by sb.typ  ASC",
				BEIHILFE_NR_PARAMETER);

		return jdbcOperations.query(sql, parameterMapBeihilfeNr(beihilfeNr),
				new BeanPropertyRowMapper<>(SepaBankVerbindung.class));

	}

	/*
	 * (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.StammRepository#findLastStatus()
	 */
	@Override
	public List<HiAntragssteller> findLastStatus() {
		final String sql ="SELECT HIA.* FROM HI_ANTRAGSTELLER HIA  WHERE HIA.WERT_ID = 18 AND HIA.BEGINN_DATUM = ( SELECT MAX(BEGINN_DATUM) from HI_ANTRAGSTELLER  where HIA.BEIHILFENR = BEIHILFENR and HIA.WERT_ID = WERT_ID)";
		return jdbcOperations.query(sql,new BeanPropertyRowMapper<>(HiAntragssteller.class));
	}

}
