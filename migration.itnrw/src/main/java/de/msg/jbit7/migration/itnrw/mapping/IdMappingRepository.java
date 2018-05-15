package de.msg.jbit7.migration.itnrw.mapping;

import java.util.List;

import de.msg.jbit7.migration.itnrw.mapping.support.Counters;

public interface IdMappingRepository {

	void persist(IdMapping idMapping);

	void delete(final long mandator);

	List<IdMapping> findAll(final long mandator);

	Counters findCounters(long mandator);

}