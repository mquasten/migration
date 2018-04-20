package de.msg.jbit7.migration.itnrw.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Persistence {
	String value();
}
