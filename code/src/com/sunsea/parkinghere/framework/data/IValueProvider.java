package com.sunsea.parkinghere.framework.data;

import java.util.Map;

public interface IValueProvider<T> {
	/**
	 * 取值
	 * 
	 * @param field
	 * @param valueModel
	 * @param obj
	 * @param resultClass
	 * @param params
	 * @return
	 */
	public T getValue(String field, ValueModel valueModel, Object obj, Class<?> resultClazz, Map<String, Object> params);

	/**
	 * 是否可以写<br>
	 * 如果返回不允许，将直接写入
	 * 
	 * @param field
	 * @param valueModel
	 * @param obj
	 * @param val
	 * @param params
	 * @return
	 */
	public boolean isSupportWrite(String field, ValueModel valueModel, Object obj, Object val, Map<String, Object> params);

	/**
	 * 写值
	 * 
	 * @param field
	 * @param valueModel
	 * @param obj
	 * @param val
	 * @param params
	 */
	public void setValue(String field, ValueModel valueModel, Object obj, Object val, Map<String, Object> params);
}
