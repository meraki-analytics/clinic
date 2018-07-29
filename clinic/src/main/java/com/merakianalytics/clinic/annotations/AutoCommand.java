package com.merakianalytics.clinic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merakianalytics.clinic.Default;

/**
 * Marks a method as a clinic automatic command, using all parameters as options even if they are not annotated. Equivalent to setting the automatic option to
 * true on @Command. Should only be used on static methods.
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoCommand {
    /**
     * @return whether this Command is the default command to use if the user doesn't provide a command
     *
     * @since 1.0.0
     */
    public boolean defaultCommand() default false;

    /**
     * @return the help message for this command
     *
     * @since 1.0.0
     */
    public String help() default Default.STRING;

    /**
     * @return the name of this command
     *
     * @since 1.0.0
     */
    public String name() default Default.STRING;
}
