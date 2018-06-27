package com.sunsea.parkinghere.framework.data;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import com.sunsea.parkinghere.exception.InternalException;

public final class ExBeanUtils {
	/**
	 * 设置字段的值
	 * 
	 * @param obj
	 *          null或实例对象，如果为null则表示设置类成员字段的值
	 * @param clazz
	 *          类
	 * @param fieldName
	 *          　字段名
	 * @param value
	 *          值
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("rawtypes")
	private static void setFieldValue(Object obj, Class clazz, String fieldName, Object value) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assert (clazz != null);
		assert (fieldName != null);
		Field f = clazz.getDeclaredField(fieldName);
		boolean a = f.isAccessible();
		if (!a)
			f.setAccessible(true);
		try {
			f.set(obj, value);
		} finally {
			if (!a)
				f.setAccessible(a);
		}
	}

	/**
	 * 设置字段的值
	 * 
	 * @param obj
	 *          null或实例对象，如果为null则表示设置类成员字段的值
	 * @param clazz
	 *          类
	 * @param fieldName
	 *          　字段名
	 * @param value
	 *          值
	 * @param force
	 *          是否强制，如果强制时不会抛出任何异常，只是在日志中会记录
	 */
	@SuppressWarnings("rawtypes")
	public static void setFieldValue(Object obj, Class clazz, String fieldName, Object value, boolean force) {
		assert (clazz != null);
		assert (fieldName != null);

		if (force) {
			try {
				setFieldValue(obj, clazz, fieldName, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				setFieldValue(obj, clazz, fieldName, value);
			} catch (Exception e) {
				throw new InternalException(MessageFormat.format("设置类{0}的字段{1}出错！", clazz.getName(), fieldName), e);
			}
		}
	}
}
