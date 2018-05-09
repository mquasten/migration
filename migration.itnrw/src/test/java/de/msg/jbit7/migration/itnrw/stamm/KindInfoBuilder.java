package de.msg.jbit7.migration.itnrw.stamm;

import java.lang.reflect.Modifier;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import de.msg.jbit7.migration.itnrw.util.TestUtil;

public class KindInfoBuilder {

	private final KindInfo kindInfo = new KindInfo();

	public KindInfoBuilder withBeihilfenr(final long beihilfenr) {
		kindInfo.setBeihilfenr(beihilfenr);
		return this;
	}

	public KindInfoBuilder withSterbedatum(final long sterbeDatum) {
		kindInfo.setSterbedatum(sterbeDatum);
		return this;
	}

	public KindInfoBuilder withSterbeDatum() {
		kindInfo.setSterbedatum(TestUtil.toLong(TestUtil.randomDate()));
		return this;
	}

	public KindInfoBuilder withGebDatum(final long gebDatum) {
		kindInfo.setGebDatum(gebDatum);
		return this;
	}
	public KindInfoBuilder withLfdKind(final long lfdKind) {
		kindInfo.setLfdKind(lfdKind);
		return this;
	}

	public final KindInfo build() {

		if (kindInfo.getSterbedatum() == null) {
			kindInfo.setSterbedatum(0L);
		}

		if (kindInfo.getGebDatum() == null) {
			kindInfo.setGebDatum(TestUtil.toLong(TestUtil.randomDate()));
		}
		
		if( StringUtils.isEmpty(kindInfo.getName())) {
			kindInfo.setName("");
		}

		TestUtil.assignValuesToBean(kindInfo, field -> {
			if (Modifier.isStatic(field.getModifiers())) {
				return false;
			}
			if (field.getType().isArray()) {
				return false;
			}

			field.setAccessible(true);

			return ReflectionUtils.getField(field, kindInfo) == null;

		});

		
		if( StringUtils.isEmpty(kindInfo.getName())) {
			kindInfo.setName(null);
		}
		
		if (kindInfo.getSterbedatum() == 0) {
			kindInfo.setSterbedatum(null);

		}

		Assert.notNull(kindInfo.getBeihilfenr(), "Beihilfenr required.");
		Assert.notNull(kindInfo.getGebDatum(), "GebDatum required.");
		Assert.hasText(kindInfo.getVorname(), "VornameEhe required.");
		return kindInfo;

	}
	
	public static KindInfoBuilder builder () {
		return new KindInfoBuilder();
	}

}
