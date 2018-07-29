package com.merakianalytics.clinic;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.merakianalytics.clinic.exceptions.ClinicAnnotationException;
import com.merakianalytics.clinic.exceptions.ClinicException;
import com.merakianalytics.clinic.exceptions.ClinicGetHelpException;
import com.merakianalytics.clinic.exceptions.ClinicParseException;

/**
 * A clinic CLI command
 */
public class Command {
    private static class AnnotationData {
        public static AnnotationData from(final Method method, final com.merakianalytics.clinic.annotations.AutoCommand annotation) {
            final String name = Default.STRING.equals(annotation.name()) ? Common.toHypenCase(method.getName()) : annotation.name();
            final String help = Default.STRING.equals(annotation.help()) ? null : annotation.help();
            final boolean defaultCommand = annotation.defaultCommand();
            final Option[] options = getOptions(method, true);

            return new AnnotationData(name, help, defaultCommand, options);
        }

        public static AnnotationData from(final Method method, final com.merakianalytics.clinic.annotations.Command annotation) {
            final boolean automatic = annotation.automatic();
            final String name = Default.STRING.equals(annotation.name()) ? Common.toHypenCase(method.getName()) : annotation.name();
            final String help = Default.STRING.equals(annotation.help()) ? null : annotation.help();
            final boolean defaultCommand = annotation.defaultCommand();
            final Option[] options = getOptions(method, automatic);

            return new AnnotationData(name, help, defaultCommand, options);
        }

        private static Option[] getOptions(final Method method, final boolean automatic) {
            return Arrays.stream(method.getParameters()).map((final Parameter parameter) -> {
                if(!parameter.isAnnotationPresent(com.merakianalytics.clinic.annotations.Option.class) && !automatic) {
                    throw new ClinicAnnotationException(method.getName()
                        + " is a @Command but has parameters without @Option annotations! Either add @Option annotations to its parameters or make it an automatic command by setting automatic = true or using @AutoCommand instead!");
                }
                return Option.get(method, parameter);
            }).toArray(Option[]::new);
        }

        public boolean defaultCommand;
        public String help;
        public String name;
        public Option[] options;

        private AnnotationData(final String name, final String help, final boolean defaultCommand, final Option[] options) {
            this.name = name;
            this.help = help;
            this.defaultCommand = defaultCommand;
            this.options = options;
        }
    }

    /**
     * Gets a {@link com.merakianalytics.clinic.Command} from a {@link com.merakianalytics.clinic.annotations.Command}-annotated
     * {@link java.lang.reflect.Method}
     *
     * @param executableName
     *        the executable name that launches your application
     * @param method
     *        the method to get a {@link com.merakianalytics.clinic.Command} from
     * @return the {@link com.merakianalytics.clinic.Command} specified by the {@link java.lang.reflect.Method} and its clinic annotations
     */
    public static Command get(final String executableName, final Method method) {
        if(!Modifier.isStatic(method.getModifiers())) {
            throw new ClinicAnnotationException(
                "Found an @Command annotation on " + method.getName() + ", but the method is not static. All @Command methods must be static.");
        }

        final AnnotationData annotationData;
        if(method.isAnnotationPresent(com.merakianalytics.clinic.annotations.Command.class)) {
            annotationData = AnnotationData.from(method, method.getAnnotation(com.merakianalytics.clinic.annotations.Command.class));
        } else if(method.isAnnotationPresent(com.merakianalytics.clinic.annotations.AutoCommand.class)) {
            annotationData = AnnotationData.from(method, method.getAnnotation(com.merakianalytics.clinic.annotations.AutoCommand.class));
        } else {
            annotationData = AnnotationData.from(method, Default.command(method));
        }

        // Verify there aren't any duplicate option names
        final Set<String> optionNames = new HashSet<>();
        for(final Option option : annotationData.options) {
            if(option == null) {
                continue;
            }
            for(final String n : option.getNames()) {
                if(optionNames.contains(n)) {
                    throw new ClinicAnnotationException("Duplicate option name " + n + "!");
                }
                optionNames.add(n);
            }
        }

        return new Command(method, annotationData.name, executableName, annotationData.help, annotationData.defaultCommand, annotationData.options);
    }

