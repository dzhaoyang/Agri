package com.sunsea.parkinghere.framework.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.sunsea.parkinghere.exception.InternalException;

public final class DataObjInvocationHandler implements InvocationHandler {
	private static final Map<Class<? extends IDataObj>, Map<String, ValueModel>> DATAOBJMETAS = new HashMap<Class<? extends IDataObj>, Map<String, ValueModel>>();

	@JsonIgnore
	private Class<? extends IDataObj> proxyClazz;
	@JsonIgnore
	private Object delegate;
	@JsonIgnore
	private Map<String, Object> params;
	@JsonIgnore
	private boolean isDataMap;

	public DataObjInvocationHandler(Class<? extends IDataObj> proxyClazz, Object delegate) {
		this(proxyClazz, delegate, null);
	}

	public DataObjInvocationHandler(Class<? extends IDataObj> proxyClazz, Object delegate, Map<String, Object> params) {
		this.proxyClazz = proxyClazz;
		this.delegate = delegate;
		this.params = params;
		this.isDataMap = delegate instanceof DataObj;
		scanValueModelAnnonation();
	}

	public Class<? extends IDataObj> getProxyClazz() {
		return proxyClazz;
	}

	/**
	 * 设置代理类
	 * 
	 * @param proxyClazz
	 *          set the proxyClazz
	 */
	void setProxyClazz(Class<? extends IDataObj> proxyClazz) {
		this.proxyClazz = proxyClazz;
	}

	public Map<String, Object> getParams() {
		return Collections.unmodifiableMap(params);
	}

	@SuppressWarnings("unchecked")
	public <T> T getDelegate() {
		return (T) delegate;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (ExFieldUtils.isBeanMethod(method)) {
			Object retval = null;
			String field = ExFieldUtils.getBeanField(method);
			if (ExFieldUtils.isGetMethod(method)) {
				if (method.getReturnType() == null)
					throw new InternalException("方法没有定义返回值!" + method.toString());
				return getValue(field, method.getReturnType());
			} else {
				setValue(field, args[0]);
			}
			return retval;
		} else {
			return method.invoke(delegate, args);
		}
	}

	/**
	 * 要测试一下，这个是否有值
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private void scanValueModelAnnonation() {
		Map<String, ValueModel> valueModelMetas = DATAOBJMETAS.get(proxyClazz);
		if (valueModelMetas == null) {
			synchronized (DATAOBJMETAS) {
				valueModelMetas = DATAOBJMETAS.get(proxyClazz);
				if (valueModelMetas == null) {
					valueModelMetas = new HashMap<String, ValueModel>();
					for (Method method : proxyClazz.getMethods()) {
						if (ExFieldUtils.isGetMethod(method)) {
							ValueModel valueModel = method.getAnnotation(ValueModel.class);
							if (valueModel != null) {
								valueModelMetas.put(ExFieldUtils.getBeanField(method), valueModel);
							}
						}
					}
					DATAOBJMETAS.put(proxyClazz, valueModelMetas);
				}
			}
		}
	}

	private ValueModel getFieldValueModel(String field) {
		Map<String, ValueModel> valueModelMetas = DATAOBJMETAS.get(proxyClazz);
		return valueModelMetas == null ? null : valueModelMetas.get(field);
	}

	private Object getValue(String field, Class<?> resultClazz) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object retval = null;
		ValueModel valueModel = getFieldValueModel(field);
		if (valueModel == null) {
			retval = doGetValue(field, resultClazz);
		} else {
			Class<? extends IValueProvider<?>> providerClazz = valueModel.provider();
			if (providerClazz == null || providerClazz == DefaultValueProvider.class) {
				if (StringUtils.isBlank(valueModel.value())) {
					retval = doGetValue(field, resultClazz);
				} else {
					retval = doGetValue(valueModel.value(), resultClazz);
				}
			} else {
				IValueProvider<?> provider = ValueProviderFactory.getValueProvider(providerClazz);
				retval = provider.getValue(field, valueModel, delegate, resultClazz, params);
				retval = ConvertUtils.convert(retval, resultClazz);
			}
		}
		return retval;
	}

	/**
	 * 原来考虑保存转型结果，现在放弃，主要是因为支持对象后，可能设置失败
	 * 
	 * @param field
	 * @param resultClazz
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	private Object doGetValue(String field, Class<?> resultClazz) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Object retval = null;
		if (isDataMap) {
			retval = ((DataObj) delegate).get(field);
		} else {
			try {
				retval = PropertyUtils.getProperty(delegate, field);
			} catch (NestedNullException e) {
			}
		}
		if (resultClazz.isEnum() && IEnumValue.class.isAssignableFrom(resultClazz)) {
			return IEnumValueUtils.valAsEnumValue(retval, (Class<IEnumValue<Object>>) resultClazz);
		} else if(Time.class.isAssignableFrom(resultClazz)) {
			return ExDateUtils.toTime(retval);
		} else if(Date.class.isAssignableFrom(resultClazz)) {
			return ExDateUtils.toDate(retval);
		} else if(DateTime.class.isAssignableFrom(resultClazz)) {
			return ExDateUtils.toDateTime(retval);
		}
		return ConvertUtils.convert(retval, resultClazz);
	}

	private void setValue(String field, Object value) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		ValueModel valueModel = getFieldValueModel(field);
		if (valueModel == null) {
			PropertyUtils.setProperty(delegate, field, value);
		} else {
			Class<? extends IValueProvider<?>> providerClazz = valueModel.provider();
			if (providerClazz == null || providerClazz == DefaultValueProvider.class) {
				if (StringUtils.isBlank(valueModel.value())) {
					PropertyUtils.setProperty(delegate, field, value);
				} else {
					PropertyUtils.setProperty(delegate, valueModel.value(), value);
				}
			} else {
				IValueProvider<?> provider = ValueProviderFactory.getValueProvider(providerClazz);
				if (provider.isSupportWrite(field, valueModel, delegate, value, params)) {
					provider.setValue(field, valueModel, delegate, value, params);
				} else {
					if (StringUtils.isBlank(valueModel.value())) {
						PropertyUtils.setProperty(delegate, field, value);
					} else {
						PropertyUtils.setProperty(delegate, valueModel.value(), value);
					}
				}
			}
		}
	}
}
