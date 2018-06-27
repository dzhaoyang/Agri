package com.sunsea.parkinghere.framework.data;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeanUtils;

import com.sunsea.parkinghere.exception.DataObjProxyException;
import com.sunsea.parkinghere.exception.InternalException;

public final class DataObjUtils {
	public static Class<? extends IDataObj> getProxyClass(IDataObj dataObj) {
		assert dataObj != null : "代理对象不允许为null";
		return ((DataObjInvocationHandler) Proxy.getInvocationHandler(dataObj)).getProxyClazz();
	}

	@SuppressWarnings("unchecked")
	public static <E extends IDataObj> E newProxy(Class<E> clazz) {
		assert clazz != null : "代理接口不允许为null";
		try {
			return (E) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new DataObjInvocationHandler(
					clazz, new DataObj()));
		} catch (Exception ex) {
			throw new DataObjProxyException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static <E extends IDataObj> E proxy(Object entity, Class<E> clazz) {
		assert clazz != null : "代理接口不允许为null";
		try {
			return (E) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new DataObjInvocationHandler(
					clazz, entity));
		} catch (Exception ex) {
			throw new DataObjProxyException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static <E extends IDataObj> E as(IDataObj dataObj, Class<E> clazz) {
		assert clazz != null : "代理接口不允许为null";
		if (clazz.isAssignableFrom(dataObj.getClass()))
			return (E) dataObj;
		try {
			DataObjInvocationHandler handler = (DataObjInvocationHandler) ((DataObjInvocationHandler) Proxy
					.getInvocationHandler(dataObj)).clone();
			handler.setProxyClazz(clazz);
			return (E) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
		} catch (Exception ex) {
			throw new DataObjProxyException("转换类型时失败", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T go(IDataObj dataObj) {
		return (T) (dataObj == null ? null : ((DataObjInvocationHandler) Proxy.getInvocationHandler(dataObj)).getDelegate());
	}

	public static DataObj gogo(IDataObj dataObj) {
		return gogo(dataObj, false);
	}

	/**
	 * 强行将任意对象按接口生成DataObj,这个作用主要是一些查询的实体太复杂了，此处做强行生成
	 * 
	 * @param force
	 *          是否强行生成
	 * @return
	 */
	public static DataObj gogo(IDataObj dataObj, boolean force) {
		if (dataObj == null)
			return new DataObj();
		Object delegate = ((DataObjInvocationHandler) Proxy.getInvocationHandler(dataObj)).getDelegate();
		if (delegate instanceof DataObj) {
			return (DataObj) delegate;
		}
		DataObj retval = new DataObj();
		for (Method method : dataObj.getClass().getMethods()) {
			if (ExFieldUtils.isGetMethod(method)) {
				try {
					retval.put(ExFieldUtils.getBeanField(method), method.invoke(dataObj, new Object[] {}));
				} catch (Exception e) {
					if (!force)
						throw new InternalException(e);
				}
			}
		}
		retval.remove("class");
		return retval;
	}
	
	/**
	 * 强行将任意对象按接口生成DataObj,这个作用主要是一些查询的实体太复杂了，此处做强行生成
	 * 
	 * @param force
	 *          是否强行生成
	 * @param nullToEmptyStr
	 *          是否强行将null转换为""
	 * @return
	 */
	public static DataObj gogo(IDataObj dataObj, boolean force, boolean nullToEmptyStr) {
		if (dataObj == null)
			return new DataObj();
		Object delegate = ((DataObjInvocationHandler) Proxy.getInvocationHandler(dataObj)).getDelegate();
		if (delegate instanceof DataObj) {
			return (DataObj) delegate;
		}
		DataObj retval = new DataObj();
		for (Method method : dataObj.getClass().getMethods()) {
			if (ExFieldUtils.isGetMethod(method)) {
				try {
					String key = ExFieldUtils.getBeanField(method);
					Object value = method.invoke(dataObj, new Object[] {});
					if(nullToEmptyStr){
						value = value==null?"":value;
					}
					retval.put(key, value);
				} catch (Exception e) {
					if (!force)
						throw new InternalException(e);
				}
			}
		}
		retval.remove("class");
		return retval;
	}

	public static <E> E copyTo(IDataObj dataObj, E obj) {
		if (obj == null)
			return obj;
		if (dataObj == null)
			return obj;
		BeanUtils.copyProperties(go(dataObj), obj);
		return obj;
	}
}
