package de.msg.jbit7.migration.itnrw.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

	public static Map<String, Object> toMap(final Object entity) {
		try {
			return doMapping(entity);
		} catch (final Exception ex) {
			throw new IllegalStateException(ex);
		}

	}

	private static Map<String, Object> doMapping(final Object entity)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		final Map<String, Object> values = new HashMap<>();

		final BeanInfo info = Introspector.getBeanInfo(entity.getClass());
		for (final PropertyDescriptor pd : info.getPropertyDescriptors()) {
			final Method reader = pd.getReadMethod();
			values.put(pd.getName(), reader.invoke(entity));
		}
		return values;
	}

}
