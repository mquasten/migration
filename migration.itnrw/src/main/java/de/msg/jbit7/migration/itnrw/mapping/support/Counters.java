package de.msg.jbit7.migration.itnrw.mapping.support;

import de.msg.jbit7.migration.itnrw.mapping.StartValues;

public interface Counters {

	String nextPartnerNumber();

	long nextProcessNumber();

	long nextContactNumber();

	long nextCollectiveContractNumberSchool(final String schoolNumber);

	long nextCollectiveContractNumberOffice(final String officeNumber);

	long mandator();
	
	StartValues startValues();
}