package com.sunsea.parkinghere.framework.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sunsea.parkinghere.exception.InternalException;

public final class ValueProviderFactory {
	private static final Map<Class<? extends IValueProvider<?>>, IValueProvider<?>> VALUEPROVIDERS = new ConcurrentHashMap<Class<? extends IValueProvider<?>>, IValueProvider<?>>();

	static {
		VALUEPROVIDERS.put(DefaultValueProvider.class, new DefaultValueProvider());
	}

	/**
	 * 取特定类的值提供者实例
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IValueProvider<?>> T getValueProvider(Class<T> clazz) {
		T result = (T) VALUEPROVIDERS.get(clazz);
		if (result == null) {
			synchronized (VALUEPROVIDERS) {
				result = (T) VALUEPROVIDERS.get(clazz);
				if (result == null) {
					try {
						result = clazz.newInstance();
						VALUEPROVIDERS.put(clazz, result);
					} catch (Exception e) {
						throw new InternalException(e);
					}
				}
			}
		}
		return result;
	}
}
