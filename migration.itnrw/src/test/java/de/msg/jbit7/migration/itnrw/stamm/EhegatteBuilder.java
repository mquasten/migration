package de.msg.jbit7.migration.itnrw.stamm;

import java.lang.reflect.Modifier;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class EhegatteBuilder {
	
	private Ehegatte ehegatte = new Ehegatte();
	
	public EhegatteBuilder withBeihilfenr(final long beihilfenr) {
		ehegatte.setBeihilfenr(beihilfenr);
		return this;
	}
	
	
	public EhegatteBuilder withSterbedatum(final long sterbeDatum) {
		ehegatte.setSterbedatum(sterbeDatum);
		return this;
	}
	
	
	public EhegatteBuilder withSterbeDatum() {
		ehegatte.setSterbedatum(TestUtil.toLong(TestUtil.randomDate()));
		return this;
	}
	
	public EhegatteBuilder withGebDatumEhe(final long gebDatumEhe) {
		ehegatte.setGebDatumEhe(gebDatumEhe);
		return this;
	}
	
	
	public final EhegatteBuilder withAbwName(final String abwName) {
		ehegatte.setAbwName(abwName);
		return this;
	}
	
	public final EhegatteBuilder withVornameEhe(final String vornameEhe) {
		ehegatte.setVornameEhe(vornameEhe);
		return this;
	}
	public final Ehegatte build() {
		
		if( ehegatte.getAbwName() == null) {
			ehegatte.setAbwName("");
		}
		
		if( ehegatte.getSterbedatum() == null) {
			ehegatte.setSterbedatum(0L);
		}
		
		if( ehegatte.getGebDatumEhe() == null) {
			ehegatte.setGebDatumEhe(TestUtil.toLong(TestUtil.randomDate()));
		}
		
		TestUtil.assignValuesToBean(ehegatte, field -> {
			if(Modifier.isStatic(field.getModifiers())) {
				return false;
			}
			if( field.getType().isArray() ) {
				return false;
			}
			
			field.setAccessible(true);
		
			return ReflectionUtils.getField(field,ehegatte) == null;
			
		});
		
		if( ehegatte.getSterbedatum() == 0) {
			ehegatte.setSterbedatum(null);
			
		}
		if( !StringUtils.hasText(ehegatte.getAbwName())){
			ehegatte.setAbwName(null);
		}
		
		Assert.notNull(ehegatte.getBeihilfenr(), "Beihilfenr required.");
		Assert.hasText(ehegatte.getVornameEhe(), "VornameEhe required.");
		return ehegatte;
		
	}

	public static EhegatteBuilder builder() {
		return new EhegatteBuilder();
	}
}
