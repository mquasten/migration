package de.msg.jbit7.migration.itnrw.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import de.msg.jbit7.migration.itnrw.partner.PMContract;

public class BeanUtil {

	public static Map<String, Object> toMap(final PMContract contract) {
		try {
			return doMapping(contract);
		} catch (final Exception ex) {
			throw new IllegalStateException(ex);
		}

	}

	private static Map<String, Object> doMapping(final PMContract contract)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		final Map<String, Object> values = new HashMap<>();

		final BeanInfo info = Introspector.getBeanInfo(contract.getClass());
		for (final PropertyDescriptor pd : info.getPropertyDescriptors()) {
			final Method reader = pd.getReadMethod();
			values.put(pd.getName(), reader.invoke(contract));
		}
		return values;
	}

}
