package com.merakianalytics.clinic;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.merakianalytics.clinic.exceptions.ClinicAnnotationException;
import com.merakianalytics.clinic.exceptions.ClinicException;

/**
 * A clinic CLI option for a command
 */
public class Option {
    private static final Set<Class<?>> WRAPPER_CLASSES = new HashSet<>(Arrays.asList(new Class<?>[] {
        Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class
    }));

    private static boolean anyDefaultSet(final com.merakianalytics.clinic.annotations.Option annotation) {
        return Default.BOOLEAN != annotation.defaultBoolean() ||
            Default.BYTE != annotation.defaultByte() ||
            Default.CHAR != annotation.defaultChar() ||
            Default.DOUBLE != annotation.defaultDouble() ||
            Default.FLOAT != annotation.defaultFloat() ||
            Default.INT != annotation.defaultInt() ||
            Default.LONG != annotation.defaultLong() ||
            Default.SHORT != annotation.defaultShort() ||
            !Default.STRING.equals(annotation.defaultString()) ||
            !Default.STRING.equals(annotation.defaultValue()) ||
            annotation.defaultBooleans().length > 0 ||
            annotation.defaultBytes().length > 0 ||
            annotation.defaultChars().length > 0 ||
            annotation.defaultDoubles().length > 0 ||
            annotation.defaultFloats().length > 0 ||
            annotation.defaultInts().length > 0 ||
            annotation.defaultLongs().length > 0 ||
            annotation.defaultShorts().length > 0 ||
            annotation.defaultStrings().length > 0 ||
            annotation.defaultValues().length > 0;
    }

    private static String[] ensureDashes(final String[] names) {
        for(int i = 0; i < names.length; i++) {
            if(!names[i].startsWith("-")) {
                names[i] = names[i].length() > 1 ? "--" + names[i] : "-" + names[i];
            }
        }
        return names;
    }

