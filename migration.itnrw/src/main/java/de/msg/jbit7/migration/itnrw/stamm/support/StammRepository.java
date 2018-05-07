package de.msg.jbit7.migration.itnrw.stamm.support;

import java.util.Date;
import java.util.List;
import java.util.Map;

import de.msg.jbit7.migration.itnrw.stamm.Ehegatte;
import de.msg.jbit7.migration.itnrw.stamm.KindInfo;
import de.msg.jbit7.migration.itnrw.stamm.SepaBankVerbindung;
import de.msg.jbit7.migration.itnrw.stamm.StammImpl;

public interface StammRepository {

	List<StammImpl> findAll();

	List<Ehegatte> findAllEhegatte();

	List<KindInfo> findAllChildren();

	List<KindInfo> findChildren(long beihilfeNr, Long[] ids);

	Map<Long, Date> beginDates();

	StammImpl findStamm(long beihilfeNr);

	List<SepaBankVerbindung> findSepaBank(long beihilfeNr);

}