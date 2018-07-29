package com.merakianalytics.clinic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExpectedOption {
    public int defaultValueHashCode();

    public boolean flag();

    public String help();

    public boolean multiArgument();

    public String[] names();

    public boolean required();

    public boolean showDefault();

    public Class<?> type() default void.class;
}
