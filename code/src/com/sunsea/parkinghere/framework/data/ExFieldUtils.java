package com.sunsea.parkinghere.framework.data;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import com.sunsea.parkinghere.exception.InternalException;

public final class ExFieldUtils {
	private static final String INVALID_METHOD_NAME = "{0}不符合POJO规范";

	private static final String IS = "is";

	private static final String SET = "set";

	private static final String GET = "get";

	public final static boolean isBeanMethod(Method method) {
		return isBeanMethod(method.getName());
	}

	public final static boolean isBeanMethod(String methodName) {
		return methodName.startsWith(GET) || methodName.startsWith(SET)
				|| methodName.startsWith(IS);
	}

	public final static boolean isGetOrSetMethod(String methodName) {
		return methodName.startsWith(GET) || methodName.startsWith(SET);
	}

	public final static boolean isISMethod(Method method) {
		return method.getName().startsWith(IS);
	}

	public final static boolean isISMethod(String methodName) {
		return methodName.startsWith(IS);
	}

	public final static boolean isGetMethod(Method method) {
		return method.getName().startsWith(GET)
				|| method.getName().startsWith(IS);
	}

	public final static boolean isGetMethod(String methodName) {
		return methodName.startsWith(GET) || methodName.startsWith(IS);
	}

	public final static boolean isSetMethod(Method method) {
		return method.getName().startsWith(SET);
	}

	public final static boolean isSetMethod(String methodName) {
		return methodName.startsWith(SET);
	}

	public final static String getBeanField(Method method) {
		return getBeanField(method.getName());
	}

	public final static String getBeanField(String methodName) {
		if (isGetOrSetMethod(methodName)) {
			return toBeanFieldName(methodName.substring(3));
		} else if (isISMethod(methodName)) {
			return toBeanFieldName(methodName.substring(2));
		}
		throw new InternalException(MessageFormat.format(INVALID_METHOD_NAME,
				methodName));
	}

	public final static String toBeanFieldName(String name) {
		return Introspector.decapitalize(name);
	}

}
