package com.sunsea.parkinghere.framework.data;

public final class ExNumberUtils {
	public static float toFloat(String s) {
		try {
			return Float.valueOf(s);
		} catch (Exception ex) {
			return 0.00f;
		}
	}
}
