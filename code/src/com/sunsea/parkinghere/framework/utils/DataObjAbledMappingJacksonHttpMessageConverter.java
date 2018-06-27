package com.sunsea.parkinghere.framework.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import com.sunsea.parkinghere.framework.data.DataObj;
import com.sunsea.parkinghere.framework.data.DataObjUtils;
import com.sunsea.parkinghere.framework.data.IDataObj;
import com.sunsea.parkinghere.framework.utils.bean.ExBeanUtils;

public class DataObjAbledMappingJacksonHttpMessageConverter extends MappingJacksonHttpMessageConverter {

	public DataObjAbledMappingJacksonHttpMessageConverter() {
		super();
		ExBeanUtils.setFieldValue(DateSerializer.instance, DateTimeSerializerBase.class, "_customFormat", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"), true);
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		if (isDataObjAbleClass(clazz))
			return true;
		else
			return super.canRead(clazz, mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if (isDataObjAbleClass(clazz))
			return true;
		else
			return super.canWrite(clazz, mediaType);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		if (isDataObjAbleClass(clazz)) {
			Object retval = (DataObj) super.readInternal(DataObj.class, inputMessage);
			return isIDataObjClass(clazz) ? DataObjUtils.proxy((DataObj) retval, (Class<? extends IDataObj>) clazz) : retval;
		} else
			return super.readInternal(clazz, inputMessage);

	}

	private boolean isDataObjAbleClass(Class<?> clazz) {
		return isIDataObjClass(clazz) || DataObj.class.isAssignableFrom(clazz);
	}

	private boolean isIDataObjClass(Class<?> clazz) {
		return IDataObj.class.isAssignableFrom(clazz);
	}
}
