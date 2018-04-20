package de.msg.jbit7.migration.itnrw.mapping;

import java.util.Map;

public interface IdGenerationService {

	void createIds(final long mandator, final boolean deleteFirst, final String migUser);
	
	Map<Long, IdMapping> findAll();

}