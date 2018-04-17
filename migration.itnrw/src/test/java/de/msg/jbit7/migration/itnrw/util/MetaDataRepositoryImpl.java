package de.msg.jbit7.migration.itnrw.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.StringUtils;


class MetaDataRepositoryImpl {
	private JdbcOperations jdbcOperations;

	private Map<String, String> types = new HashMap<>();


	MetaDataRepositoryImpl(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
		types.put("VARCHAR2", "String");
		types.put("CHAR", "String");
		types.put("NUMBER", "Long");
		types.put("DATE", "Date");
		types.put("TIMESTAMP", "Date");
		types.put("RAW", "byte[]");
		types.put("MAPPING.PARTNER_NR_VAT", "String[]");
		types.put("MAPPING.COLLECTIVE_CONTRACT_VAT", "Long[]");
		types.put("MAPPING.CHILD_NR_VAT", "Long[]");
		
	}

	public final String generateAnemicObject(final String table) throws SQLException {
		final String select = String.format("select * from %s", table);
		return jdbcOperations.query(select, rs ->  (String) resultSetToString(table, rs));

	}
	
	
	

	private String resultSetToString(final String table, final ResultSet rs) throws SQLException {
		final ResultSetMetaData resultSetMetaData = rs.getMetaData();
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("public class %s {\n", StringUtils.capitalize(table.toLowerCase())));
		for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {

			final String colName =JdbcUtils.convertUnderscoreNameToPropertyName(JdbcUtils.lookupColumnName(resultSetMetaData, i + 1));
			
			//System.out.println(JdbcUtils.lookupColumnName(resultSetMetaData, i + 1) + "=" + resultSetMetaData.getColumnTypeName(i+1));
			
			
			builder.append(String.format("   private %s %s;\n", types.get(resultSetMetaData.getColumnTypeName(i+1)), colName));
		}
		builder.append("}");
		return builder.toString();
	}
	
	public final String insertFor(final String table) {
		final String select = String.format("select * from %s", table);
		return jdbcOperations.query(select, rs -> (String) resultSetToInsertString(table, rs) );
	
	}
	
	private String resultSetToInsertString(final String table, final ResultSet rs) throws SQLException {
		final ResultSetMetaData resultSetMetaData = rs.getMetaData();
		final List<String> columns = new ArrayList<>();
		final List<String> names = new ArrayList<>();
		for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {

			final String name =JdbcUtils.convertUnderscoreNameToPropertyName(JdbcUtils.lookupColumnName(resultSetMetaData, i + 1));
			columns.add(JdbcUtils.lookupColumnName(resultSetMetaData, i + 1));
			names.add(":" + name);
		}
	
		return String.format("INSERT INTO %s (%s) VALUES (%s)", table, StringUtils.collectionToCommaDelimitedString(columns), StringUtils.collectionToCommaDelimitedString(names));
	
	
	}

	
}
