package com.merakianalytics.clinic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merakianalytics.clinic.Default;

/**
 * Marks a parameter as a clinic option.
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Option {
    /**
     * @return the default value to use for a boolean or Boolean parameter
     *
     * @since 1.0.0
     */
    public boolean defaultBoolean() default Default.BOOLEAN;

    /**
     * @return the default value to use for a boolean[], Boolean[], or Boolean Collection parameter
     *
     * @since 1.0.0
     */
    public boolean[] defaultBooleans() default {};

    /**
     * @return the default value to use for a byte or Byte parameter
     *
     * @since 1.0.0
     */
    public byte defaultByte() default Default.BYTE;

    /**
     * @return the default value to use for a byte[], Byte[], or Byte Collection parameter
     *
     * @since 1.0.0
     */
    public byte[] defaultBytes() default {};

    /**
     * @return the default value to use for a char or Character parameter
     *
     * @since 1.0.0
     */
    public char defaultChar() default Default.CHAR;

    /**
     * @return the default value to use for a char[], Character[], or Character Collection parameter
     *
     * @since 1.0.0
     */
    public char[] defaultChars() default {};

    /**
     * @return the default value to use for a double or Double parameter
     *
     * @since 1.0.0
     */
    public double defaultDouble() default Default.DOUBLE;

    /**
     * @return the default value to use for a double[], Double[], or Double Collection parameter
     *
     * @since 1.0.0
     */
    public double[] defaultDoubles() default {};

    /**
     * @return the default value to use for a float or Float parameter
     *
     * @since 1.0.0
     */
    public float defaultFloat() default Default.FLOAT;

    /**
     * @return the default value to use for a float[], Float[], or Float Collection parameter
     *
     * @since 1.0.0
     */
    public float[] defaultFloats() default {};

    /**
     * @return the default value to use for an int or Integer parameter
     *
     * @since 1.0.0
     */
    public int defaultInt() default Default.INT;

    /**
     * @return the default value to use for a int[], Integer[], or Integer Collection parameter
     *
     * @since 1.0.0
     */
    public int[] defaultInts() default {};

    /**
     * @return the default value to use for a long or Long parameter
     *
     * @since 1.0.0
     */
    public long defaultLong() default Default.LONG;

    /**
     * @return the default value to use for a long[], Long[], or Long Collection parameter
     *
     * @since 1.0.0
     */
    public long[] defaultLongs() default {};

    /**
     * @return the default value to use for a short or Short parameter
     *
     * @since 1.0.0
     */
    public short defaultShort() default Default.SHORT;

    /**
     * @return the default value to use for a short[], Short[], or Short Collection parameter
     *
     * @since 1.0.0
     */
    public short[] defaultShorts() default {};

    /**
     * @return the default value to use for a String parameter
     *
     * @since 1.0.0
     */
    public String defaultString() default Default.STRING;

    /**
     * @return the default value for a String[] or String Collection parameter
     *
     * @since 1.0.0
     */
    public String[] defaultStrings() default {};

    /**
     * @return the default value for an arbitrary-typed parameter with a String-only constructor
     *
     * @since 1.0.0
     */
    public String defaultValue() default Default.STRING;

    /**
     * @return the default value for an array or Collection parameter of any arbitrary type that has a String-only constructor
     *
     * @since 1.0.0
     */
    public String[] defaultValues() default {};

    /**
     * @return whether this boolean or Boolean parameter is an argument-less flag
     *
     * @since 1.0.0
     */
    public boolean flag() default false;

    /**
     * @return the help message for this option
     *
     * @since 1.0.0
     */
    public String help() default Default.STRING;

    /**
     * @return the valid names for this option
     *
     * @since 1.0.0
     */
    public String[] names() default {};

    /**
     * @return whether this option is required
     *
     * @since 1.0.0
     */
    public boolean required() default false;

    /**
     * @return whether to show the default value when printing a help message for this option
     *
     * @since 1.0.0
     */
    public boolean showDefault() default true;

    /**
     * @return the inner type specified in a Collection type (required because of runtime generic type erasure)
     *
     * @since 1.0.0
     */
    public Class<?> type() default void.class;
}
