package com.sunsea.parkinghere.framework.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueModel {
	String value() default "";

	Class<? extends IValueProvider<?>> provider() default DefaultValueProvider.class;
}
