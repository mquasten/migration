package de.msg.jbit7.migration.itnrw.stamm;

import java.lang.reflect.Modifier;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class DrittempfaengerBuilder {

	private Drittempfaenger drittempfaenger = new Drittempfaenger();

	public DrittempfaengerBuilder withBeihilfenr(final long beihilfenr) {
		drittempfaenger.setBeihilfenr(beihilfenr);
		return this;
	}
	
	public DrittempfaengerBuilder withVollmacht() {
		drittempfaenger.setVollmacht("j");
		return this;
	}
	
	public DrittempfaengerBuilder withFemale() {
		drittempfaenger.setGeschlecht("w");
		return this;
	}
	
	public DrittempfaengerBuilder withMale() {
		drittempfaenger.setGeschlecht("m");
		return this;
	}



	public final Drittempfaenger build() {

		if( StringUtils.isEmpty(drittempfaenger.getVollmacht())) {
			drittempfaenger.setVollmacht("");
		}
		
		if( StringUtils.isEmpty(drittempfaenger.getVollmacht())) {
			sex();
		}
		
		TestUtil.assignValuesToBean(drittempfaenger, field -> {
			if (Modifier.isStatic(field.getModifiers())) {
				return false;
			}
			if (field.getType().isArray()) {
				return false;
			}

			field.setAccessible(true);

			return ReflectionUtils.getField(field, drittempfaenger) == null;

		});
		
		if( StringUtils.isEmpty(drittempfaenger.getVollmacht())) {
			drittempfaenger.setVollmacht("n");
		}

		Assert.notNull(drittempfaenger.getBeihilfenr(), "Beihilfenr required.");
		

		return drittempfaenger;

	}

	private String  sex() {
		return Math.random() < 0.5  ? "m": "w";
	}

	public static DrittempfaengerBuilder builder() {
		return new DrittempfaengerBuilder();
	}
}
