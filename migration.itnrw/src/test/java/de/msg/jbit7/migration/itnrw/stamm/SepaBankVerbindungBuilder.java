package de.msg.jbit7.migration.itnrw.stamm;

import java.lang.reflect.Modifier;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class SepaBankVerbindungBuilder {
	
	private final SepaBankVerbindung sepaBankVerbindung = new SepaBankVerbindung();
	
	
	public final SepaBankVerbindung build() {
		
		if( StringUtils.isEmpty(sepaBankVerbindung.getIban())) {
			sepaBankVerbindung.setIban(TestUtil.randomString().substring(0, 34));
		}
		
		if(StringUtils.isEmpty(sepaBankVerbindung.getBic())) {
			sepaBankVerbindung.setBic(TestUtil.randomString().substring(0, 11));
		}
		TestUtil.assignValuesToBean(sepaBankVerbindung, field -> {
			if(Modifier.isStatic(field.getModifiers())) {
				return false;
			}
			if( field.getType().isArray() ) {
				return false;
			}
			
			field.setAccessible(true);
		
			return ReflectionUtils.getField(field,sepaBankVerbindung) == null;
			
		});
		
		return sepaBankVerbindung;
	}

	
	public static SepaBankVerbindungBuilder builder() {
		return new SepaBankVerbindungBuilder();
	}
}
