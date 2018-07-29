package com.merakianalytics.clinic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Default values for clinic
 */
public abstract class Default {
    public static final boolean BOOLEAN = false;
    public static final byte BYTE = (byte)0;
    public static final char CHAR = '\u0000';
    public static final Class<?> CLASS = void.class; // This is used because Annotations can't use null as default values
    public static final double DOUBLE = 0.0d;
    public static final float FLOAT = 0.0f;
    public static final int INT = 0;
    public static final long LONG = 0L;
    public static final short SHORT = (short)0;
    public static final String STRING = "null"; // This is used because Annotations can't use null as default values

    /**
     * Gets the default @Command annotation for an unannotated method
     *
     * @param method
     *        the unannotated method
     * @return the default @Command annotation
     */
    public static com.merakianalytics.clinic.annotations.Command command(final Method method) {
        return new com.merakianalytics.clinic.annotations.Command() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return com.merakianalytics.clinic.annotations.Command.class;
            }

            @Override
            public boolean automatic() {
                return true;
            }

            @Override
            public boolean defaultCommand() {
                return false;
            }

            @Override
            public String help() {
                return STRING;
            }

            @Override
            public String name() {
                return STRING;
            }
        };
    }

    /**
     * Gets the default implementation of a Collection interface type
     *
     * @param type
     *        the interface type
     * @return the default implementation
     */
    @SuppressWarnings("rawtypes")
    public static Class<? extends Collection> implementation(final Class<? extends Collection> type) {
        if(List.class.equals(type)) {
            return ArrayList.class;
        }
        if(Set.class.equals(type)) {
            return HashSet.class;
        }
        if(SortedSet.class.equals(type)) {
            return TreeSet.class;
        }
        if(NavigableSet.class.equals(type)) {
            return TreeSet.class;
        }
        if(Queue.class.equals(type)) {
            return ArrayDeque.class;
        }
        if(BlockingQueue.class.equals(type)) {
            return ArrayBlockingQueue.class;
        }
        if(TransferQueue.class.equals(type)) {
            return LinkedTransferQueue.class;
        }
        if(Deque.class.equals(type)) {
            return ArrayDeque.class;
        }
        if(BlockingQueue.class.equals(type)) {
            return LinkedBlockingDeque.class;
        }

        throw new IllegalArgumentException("Unsupported Collection interface " + type.getSimpleName() + "!");
    }

    /**
     * Gets the default @Option annotation for an unannotated parameter
     *
     * @param parameter
     *        the unannotated parameter
     * @return the default @Option annotation
     */
    public static com.merakianalytics.clinic.annotations.Option option(final Parameter parameter) {
        return new com.merakianalytics.clinic.annotations.Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return com.merakianalytics.clinic.annotations.Option.class;
            }

            @Override
            public boolean defaultBoolean() {
                return BOOLEAN;
            }

            @Override
            public boolean[] defaultBooleans() {
                return new boolean[0];
            }

            @Override
            public byte defaultByte() {
                return BYTE;
            }

            @Override
            public byte[] defaultBytes() {
                return new byte[0];
            }

            @Override
            public char defaultChar() {
                return CHAR;
            }

            @Override
            public char[] defaultChars() {
                return new char[0];
            }

            @Override
            public double defaultDouble() {
                return DOUBLE;
            }

            @Override
            public double[] defaultDoubles() {
                return new double[0];
            }

            @Override
            public float defaultFloat() {
                return FLOAT;
            }

            @Override
            public float[] defaultFloats() {
                return new float[0];
            }

            @Override
            public int defaultInt() {
                return INT;
            }

            @Override
            public int[] defaultInts() {
                return new int[0];
            }

            @Override
            public long defaultLong() {
                return LONG;
            }

            @Override
            public long[] defaultLongs() {
                return new long[0];
            }

            @Override
            public short defaultShort() {
                return SHORT;
            }

            @Override
            public short[] defaultShorts() {
                return new short[0];
            }

            @Override
            public String defaultString() {
                return STRING;
            }

            @Override
            public String[] defaultStrings() {
                return new String[0];
            }

            @Override
            public String defaultValue() {
                return STRING;
            }

            @Override
            public String[] defaultValues() {
                return new String[0];
            }

            @Override
            public boolean flag() {
                return false;
            }

            @Override
            public String help() {
                return STRING;
            }

            @Override
            public String[] names() {
                return new String[0];
            }

            @Override
            public boolean required() {
                return false;
            }

            @Override
            public boolean showDefault() {
                return true;
            }

            @Override
            public Class<?> type() {
                return CLASS;
            }
        };
    }

    /**
     * Gets the default value for a type
     *
     * @param type
     *        the type to get the default value for
     * @return the default value for the type
     */
    public static Object value(final Class<?> type) {
        if(byte.class.equals(type)) {
            return BYTE;
        } else if(char.class.equals(type)) {
            return CHAR;
        } else if(short.class.equals(type)) {
            return SHORT;
        } else if(int.class.equals(type)) {
            return INT;
        } else if(long.class.equals(type)) {
            return LONG;
        } else if(float.class.equals(type)) {
            return FLOAT;
        } else if(double.class.equals(type)) {
            return DOUBLE;
        } else if(boolean.class.equals(type)) {
            return BOOLEAN;
        } else {
            return null;
        }
    }

    private Default() {}
}