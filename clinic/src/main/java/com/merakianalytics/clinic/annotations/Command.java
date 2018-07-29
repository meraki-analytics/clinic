package com.merakianalytics.clinic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merakianalytics.clinic.Default;

/**
 * Marks a method as a clinic command. Should only be used on static methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
    /**
     * @return whether the command should automatically create default Options for unannotated parameters
     */
    public boolean automatic() default true;

    /**
     * @return whether this Command is the default command to use if the user doesn't provide a command
     */
    public boolean defaultCommand() default false;

    /**
     * @return the help message for this command
     */
    public String help() default Default.STRING;

    /**
     * @return the name of this command
     */
    public String name() default Default.STRING;
}
