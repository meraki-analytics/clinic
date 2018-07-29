package com.merakianalytics.clinic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merakianalytics.clinic.Default;

/**
 * Marks a parameter as a clinic option.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Option {
    /**
     * @return the default value to use for a boolean or Boolean parameter
     */
    public boolean defaultBoolean() default Default.BOOLEAN;

    /**
     * @return the default value to use for a boolean[], Boolean[], or Boolean Collection parameter
     */
    public boolean[] defaultBooleans() default {};

    /**
     * @return the default value to use for a byte or Byte parameter
     */
    public byte defaultByte() default Default.BYTE;

    /**
     * @return the default value to use for a byte[], Byte[], or Byte Collection parameter
     */
    public byte[] defaultBytes() default {};

    /**
     * @return the default value to use for a char or Character parameter
     */
    public char defaultChar() default Default.CHAR;

    /**
     * @return the default value to use for a char[], Character[], or Character Collection parameter
     */
    public char[] defaultChars() default {};

    /**
     * @return the default value to use for a double or Double parameter
     */
    public double defaultDouble() default Default.DOUBLE;

    /**
     * @return the default value to use for a double[], Double[], or Double Collection parameter
     */
    public double[] defaultDoubles() default {};

    /**
     * @return the default value to use for a float or Float parameter
     */
    public float defaultFloat() default Default.FLOAT;

    /**
     * @return the default value to use for a float[], Float[], or Float Collection parameter
     */
    public float[] defaultFloats() default {};

    /**
     * @return the default value to use for an int or Integer parameter
     */
    public int defaultInt() default Default.INT;

    /**
     * @return the default value to use for a int[], Integer[], or Integer Collection parameter
     */
    public int[] defaultInts() default {};

    /**
     * @return the default value to use for a long or Long parameter
     */
    public long defaultLong() default Default.LONG;

    /**
     * @return the default value to use for a long[], Long[], or Long Collection parameter
     */
    public long[] defaultLongs() default {};

    /**
     * @return the default value to use for a short or Short parameter
     */
    public short defaultShort() default Default.SHORT;

    /**
     * @return the default value to use for a short[], Short[], or Short Collection parameter
     */
    public short[] defaultShorts() default {};

    /**
     * @return the default value to use for a String parameter
     */
    public String defaultString() default Default.STRING;

    /**
     * @return the default value for a String[] or String Collection parameter
     */
    public String[] defaultStrings() default {};

    /**
     * @return the default value for an arbitrary-typed parameter with a String-only constructor
     */
    public String defaultValue() default Default.STRING;

    /**
     * @return the default value for an array or Collection parameter of any arbitrary type that has a String-only constructor
     */
    public String[] defaultValues() default {};

    /**
     * @return whether this boolean or Boolean parameter is an argument-less flag
     */
    public boolean flag() default false;

    /**
     * @return the help message for this option
     */
    public String help() default Default.STRING;

    /**
     * @return the valid names for this option
     */
    public String[] names() default {};

    /**
     * @return whether this option is required
     */
    public boolean required() default false;

    /**
     * @return whether to show the default value when printing a help message for this option
     */
    public boolean showDefault() default true;

    /**
     * @return the inner type specified in a Collection type (required because of runtime generic type erasure)
     */
    public Class<?> type() default void.class;
}
