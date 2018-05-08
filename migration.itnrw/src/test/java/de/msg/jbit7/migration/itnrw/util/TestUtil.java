package de.msg.jbit7.migration.itnrw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.springframework.util.ReflectionUtils;

public interface TestUtil {
	
	static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public static <T> void assignValuesToBean(final T bean) {
		assignValuesToBean(bean, field -> !Modifier.isStatic(field.getModifiers()));
	}

	public static <T> void assignValuesToBean(final T bean, final Predicate<Field> predicate) {
		final Map<Class<?>, Supplier<Object>> suppliers = new HashMap<>();
		suppliers.put(String.class, () -> randomString());
		suppliers.put(Long.class, () -> randomLong());
		suppliers.put(Date.class, () -> randomDate());
		Arrays.asList(bean.getClass().getDeclaredFields()).stream().filter(field -> predicate.test(field))
				.forEach(field -> {
					assertTrue(suppliers.containsKey(field.getType()));
					final Object value = suppliers.get(field.getType()).get();
					field.setAccessible(true);
					ReflectionUtils.setField(field, bean, value);
				});
	}

	public static Date randomDate() {
		return new Date(randomLong());
	}

	public static String randomString() {
		return new UUID(randomLong(), randomLong()).toString();
	}

	public static Long randomLong() {
		return Long.valueOf(Double.valueOf(1e10 * Math.random()).longValue());
	}

	public static void assertEqualsRequired(final Object expected, final Object actual) {
		assertNotNull(actual, "Value ist required.");
		assertEquals(expected, actual);
	}

	public static Date date(int year, int month, int day) {
		final Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}

	public static Date toDate(final long dateAsLong) {
		final String date = "" + dateAsLong;
		if (date.length() != 8) {
			return null;
		}

		
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {

			return null;
		}
	}
	
	public static void assertSameDay(final Date expected, final Date date) {
		assertEquals(truncate(expected), truncate(date));
	}
	
	public static Date nextDay(final Date date) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, +1);
		return calendar.getTime();
		
	}
	
	public static Date truncate(final Date date) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
		
	}

	public static long toLong(final Date date) {
	
		String dateAsString = dateFormat.format(date);
		return Long.valueOf(dateAsString);
	}
}
