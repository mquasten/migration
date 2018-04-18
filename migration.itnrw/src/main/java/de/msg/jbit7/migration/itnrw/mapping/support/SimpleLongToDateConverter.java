package de.msg.jbit7.migration.itnrw.mapping.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class SimpleLongToDateConverter implements Converter<Long, Date> {

	@Override
	public Date convert(final Long dateAsLong) {
		if (dateAsLong == null) {
			return null;
		}
		final String dateString = "" + dateAsLong;
		if (dateString.length() != 8) {
			return null;
		}

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			return sdf.parse(dateString);
		} catch (final ParseException parseException) {
			return null;
		}

	}

}