    /**
     * Gets a {@link com.merakianalytics.clinic.Option} from a {@link com.merakianalytics.clinic.annotations.Option}-annotated
     * {@link java.lang.reflect.Parameter}
     *
     * @param parameter
     *        the parameter to get a {@link com.merakianalytics.clinic.Option} from
     * @return the {@link com.merakianalytics.clinic.Option} specified by the {@link java.lang.reflect.Parameter} and its clinic annotations
     */
    public static Option get(final Parameter parameter) {
        com.merakianalytics.clinic.annotations.Option annotation = parameter.getAnnotation(com.merakianalytics.clinic.annotations.Option.class);
        if(annotation == null) {
            annotation = Default.option(parameter);
        }

        String[] names;
        if(annotation.names().length > 0) {
            names = ensureDashes(annotation.names());
            for(final String name : names) {
                if(Common.HELP_OPTION.equals(name)) {
                    throw new ClinicAnnotationException("--help is reserved and can't be used as an option name!");
                }
            }
        } else {
            names = getNames(parameter);
            for(final String name : names) {
                if(Common.HELP_OPTION.equals(name)) {
                    throw new ClinicAnnotationException(
                        "Found an @Option called help that didn't have a name specified! --help is reserved and can't be used as an option name, so please add a name to its @Option annotation!");
                }
            }
        }
        Arrays.sort(names, (final String one, final String two) -> {
            final int size = Integer.compare(one.length(), two.length());
            return size != 0 ? size : one.compareTo(two);
        });

        final boolean required = annotation.required();
        if(required && anyDefaultSet(annotation)) {
            throw new ClinicAnnotationException("Can't set default for required option " + String.join("/", names) + "!");
        }

        final Object defaultValue = required ? null : getDefaultValue(parameter.getType(), annotation, names);
        final String help = Default.STRING.equals(annotation.help()) ? null : annotation.help();
        final boolean showDefault = required ? false : annotation.showDefault();
        final boolean flag = annotation.flag();
        final boolean multiArgument = parameter.getType().isArray() || Collection.class.isAssignableFrom(parameter.getType());
        final Class<?> genericType = Default.CLASS.equals(annotation.type()) ? null : annotation.type();

        if(flag && !boolean.class.equals(parameter.getType()) && !Boolean.class.equals(parameter.getType())) {
            throw new ClinicAnnotationException("The @Option " + String.join("/", names) + " is set as a flag but it isn't a boolean!");
        }

        return new Option(parameter, names, required, defaultValue, help, showDefault, flag, multiArgument, genericType);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Object getDefaultValue(final Class<?> type, final com.merakianalytics.clinic.annotations.Option annotation, final String[] names) {
        if(type.isPrimitive() || WRAPPER_CLASSES.contains(type)) {
            if(boolean.class.equals(type) || Boolean.class.equals(type)) {
                if(Default.BOOLEAN == annotation.defaultBoolean() && !Default.STRING.equals(annotation.defaultValue())) {
                    return Boolean.parseBoolean(annotation.defaultValue());
                }
                return annotation.defaultBoolean();
            }
            if(byte.class.equals(type) || Byte.class.equals(type)) {
                if(Default.BYTE == annotation.defaultByte() && !Default.STRING.equals(annotation.defaultValue())) {
                    return Byte.parseByte(annotation.defaultValue());
                }
                return annotation.defaultByte();
            }
            if(char.class.equals(type) || Character.class.equals(type)) {
                if(Default.CHAR == annotation.defaultChar() && !Default.STRING.equals(annotation.defaultValue())) {
                    if(annotation.defaultValue().length() != 1) {
                        throw new ClinicAnnotationException("char or Character defaultValue must be of length 1!");
                    }
                    return annotation.defaultValue().charAt(0);
                }
                return annotation.defaultChar();
            }
            if(double.class.equals(type) || Double.class.equals(type)) {
                if(Default.DOUBLE == annotation.defaultDouble() && !Default.STRING.equals(annotation.defaultValue())) {
                    return Double.parseDouble(annotation.defaultValue());
                }
                return annotation.defaultDouble();
            }
            if(float.class.equals(type) || Float.class.equals(type)) {
                if(Default.FLOAT == annotation.defaultFloat() && !Default.STRING.equals(annotation.defaultValue())) {
                    return Float.parseFloat(annotation.defaultValue());
                }
                return annotation.defaultFloat();
            }
            if(int.class.equals(type) || Integer.class.equals(type)) {
                if(Default.INT == annotation.defaultInt() && !Default.STRING.equals(annotation.defaultValue())) {
                    return Integer.parseInt(annotation.defaultValue());
                }
                return annotation.defaultInt();
            }
            if(long.class.equals(type) || Long.class.equals(type)) {
                if(Default.LONG == annotation.defaultLong() && !Default.STRING.equals(annotation.defaultValue())) {
                    return Long.parseLong(annotation.defaultValue());
                }
                return annotation.defaultLong();
            }
            if(short.class.equals(type) || Short.class.equals(type)) {
                if(Default.SHORT == annotation.defaultShort() && !Default.STRING.equals(annotation.defaultValue())) {
                    return Short.parseShort(annotation.defaultValue());
                }
                return annotation.defaultShort();
            }

            throw new ClinicException("Tried to get default value for a primitive type or wrapper class, but " + type.getSimpleName()
                + " didn't match any! Report this to the clinic team.");
        }

        if(String.class.equals(type)) {
            if(Default.STRING.equals(annotation.defaultString()) && !Default.STRING.equals(annotation.defaultValue())) {
                return annotation.defaultValue();
            }
            return Default.STRING.equals(annotation.defaultString()) ? null : annotation.defaultString();
        }

        if(type.isArray()) {
            if(type.getComponentType().isPrimitive() || WRAPPER_CLASSES.contains(type.getComponentType())) {
                if(boolean[].class.equals(type)) {
                    if(annotation.defaultBooleans().length == 0 && annotation.defaultValues().length != 0) {
                        final boolean[] value = new boolean[annotation.defaultValues().length];
                        for(int i = 0; i < value.length; i++) {
                            value[i] = Boolean.parseBoolean(annotation.defaultValues()[i]);
                        }
                        return value;
                    }
                    return Arrays.copyOf(annotation.defaultBooleans(), annotation.defaultBooleans().length);
                }
                if(Boolean[].class.equals(type)) {
                    if(annotation.defaultBooleans().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map(Boolean::valueOf).toArray(Boolean[]::new);
                    }
                    return IntStream.range(0, annotation.defaultBooleans().length).mapToObj((final int i) -> annotation.defaultBooleans()[i])
                        .toArray(Boolean[]::new);
                }

                if(byte[].class.equals(type)) {
                    if(annotation.defaultBytes().length == 0 && annotation.defaultValues().length != 0) {
                        final byte[] value = new byte[annotation.defaultValues().length];
                        for(int i = 0; i < value.length; i++) {
                            value[i] = Byte.parseByte(annotation.defaultValues()[i]);
                        }
                        return value;
                    }
                    return Arrays.copyOf(annotation.defaultBytes(), annotation.defaultBytes().length);
                }
                if(Byte[].class.equals(type)) {
                    if(annotation.defaultBytes().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map(Byte::valueOf).toArray(Byte[]::new);
                    }
                    return IntStream.range(0, annotation.defaultBytes().length).mapToObj((final int i) -> annotation.defaultBytes()[i]).toArray(Byte[]::new);
                }

                if(char[].class.equals(type)) {
                    if(annotation.defaultChars().length == 0 && annotation.defaultValues().length != 0) {
                        final char[] value = new char[annotation.defaultValues().length];
                        for(int i = 0; i < value.length; i++) {
                            if(annotation.defaultValues()[i].length() != 1) {
                                throw new ClinicAnnotationException("char or Character defaultValues can only be of length 1!");
                            }
                            value[i] = annotation.defaultValues()[i].charAt(0);
                        }
                        return value;
                    }
                    return Arrays.copyOf(annotation.defaultChars(), annotation.defaultChars().length);
                }
                if(Character[].class.equals(type)) {
                    if(annotation.defaultChars().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map((final String value) -> {
                            if(value.length() != 1) {
                                throw new ClinicAnnotationException("char or Character defaultValues must be of length 1!");
                            }
                            return value.charAt(0);
                        }).toArray(Character[]::new);
                    }
                    return IntStream.range(0, annotation.defaultChars().length).mapToObj((final int i) -> annotation.defaultChars()[i])
                        .toArray(Character[]::new);
                }

                if(double[].class.equals(type)) {
                    if(annotation.defaultDoubles().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).mapToDouble(Double::parseDouble).toArray();
                    }
                    return Arrays.copyOf(annotation.defaultDoubles(), annotation.defaultDoubles().length);
                }
                if(Double[].class.equals(type)) {
                    if(annotation.defaultDoubles().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map(Double::valueOf).toArray(Double[]::new);
                    }
                    return IntStream.range(0, annotation.defaultDoubles().length).mapToObj((final int i) -> annotation.defaultDoubles()[i])
                        .toArray(Double[]::new);
                }

                if(float[].class.equals(type)) {
                    if(annotation.defaultFloats().length == 0 && annotation.defaultValues().length != 0) {
                        final float[] value = new float[annotation.defaultValues().length];
                        for(int i = 0; i < value.length; i++) {
                            value[i] = Float.parseFloat(annotation.defaultValues()[i]);
                        }
                        return value;
                    }
                    return Arrays.copyOf(annotation.defaultFloats(), annotation.defaultFloats().length);
                }
                if(Float[].class.equals(type)) {
                    if(annotation.defaultFloats().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map(Float::valueOf).toArray(Float[]::new);
                    }
                    return IntStream.range(0, annotation.defaultFloats().length).mapToObj((final int i) -> annotation.defaultFloats()[i]).toArray(Float[]::new);
                }

                if(int[].class.equals(type)) {
                    if(annotation.defaultInts().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).mapToInt(Integer::parseInt).toArray();
                    }
                    return Arrays.copyOf(annotation.defaultInts(), annotation.defaultInts().length);
                }
                if(Integer[].class.equals(type)) {
                    if(annotation.defaultInts().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map(Integer::valueOf).toArray(Integer[]::new);
                    }
                    return IntStream.range(0, annotation.defaultInts().length).mapToObj((final int i) -> annotation.defaultInts()[i]).toArray(Integer[]::new);
                }

                if(long[].class.equals(type)) {
                    if(annotation.defaultLongs().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).mapToLong(Long::parseLong).toArray();
                    }
                    return Arrays.copyOf(annotation.defaultLongs(), annotation.defaultLongs().length);
                }
                if(Long[].class.equals(type)) {
                    if(annotation.defaultLongs().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map(Long::valueOf).toArray(Long[]::new);
                    }
                    return IntStream.range(0, annotation.defaultLongs().length).mapToObj((final int i) -> annotation.defaultLongs()[i]).toArray(Long[]::new);
                }

                if(short[].class.equals(type)) {
                    if(annotation.defaultShorts().length == 0 && annotation.defaultValues().length != 0) {
                        final short[] value = new short[annotation.defaultValues().length];
                        for(int i = 0; i < value.length; i++) {
                            value[i] = Short.parseShort(annotation.defaultValues()[i]);
                        }
                        return value;
                    }
                    return Arrays.copyOf(annotation.defaultShorts(), annotation.defaultShorts().length);
                }
                if(Short[].class.equals(type)) {
                    if(annotation.defaultShorts().length == 0 && annotation.defaultValues().length != 0) {
                        return Arrays.stream(annotation.defaultValues()).map(Short::valueOf).toArray(Short[]::new);
                    }
                    return IntStream.range(0, annotation.defaultShorts().length).mapToObj((final int i) -> annotation.defaultShorts()[i]).toArray(Short[]::new);
                }
            }

