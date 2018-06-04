package com.emucoo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sj on 2018/6/1.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {
    String value() default "";
}
