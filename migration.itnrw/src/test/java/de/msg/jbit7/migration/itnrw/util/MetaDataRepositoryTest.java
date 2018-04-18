package de.msg.jbit7.migration.itnrw.util;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})

class MetaDataRepositoryTest {
	
	@Autowired
	@Qualifier("itnrwJDBCTemplate")
	private NamedParameterJdbcOperations jdbcOperationsBeihilfe; 
	
	@Autowired
	@Qualifier("healthFactoryJDBCTemplate")
	private NamedParameterJdbcOperations jdbcOperationsHealthFactory; 
	
	private  MetaDataRepositoryImpl metaDataRepositoryBeihilfe ;
	private  MetaDataRepositoryImpl metaDataRepositoryHealthFactory ;
	
	@BeforeEach
	void setup() {
		metaDataRepositoryBeihilfe = new MetaDataRepositoryImpl(jdbcOperationsBeihilfe.getJdbcOperations());
		metaDataRepositoryHealthFactory = new MetaDataRepositoryImpl( jdbcOperationsHealthFactory.getJdbcOperations());
	}
	@Test
	@Disabled
	final void stamm() throws Exception {
		System.out.println(metaDataRepositoryBeihilfe.generateAnemicObject("stamm"));
	
	}
	
	@Test
	@Disabled
	final void idMapping() throws Exception {
		System.out.println(metaDataRepositoryBeihilfe.generateAnemicObject("id_mapping"));
	
	}
	
	@Test
	@Disabled
	final void ehegatte() throws Exception {
		System.out.println(metaDataRepositoryBeihilfe.generateAnemicObject("ehegatte"));
	
	}
	
	@Test
	@Disabled
	final void kindInfo() throws Exception {
		System.out.println(metaDataRepositoryBeihilfe.generateAnemicObject("KIND_INFO"));
	
	}


	@Test
	@Disabled
	final void startValues() throws SQLException {
		System.out.println(metaDataRepositoryBeihilfe.generateAnemicObject("MAPPING.START_VALUES"));
	}
	
	@Test
	@Disabled
	final void pmContract() throws SQLException {
		System.out.println(metaDataRepositoryHealthFactory.generateAnemicObject("PM_CONTRACT"));
	}
	
	@Test
	@Disabled
	final void insertForContract() {
		System.out.println(metaDataRepositoryHealthFactory.insertFor("PM_CONTRACT"));
	}
	@Test
	final void sepaBankVerbindung() throws SQLException {
		System.out.println(metaDataRepositoryBeihilfe.generateAnemicObject("SEPA_BANKVERBINDUNG"));
	}
	
	
	@Test
	@Disabled
	final void insertForPartnerCore() {
		System.out.println(metaDataRepositoryHealthFactory.insertFor("PARTNERS_CORE"));
	}
	
	@Test
	@Disabled
	final void partnerCore() throws SQLException {
		System.out.println(metaDataRepositoryHealthFactory.generateAnemicObject("PARTNERS_CORE"));
	}
}
