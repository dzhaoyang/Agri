package com.sunsea.parkinghere.framework.data;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

public abstract class AbstractValueProvider<T> implements IValueProvider<T> {

	@SuppressWarnings("unchecked")
	protected <E> E getPropertyForce(Object obj, String name, Class<E> clazz) {
		Object val = null;
		try {
			val = PropertyUtils.getProperty(obj, name);
		} catch (Exception e) {
		}
		if(clazz == Object.class)
			return (E) val;
		return (E) ConvertUtils.convert(val, clazz);
	}
}
