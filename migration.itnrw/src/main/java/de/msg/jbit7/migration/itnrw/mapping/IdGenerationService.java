package de.msg.jbit7.migration.itnrw.mapping;

import java.util.Map;

public interface IdGenerationService {

	void createIds(long mandator, boolean deleteFirst);
	
	Map<Long, IdMapping> findAll();

}