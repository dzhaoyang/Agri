package com.sunsea.parkinghere.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateToStringConverter implements Converter<Date, String> {
	private static final SimpleDateFormat longformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String convert(Date source) {
		return source == null ? "" : longformat.format(source);
	}

}