    private static String toString(final Object object) {
        if(object == null) {
            return "null";
        }
        if(object.getClass().isArray()) {
            final StringBuilder builder = new StringBuilder();
            final int length = Array.getLength(object);
            for(int i = 0; i < length; i++) {
                builder.append(", " + Array.get(object, i).toString());
            }
            return builder.substring(length > 0 ? 2 : 0);
        }
        if(Collection.class.isAssignableFrom(object.getClass())) {
            final Collection<?> collection = (Collection<?>)object;
            final StringBuilder builder = new StringBuilder();
            for(final Object item : collection) {
                builder.append(", " + item);
            }
            return builder.substring(collection.size() > 0 ? 2 : 0);
        }
        return object.toString();
    }

    private static String unquote(final String string) {
        if(string.charAt(0) == '\'' && string.charAt(string.length() - 1) == '\'' ||
            string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"') {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }

    private final boolean defaultCommand;
    private final String executableName;
    private final String help;
    private final Method method;
    private final String name;
    private final Option[] options;

    private Command(final Method method, final String name, final String executableName, final String help, final boolean defaultCommand,
        final Option[] options) {
        this.method = method;
        this.name = name;
        this.executableName = executableName;
        this.help = help;
        this.defaultCommand = defaultCommand;
        this.options = options;
    }

    /**
     * Executes this command with the provided option values
     *
     * @param options
     *        the option values to use
     * @return the result of executing the command with the provided option values
     */
    public Object execute(final List<String> options) {
        return execute(options.toArray(new String[options.size()]));
    }

    /**
     * Executes this command with the provided option values
     *
     * @param arguments
     *        the arguments passed to this command
     * @return the result of executing the command with the provided option values
     */
    public Object execute(final String... arguments) {
        final Object[] parameters = new Object[options.length];

        // Set the default values for parameters
        // Get the set of required options
        // Get all the names options might be referred to by and which parameter index they map to
        final Map<String, Integer> indexes = new HashMap<>();
        final Set<Option> required = new HashSet<>();
        for(int i = 0; i < options.length; i++) {
            if(options[i] == null) {
                parameters[i] = Default.value(method.getParameters()[i].getType());
                continue;
            }

            if(options[i].isRequired()) {
                required.add(options[i]);
            }

            parameters[i] = options[i].getDefaultValue();
            for(final String name : options[i].getNames()) {
                indexes.put(name, i);
            }
        }

        // Parse option values from arguments
        Option lastOption = null;
        String lastName = null;
        for(int i = 0; i < arguments.length; i++) {
            if(Common.HELP_OPTION.equals(arguments[i])) {
                throw new ClinicGetHelpException(getCommandHelp());
            }

            // Assume loop enters at the beginning of an option
            if(!arguments[i].startsWith("-")) {
                String message = "Unexpected command line argument " + arguments[i] + "!";
                if(lastOption != null && lastOption.isFlag()) {
                    message += " " + lastName + " is a flag and doesn't take any arguments.";
                } else if(lastOption != null && !lastOption.isMultiArgument()) {
                    message += " " + lastName + " only takes one argument.";
                }
                throw new ClinicParseException(message + System.lineSeparator() + getCommandHelp());
            }

            final Integer index = indexes.get(arguments[i]);
            if(index == null) {
                throw new ClinicParseException(
                    "Unrecognized commmand line option " + arguments[i] + "!" + System.lineSeparator() + getCommandHelp());
            }

            final Option option = options[index];
            required.remove(option);
            lastOption = option;
            lastName = arguments[i];

            // Flags take no arguments
            if(option.isFlag()) {
                parameters[index] = option.getValue();
                continue;
            }

            // Error if an argument is required but we're at the end
            if(i + 1 >= arguments.length) {
                throw new ClinicParseException(arguments[i] + " takes " + (option.isMultiArgument() ? "at least 1" : "1") + " argument but got 0!"
                    + System.lineSeparator() + getCommandHelp());
            }

            // Error if the next token is an option instead of an argument
            if(arguments[i + 1].startsWith("-")) {
                throw new ClinicParseException(arguments[i] + " takes " + (option.isMultiArgument() ? "at least 1" : "1") + " argument but got 0!"
                    + System.lineSeparator() + getCommandHelp());
            }

            if(!option.isMultiArgument()) {
                final String argument = unquote(arguments[i + 1]);
                try {
                    parameters[index] = option.getValue(argument);
                } catch(final Exception e) {
                    throw new ClinicParseException("Failed to convert argument \"" + argument + "\" to " + option.getParameter().getType().getSimpleName()
                        + " for option " + String.join("/", option.getNames()) + "!" + System.lineSeparator() + getCommandHelp(), e);
                }
                i += 1;
                continue;
            }

            int endArgument = i + 1;
            while(endArgument < arguments.length && !arguments[endArgument].startsWith("-")) {
                endArgument++;
            }
            final String[] argument = Arrays.stream(arguments, i + 1, endArgument).map(Command::unquote).toArray(String[]::new);
            try {
                parameters[index] = option.getValue(argument);
            } catch(final Exception e) {
                throw new ClinicParseException("Failed to convert argument \"" + String.join(" ", argument) + "\" to "
                    + option.getParameter().getType().getSimpleName() + " for option " + String.join("/", option.getNames()) + "!" + System.lineSeparator()
                    + System.lineSeparator() + getCommandHelp(), e);
            }
            i += endArgument - (i + 1);
        }

        // Did we miss any required options
        if(!required.isEmpty()) {
            final StringBuilder message = new StringBuilder("Missing required option" + (required.size() > 1 ? "s:" : ":"));
            for(final Option option : required) {
                message.append(" " + String.join("/", option.getNames()));
            }
            throw new ClinicParseException(message.toString() + System.lineSeparator() + getCommandHelp());
        }

        try {
            return method.invoke(null, parameters);
        } catch(final IllegalAccessException e) {
            throw new ClinicException("Couldn't get access to " + method.getName() + "! Check its access modifiers and ensure it is visible to clinic.", e);
        } catch(final IllegalArgumentException e) {
            throw new ClinicException("Something unexpected went wrong calling " + getName() + ". Report this to the clinic team.", e);
        } catch(final InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if(cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw new RuntimeException(null, cause);
        }
    }

    private String getCommandHelp() {
        final StringBuilder builder = new StringBuilder(System.lineSeparator());
        builder.append("Usage:  " + executableName + " " + name + (options.length > 0 ? " [OPTIONS]" : "") + System.lineSeparator());
        builder.append(System.lineSeparator());
        if(help != null) {
            builder.append(help + System.lineSeparator());
            builder.append(System.lineSeparator());
        }

        if(options.length > 0) {
            int maxNamesLength = 0;
            for(final Option option : options) {
                final int namesLength = Arrays.stream(option.getNames()).mapToInt(String::length).sum() + 2 * (option.getNames().length - 1);
                if(namesLength > maxNamesLength) {
                    maxNamesLength = namesLength;
                }
            }

            builder.append("Options:" + System.lineSeparator());
            for(final Option option : options) {
                final String names = String.join(", ", option.getNames());
                builder.append("  " + names);
                if(option.getHelp() != null || option.isRequired() || option.isShowDefault()) {
                    final int buffer = maxNamesLength - names.length();
                    for(int i = 0; i < buffer; i++) {
                        builder.append(' ');
                    }
                    builder.append("  ");
                    if(option.getHelp() != null) {
                        builder.append(" " + option.getHelp());
                    }
                    if(option.isRequired()) {
                        builder.append(" (required)");
                    }
                    if(option.isShowDefault()) {
                        builder.append(" (default " + toString(option.getDefaultValue()) + ")");
                    }
                }
                builder.append(System.lineSeparator());
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    /**
     * @return the help message for the command
     */
    public String getHelp() {
        return help;
    }

    /**
     * @return the method that the command calls
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * @return the options the command has
     */
    public Option[] getOptions() {
        return options;
    }

    /**
     * @return whether the command is the default command to use if none is specified
     */
    public boolean isDefaultCommand() {
        return defaultCommand;
    }
}
