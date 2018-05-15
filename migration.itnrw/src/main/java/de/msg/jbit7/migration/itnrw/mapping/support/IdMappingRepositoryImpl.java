package de.msg.jbit7.migration.itnrw.mapping.support;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import de.msg.jbit7.migration.itnrw.mapping.IdMapping;
import de.msg.jbit7.migration.itnrw.mapping.IdMappingRepository;
import de.msg.jbit7.migration.itnrw.mapping.StartValues;
import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.internal.OracleResultSet;

@Repository
public class IdMappingRepositoryImpl implements IdMappingRepository {
	
	private final NamedParameterJdbcOperations jdbcOperations;
	
	private static final String MANDATOR_NAME_PARAMETER = "mandator";
	
	@Autowired
	IdMappingRepositoryImpl(@Qualifier("itnrwJDBCTemplate") NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.mapping.support.IdMappingRepository#persist(de.msg.jbit7.migration.itnrw.mapping.IdMapping)
	 */
	@Override
	public final void persist(final IdMapping idMapping) {
		final String insert = "INSERT INTO ID_MAPPING (BEIHILFENR, PARTNER_NR, CONTRACT_NUMBER, PROCESS_NUMBER,MARRIAGE_PARTNER_NR, CHILDREN_PARTNER_NR,CHILDREN_NR, COLLECTIVE_CONTRACT_NUMBERS,SCHULNUMMER, DIENSTSTELLE, MANDATOR, MIGRATION_USER, LAST_STATE, LAST_STATE_DATE) VALUES ( ?,?,?,?,?,?,?,?,?,?,?, ?, ?,?)";
	
		
		jdbcOperations.getJdbcOperations().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				final PreparedStatement statement = conn.prepareStatement(insert);
				statement.setLong(1, idMapping.getBeihilfenr());
				statement.setString(2, idMapping.getPartnerNr());
				statement.setLong(3, idMapping.getContractNumber());
				statement.setLong(4, idMapping.getProcessNumber());
				statement.setString(5, idMapping.getMarriagePartnerNr());
				
				statement.setArray(6, ((OracleConnection)conn).createOracleArray("MAPPING.PARTNER_NR_VAT", idMapping.getChildrenPartnerNr()));
				statement.setArray(7,((OracleConnection)conn).createOracleArray("MAPPING.CHILD_NR_VAT",idMapping.getChildrenNr()));
				statement.setArray(8,((OracleConnection)conn).createOracleArray("MAPPING.COLLECTIVE_CONTRACT_VAT",idMapping.getCollectiveContractNumbers()));
				
				statement.setString(9,idMapping.getSchulnummer());
				statement.setString(10,idMapping.getDienststelle());
				statement.setLong(11,idMapping.getMandator());
				statement.setString(12, idMapping.getMigrationUser());
				statement.setString(13, idMapping.getLastState());
				statement.setDate(14, new Date(idMapping.getLastStateDate().getTime()));
				return statement;
			}
		});
		
	}
	
	


	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.mapping.support.IdMappingRepository#delete()
	 */
	@Override
	public final void delete() {
		jdbcOperations.getJdbcOperations().execute("DELETE FROM ID_MAPPING");
	}
	
	
	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.mapping.support.IdMappingRepository#findAll()
	 */
	@Override
	public final List<IdMapping> findAll(final long mandator) {
		final BeanPropertyRowMapper<IdMapping> rowMapper = new BeanPropertyRowMapper<IdMapping>( IdMapping.class) {
			  protected Object getColumnValue(ResultSet rs, int index,PropertyDescriptor pd) throws SQLException {
				 
				 // System.out.println(">>>" + pd.getName());
				  if (pd.getName().equals("childrenPartnerNr") ) {
					  
					final OracleArray array =    ((OracleResultSet) rs).getARRAY(index);
					final String[] result = (String[]) array.getArray();
					
					return result;
				  }
				  
				  if(pd.getName().equals("childrenNr")) {
					  final OracleArray array =    ((OracleResultSet) rs).getARRAY(index);
					  return array.getLongArray();
				  }
				  
				  
				  if(pd.getName().equals("collectiveContractNumbers" )) {
						final OracleArray array =    ((OracleResultSet) rs).getARRAY(index);
						return array.getLongArray();
				  }
				  
				 
				  
				  return super.getColumnValue(rs, index, pd);
			  }
			  
		};
		final Map<String,Long> parameters = new HashMap<>();	 
		parameters.put(MANDATOR_NAME_PARAMETER, mandator);
		return jdbcOperations.query(String.format("SELECT * FROM ID_MAPPING WHERE MANDATOR =:%s", MANDATOR_NAME_PARAMETER), parameters, rowMapper);
	}

	
	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.mapping.support.IdMappingRepository#findCounters(long)
	 */
	@Override
	public Counters findCounters(final long mandator) {
		return new CountersImpl(DataAccessUtils.requiredSingleResult(jdbcOperations.getJdbcOperations().query("Select * from START_VALUES where mandator = ?", new Long[] {mandator}, new BeanPropertyRowMapper<StartValues>(StartValues.class))));
	}
}
