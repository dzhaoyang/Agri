package com.sunsea.parkinghere.framework.data;

public final class IEnumValueUtils {
	/**
	 * 前条件是必须是枚举
	 * 
	 * @param obj
	 * @param clazz
	 */
	public static <M, E extends IEnumValue<M>> E valAsEnumValue(M obj, Class<E> clazz) {
		if (obj == null)
			return null;
		if (!clazz.isEnum())
			return null;
		for (E e : clazz.getEnumConstants()) {
			if (obj.equals(e.getEnumValue())) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 前条件是必须是枚举
	 * 
	 * @param obj
	 * @param clazz
	 */
	public static <M, E extends IEnumValue<M>> E labelAsEnumValue(String label, Class<E> clazz) {
		if (label == null)
			return null;
		if (!clazz.isEnum())
			return null;
		for (E e : clazz.getEnumConstants()) {
			if (label.equals(e.getLabel())) {
				return e;
			}
		}
		return null;
	}
}
