package com.sunsea.parkinghere.framework.data;

import java.util.Map;

public final class DefaultValueProvider extends AbstractValueProvider<Object> {

	@Override
	public Object getValue(String field, ValueModel valueModel, Object obj, Class<?> resultClass,
			Map<String, Object> params) {
		return null;
	}

	@Override
	public boolean isSupportWrite(String field, ValueModel valueModel, Object obj, Object val, Map<String, Object> params) {
		return false;
	}

	@Override
	public void setValue(String field, ValueModel valueModel, Object obj, Object val, Map<String, Object> params) {
	}

}
