package de.msg.jbit7.migration.itnrw.stamm;

import java.lang.reflect.Modifier;
import java.util.Date;

import org.springframework.util.ReflectionUtils;

import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class HiAntragstellerBuilder {
	
	private final HiAntragsteller hiAntragsteller = new HiAntragsteller();
	
	public HiAntragstellerBuilder withBeihilfenr(final long beihilfenr) {
		hiAntragsteller.setBeihilfenr(beihilfenr);
		return this;
	}
	
	public HiAntragstellerBuilder withWertId(final long wertId) {
		hiAntragsteller.setWertId(wertId);
		return this;
	}
	
	public HiAntragstellerBuilder withWert(final String wert) {
		hiAntragsteller.setWertChar(wert);
		return this;
	}
	
	public HiAntragstellerBuilder withWert(final long wert) {
		hiAntragsteller.setWertInt(wert);
		return this;
	}
	
	public HiAntragstellerBuilder withBeginn(final long date) {
		hiAntragsteller.setBeginnDatum(date);
		return this;
	}
	
	public HiAntragstellerBuilder withBeginn(final Date date) {
		hiAntragsteller.setWertInt(TestUtil.toLong(date));
		return this;
	}
	
	public final HiAntragsteller build() {
		
		
	if( hiAntragsteller.getBeginnDatum() == null)	{
		hiAntragsteller.setBeginnDatum(TestUtil.toLong(TestUtil.randomDate()));
	}
		
	TestUtil.assignValuesToBean(hiAntragsteller, field -> {
		if(Modifier.isStatic(field.getModifiers())) {
			return false;
		}
		if( field.getType().isArray() ) {
			return false;
		}
		
		field.setAccessible(true);
	
		return ReflectionUtils.getField(field,hiAntragsteller) == null;
		
	});
	
	
	return hiAntragsteller;
	}

	
	public static HiAntragstellerBuilder builder() {
		return new HiAntragstellerBuilder();
	}
}