            if(String.class.equals(type.getComponentType())) {
                if(annotation.defaultStrings().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.copyOf(annotation.defaultValues(), annotation.defaultValues().length);
                }
                return Arrays.copyOf(annotation.defaultStrings(), annotation.defaultStrings().length);
            }

            final Object result = Array.newInstance(type.getComponentType(), annotation.defaultValues().length);
            for(int i = 0; i < annotation.defaultValues().length; i++) {
                Array.set(result, i, instantiate(type.getComponentType(), annotation.defaultValues()[i], names));
            }
            return result;
        }

        if(Collection.class.isAssignableFrom(type)) {
            final Class<?> genericType = Default.CLASS.equals(annotation.type()) ? null : annotation.type();
            if(genericType == null) {
                throw new ClinicAnnotationException("The @Option " + String.join("/", names)
                    + " is a Collection type, but doesn't set the \"type\" @Option parameter. Because of Java's runtime generic type erasure, the \"type\" parameter is needed to resolve the default value for this @Option. Please set it to the type that the Collection is of.");
            }

            final Class<? extends Collection> collectionType = (Class<? extends Collection>)type;
            final Collector<Object, ?, Collection<Object>> collector = Collectors.toCollection(() -> {
                try {
                    if(collectionType.isInterface()) {
                        return Default.implementation(collectionType).getConstructor().newInstance();
                    }
                    return collectionType.getConstructor().newInstance();
                } catch(final InstantiationException e) {
                    throw new ClinicAnnotationException(
                        type.getSimpleName() + " is an abstract class or interface, but the @Option " + String.join("/", names)
                            + " uses that type! Please use a concrete type.",
                        e);
                } catch(final IllegalAccessException | NoSuchMethodException | SecurityException e) {
                    throw new ClinicAnnotationException("The no-arg constructor for " + type.getSimpleName() + " is not visible to clinic, but the @Option "
                        + String.join("/", names) + " uses that type! Please make the constuctor exists and is visible or use a different type.", e);
                } catch(final InvocationTargetException e) {
                    final Throwable cause = e.getCause();
                    if(cause instanceof RuntimeException) {
                        throw (RuntimeException)cause;
                    }
                    throw new RuntimeException(null, cause);
                }
            });

            if(Boolean.class.equals(genericType)) {
                if(annotation.defaultBooleans().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map(Boolean::valueOf).collect(collector);
                }
                return IntStream.range(0, annotation.defaultBooleans().length).mapToObj((final int i) -> annotation.defaultBooleans()[i]).collect(collector);
            }
            if(Byte.class.equals(genericType)) {
                if(annotation.defaultBytes().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map(Byte::valueOf).collect(collector);
                }
                return IntStream.range(0, annotation.defaultBytes().length).mapToObj((final int i) -> annotation.defaultBytes()[i]).collect(collector);
            }
            if(Character.class.equals(genericType)) {
                if(annotation.defaultChars().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map((final String value) -> {
                        if(value.length() != 1) {
                            throw new ClinicAnnotationException("char or Character defaultValues must be of length 1!");
                        }
                        return value.charAt(0);
                    }).collect(collector);
                }
                return IntStream.range(0, annotation.defaultChars().length).mapToObj((final int i) -> annotation.defaultChars()[i]).collect(collector);
            }
            if(Double.class.equals(genericType)) {
                if(annotation.defaultDoubles().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map(Double::valueOf).collect(collector);
                }
                return IntStream.range(0, annotation.defaultDoubles().length).mapToObj((final int i) -> annotation.defaultDoubles()[i]).collect(collector);
            }
            if(Float.class.equals(genericType)) {
                if(annotation.defaultFloats().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map(Float::valueOf).collect(collector);
                }
                return IntStream.range(0, annotation.defaultFloats().length).mapToObj((final int i) -> annotation.defaultFloats()[i]).collect(collector);
            }
            if(Integer.class.equals(genericType)) {
                if(annotation.defaultInts().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map(Integer::valueOf).collect(collector);
                }
                return IntStream.range(0, annotation.defaultInts().length).mapToObj((final int i) -> annotation.defaultInts()[i]).collect(collector);
            }
            if(Long.class.equals(genericType)) {
                if(annotation.defaultLongs().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map(Long::valueOf).collect(collector);
                }
                return IntStream.range(0, annotation.defaultLongs().length).mapToObj((final int i) -> annotation.defaultLongs()[i]).collect(collector);
            }
            if(Short.class.equals(genericType)) {
                if(annotation.defaultShorts().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).map(Short::valueOf).collect(collector);
                }
                return IntStream.range(0, annotation.defaultShorts().length).mapToObj((final int i) -> annotation.defaultShorts()[i]).collect(collector);
            }
            if(String.class.equals(genericType)) {
                if(annotation.defaultStrings().length == 0 && annotation.defaultValues().length != 0) {
                    return Arrays.stream(annotation.defaultValues()).collect(collector);
                }
                return Arrays.stream(annotation.defaultStrings()).collect(collector);
            }

            return Arrays.stream(annotation.defaultValues()).map((final String value) -> instantiate(genericType, value, names))
                .collect(collector);
        }

        return Default.STRING.equals(annotation.defaultValue()) ? null : instantiate(type, annotation.defaultValue(), names);
    }

    private static String[] getNames(final Parameter parameter) {
        if(!parameter.isNamePresent()) {
            throw new ClinicAnnotationException(
                "Couldn't get a default name for an @Option annotation. Either compile your application with the -parameter option or add names to the @Option annotation.");
        }
        final String name = (parameter.getName().length() > 1 ? "--" : "-") + Common.toHypenCase(parameter.getName());
        return new String[] {name};
    }

    private static <T> T instantiate(final Class<T> type, final String value, final String[] names) {
        if(value == null) {
            return null;
        }
        try {
            final Constructor<T> constructor = type.getConstructor(String.class);
            return constructor.newInstance(value);
        } catch(final NoSuchMethodException e) {
            throw new ClinicAnnotationException("Couldn't find a String-only constructor for " + type.getSimpleName() + ", but the @Option"
                + String.join("/", names) + " uses that type! Please add a String-only constructor or use a different type.", e);
        } catch(SecurityException | IllegalAccessException e) {
            throw new ClinicAnnotationException("The String-only constructor for " + type.getSimpleName() + " is not visible to clinic, but the @Option "
                + String.join("/", names) + " uses that type! Please make the constructor visible or use a different type.", e);
        } catch(final InstantiationException e) {
            throw new ClinicAnnotationException(
                type.getSimpleName() + " is an abstract class or interface, but the @Option " + String.join("/", names)
                    + " uses that type! Please use a concrete type.",
                e);
        } catch(final IllegalArgumentException e) {
            throw new ClinicException("Something unexpected went wrong trying to instantiate a " + type.getSimpleName()
                + " with its defaultValue String for @Option " + String.join("/", names) + "! Report this to the clinic team.", e);
        } catch(final InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if(cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw new ClinicException(cause);
        }
    }

    private final Object defaultValue;
    private final boolean flag;
    private final Class<?> genericType;
    private final String help;
    private final boolean multiArgument;
    private final String[] names;
    private final Parameter parameter;
    private final boolean required;
    private final boolean showDefault;

    private Option(final Parameter parameter, final String[] names, final boolean required, final Object defaultValue, final String help,
        final boolean showDefault, final boolean flag, final boolean multiArgument, final Class<?> genericType) {
        this.parameter = parameter;
        this.names = names;
        this.required = required;
        this.defaultValue = defaultValue;
        this.help = help;
        this.showDefault = showDefault;
        this.flag = flag;
        this.multiArgument = multiArgument;
        this.genericType = genericType;
    }

    /**
     * @return the defaultValue for this option
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return the help message for this option
     */
    public String getHelp() {
        return help;
    }

    /**
     * @return the valid names for this option
     */
    public String[] getNames() {
        return names;
    }

    /**
     * @return the parameter this option feeds
     */
    public Parameter getParameter() {
        return parameter;
    }

    /**
     * @return the value of this flag option
     */
    public Object getValue() {
        if(!flag) {
            throw new IllegalArgumentException("Must provide getValue parameters for non-flag @Options!");
        }
        return Boolean.TRUE;
    }

    private Object getValue(final Class<?> type, final String value) {
        if(value == null) {
            return null;
        }

        if(type.isPrimitive() || WRAPPER_CLASSES.contains(type)) {
            if(boolean.class.equals(type)) {
                return Boolean.parseBoolean(value);
            }
            if(Boolean.class.equals(type)) {
                return Boolean.valueOf(value);
            }
            if(byte.class.equals(type)) {
                return Byte.parseByte(value);
            }
            if(Byte.class.equals(type)) {
                return Byte.valueOf(value);
            }
            if(char.class.equals(type)) {
                if(value.length() != 1) {
                    throw new IllegalArgumentException("char or Character arguments must be of length 1!");
                }
                return value.charAt(0);
            }
            if(Character.class.equals(type)) {
                if(value.length() != 1) {
                    throw new IllegalArgumentException("char or Character arguments must be of length 1!");
                }
                return Character.valueOf(value.charAt(0));
            }
            if(double.class.equals(type)) {
                return Double.parseDouble(value);
            }
            if(Double.class.equals(type)) {
                return Double.valueOf(value);
            }
            if(float.class.equals(type)) {
                return Float.parseFloat(value);
            }
            if(Float.class.equals(type)) {
                return Float.valueOf(value);
            }
            if(int.class.equals(type)) {
                return Integer.parseInt(value);
            }
            if(Integer.class.equals(type)) {
                return Integer.valueOf(value);
            }
            if(long.class.equals(type)) {
                return Long.parseLong(value);
            }
            if(Long.class.equals(type)) {
                return Long.valueOf(value);
            }
            if(short.class.equals(type)) {
                return Short.parseShort(value);
            }
            if(Short.class.equals(type)) {
                return Short.valueOf(value);
            }

            throw new ClinicException(
                "Tried to get value for a primitive type or wrapper class, but " + type.getSimpleName() + " didn't match any! Report this to the clinic team.");
        }

        if(String.class.equals(type)) {
            return value;
        }

        return instantiate(type, value, names);
    }

    /**
     * @param values
     *        the command line arguments this option was given
     * @return the option's value
     */
    public Object getValue(final List<String> values) {
        return getValue(values.toArray(new String[values.size()]));
    }

    /**
     * @param values
     *        the command line argument(s) this option was given
     * @return the option's value
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object getValue(final String... values) {
        if(flag) {
            throw new IllegalArgumentException("Must use parameter-less getValue for flag @Options!");
        }
        if(values.length == 0) {
            throw new IllegalArgumentException("No parameters provided to getValue!");
        }
        if(!multiArgument && values.length > 1) {
            throw new IllegalArgumentException("Too many parameters provided to getValue for singe-valued @Option!");
        }

        final Class<?> type = parameter.getType();
        if(String.class.equals(type)) {
            return values[0];
        }

        if(type.isArray()) {
            if(String.class.equals(type.getComponentType())) {
                return Arrays.copyOf(values, values.length);
            }

            final Object result = Array.newInstance(type.getComponentType(), values.length);
            for(int i = 0; i < values.length; i++) {
                final Object value = values[i] == null ? null : getValue(type.getComponentType(), values[i]);
                if(value != null || !type.getComponentType().isPrimitive()) {
                    Array.set(result, i, value);
                }
            }
            return result;
        }

        if(Collection.class.isAssignableFrom(type)) {
            if(genericType == null) {
                throw new ClinicAnnotationException("The @Option " + String.join("/", names)
                    + " is a Collection type, but doesn't set the \"type\" @Option parameter. Because of Java's runtime generic type erasure, the \"type\" parameter is needed to resolve the value for this @Option. Please set it to the type that the Collection is of.");
            }

            final Class<? extends Collection> collectionType = (Class<? extends Collection>)type;
            final Collector<Object, ?, Collection<Object>> collector = Collectors.toCollection(() -> {
                try {
                    if(collectionType.isInterface()) {
                        return Default.implementation(collectionType).getConstructor().newInstance();
                    }
                    return collectionType.getConstructor().newInstance();
                } catch(final InstantiationException e) {
                    throw new ClinicAnnotationException(
                        type.getSimpleName() + " is an abstract class or interface, but the @Option " + String.join("/", names)
                            + " uses that type! Please use a concrete type.",
                        e);
                } catch(final IllegalAccessException | NoSuchMethodException | SecurityException e) {
                    throw new ClinicAnnotationException("The no-arg constructor for " + type.getSimpleName() + " is not visible to clinic, but the @Option "
                        + String.join("/", names) + " uses that type! Please make the constuctor exists and is visible or use a different type.", e);
                } catch(final InvocationTargetException e) {
                    final Throwable cause = e.getCause();
                    if(cause instanceof RuntimeException) {
                        throw (RuntimeException)cause;
                    }
                    throw new RuntimeException(null, cause);
                }
            });

            if(String.class.equals(genericType)) {
                return Arrays.stream(values).collect(collector);
            }

            return Arrays.stream(values).map((final String value) -> getValue(genericType, value)).collect(collector);
        }

        return getValue(type, values[0]);
    }

    /**
     * @return whether this option is a argument-less flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @return whether this option accepts multiple arguments
     */
    public boolean isMultiArgument() {
        return multiArgument;
    }

    /**
     * @return whether this option is required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @return whether to show the default value when printing a gelp message for this option
     */
    public boolean isShowDefault() {
        return showDefault;
    }
}
