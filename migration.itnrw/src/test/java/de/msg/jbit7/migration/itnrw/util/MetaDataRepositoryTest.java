package de.msg.jbit7.migration.itnrw.util;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration({"/beans.xml"})

class MetaDataRepositoryTest {
	
	@Autowired
	@Qualifier("itnrwJDBCTemplate")
	private JdbcOperations jdbcOperations; 
	
	private  MetaDataRepositoryImpl metaDataRepository ;
	
	@BeforeEach
	void setup() {
		 metaDataRepository = new MetaDataRepositoryImpl(jdbcOperations);
	}
	@Test
	
	final void stamm() throws Exception {
		System.out.println(metaDataRepository.generateAnemicObject("stamm"));
	
	}
	
	@Test
	final void idMapping() throws Exception {
		System.out.println(metaDataRepository.generateAnemicObject("id_mapping"));
	
	}
	
	@Test
	final void ehegatte() throws Exception {
		System.out.println(metaDataRepository.generateAnemicObject("ehegatte"));
	
	}
	
	@Test
	final void kindInfo() throws Exception {
		System.out.println(metaDataRepository.generateAnemicObject("KIND_INFO"));
	
	}


	@Test
	final void startValues() throws SQLException {
		System.out.println(metaDataRepository.generateAnemicObject("MAPPING.START_VALUES"));
	}
}
