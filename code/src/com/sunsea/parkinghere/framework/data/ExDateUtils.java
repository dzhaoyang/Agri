package com.sunsea.parkinghere.framework.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ExDateUtils {
	private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

	/**
	 * 转成日期
	 * 
	 * @param obj
	 * @return
	 */
	public static Time toTime(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Date) {
			return new Time(((Date) obj).getTime());
		}
		try {
			return new Time(TIME_FORMATTER.parse(obj.toString()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转成日期
	 * 
	 * @param obj
	 * @return
	 */
	public static Date toDate(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Date) {
			return new Date(((Date) obj).getTime());
		}
		try {
			return DATE_FORMATTER.parse(obj.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转成日期
	 * 
	 * @param obj
	 * @return
	 */
	public static DateTime toDateTime(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Date) {
			return new DateTime(((Date) obj).getTime());
		}
		try {
			return new DateTime(DATETIME_FORMATTER.parse(obj.toString()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
