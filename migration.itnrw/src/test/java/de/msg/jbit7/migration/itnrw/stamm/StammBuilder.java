package de.msg.jbit7.migration.itnrw.stamm;

import java.lang.reflect.Modifier;
import java.util.Date;

import org.springframework.util.ReflectionUtils;

import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class StammBuilder {
	
	private final StammImpl stamm = new StammImpl();
	
	
	public final StammBuilder withSterbeDatum(final Date date) {
		stamm.setSterbedatum(TestUtil.toLong(date));
		return this;
	}
	
	public final StammBuilder withSterbeDatum() {
		stamm.setSterbedatum(TestUtil.toLong(TestUtil.randomDate()));
		return this;
	}
	public final StammBuilder withSterbeDatum(final long date) {
		stamm.setSterbedatum(date);
		return this;
	}
	
	
	
	public final StammBuilder withBeihilfenr(final long beihilfenr) {
		stamm.setBeihilfenr(beihilfenr);
		return this;
	}
	
	public final StammBuilder withFemaleGender() {
		stamm.setGeschlecht("w");
		return this;
	}
	
	public final StammImpl build() {
		if( stamm.getSterbedatum() == null ) {
			stamm.setSterbedatum(0L);
		}
		if( stamm.getGebDatum() == null ) {
			stamm.setGebDatum(TestUtil.toLong(TestUtil.randomDate()));
		}
		
		if( stamm.getAnzKinder() == null) {
			stamm.setAnzKinder(0L);
		}
		
		if(stamm.getGeloescht() == null) {
			stamm.setGeloescht(0L);
		}
		
		
		
		if(stamm.getGeschlecht()==null) {
			if( Math.random() > 0.5 ) {
				stamm.setGeschlecht("m");
			} else {
				stamm.setGeschlecht("w");
			}
		}
		
		if( stamm.getPlz() == null) {
			stamm.setPlz(TestUtil.randomString().substring(0, 5));
		}
		
		if( stamm.getLaenderKennz()==null) {
			stamm.setLaenderKennz("de");
		}
		TestUtil.assignValuesToBean(stamm, field -> {
			if(Modifier.isStatic(field.getModifiers())) {
				return false;
			}
			if( field.getType().isArray() ) {
				return false;
			}
			
			field.setAccessible(true);
		
			return ReflectionUtils.getField(field,stamm) == null;
			
		});
		
		if( stamm.getSterbedatum() == 0) {
			stamm.setSterbedatum(null);
		}
		return stamm;	
	}
	
	public static StammBuilder builder() {
		return new StammBuilder();
	}

}
