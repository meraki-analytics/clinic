package com.merakianalytics.clinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestOptionGetValue {
    private static void test(final Class<?> testMethodContainer, final List<?> values) {
        final String[] args = values.stream().map((final Object object) -> object == null ? null : object.toString()).toArray(String[]::new);

        for(final Parameter parameter : testMethodContainer.getDeclaredMethods()[0].getParameters()) {
            final Option option = Option.get(null, parameter);

            if(!option.isMultiArgument()) {
                assertThrows(IllegalArgumentException.class, () -> option.getValue());
                assertThrows(IllegalArgumentException.class, () -> option.getValue(new String[2]));

                for(int i = 0; i < values.size(); i++) {
                    final Object result = option.getValue(args[i]);
                    if(result != null) {
                        assertEquals(TestCommon.coerce(parameter.getType()), result.getClass());
                    }
                    assertEquals(values.get(i), result);
                }
            } else {
                assertThrows(IllegalArgumentException.class, () -> option.getValue());

                final Object result = option.getValue(args);

                if(parameter.getType().isArray()) {
                    assertEquals(parameter.getType(), result.getClass());
                    assertEquals(values.size(), Array.getLength(result));
                    for(int i = 0; i < values.size(); i++) {
                        if(values.get(i) != null) {
                            assertEquals(values.get(i).getClass(), Array.get(result, i).getClass());
                            assertEquals(values.get(i), Array.get(result, i));
                        } else if(parameter.getType().getComponentType().isPrimitive()) {
                            assertEquals(Default.value(parameter.getType().getComponentType()), Array.get(result, i));
                        } else {
                            assertEquals(values.get(i), Array.get(result, i));
                        }
                    }
                } else {
                    final Collection<?> collection = (Collection<?>)result;
                    assertEquals(TestCommon.coerce(parameter.getType()), collection.getClass());
                    assertEquals(values.size(), collection.size());

                    if(List.class.isAssignableFrom(collection.getClass())) {
                        final List<?> list = (List<?>)collection;
                        for(int i = 0; i < values.size(); i++) {
                            if(values.get(i) != null) {
                                assertEquals(values.get(i).getClass(), list.get(i).getClass());
                            }
                            assertEquals(values.get(i), list.get(i));
                        }
                    } else {
                        for(final Object item : collection) {
                            if(values.get(0) != null) {
                                assertEquals(values.get(0).getClass(), item.getClass());
                            }
                        }
                        for(int i = 0; i < values.size(); i++) {
                            assertTrue(collection.contains(values.get(i)));
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testBooleans() {
        final List<Boolean> values = Arrays.asList(new Boolean[] {
            null,
            true,
            false
        });
        test(Options.Booleans.class, values);
    }

    @Test
    public void testBytes() {
        final List<Byte> values = Arrays.asList(new Byte[] {
            null,
            1,
            2,
            3,
            4
        });
        test(Options.Bytes.class, values);
    }

    @Test
    public void testChars() {
        final List<Character> values = Arrays.asList(new Character[] {
            null,
            'A',
            'B',
            'C',
            'D'
        });
        test(Options.Chars.class, values);
    }

    @Test
    public void testDoubles() {
        final List<Double> values = Arrays.asList(new Double[] {
            null,
            1.0,
            2.0,
            3.0,
            4.0
        });
        test(Options.Doubles.class, values);
    }

    @Test
    public void testFlag() {
        for(final Parameter parameter : Options.Flag.Off.class.getDeclaredMethods()[0].getParameters()) {
            final Option option = Option.get(null, parameter);
            assertThrows(IllegalArgumentException.class, () -> option.getValue());
        }

        for(final Parameter parameter : Options.Flag.On.class.getDeclaredMethods()[0].getParameters()) {
            final Option option = Option.get(null, parameter);
            final Object result = option.getValue();
            assertThrows(IllegalArgumentException.class, () -> option.getValue((String)null));
            assertThrows(IllegalArgumentException.class, () -> option.getValue(new String[2]));
            assertEquals(Boolean.class, result.getClass());
            assertEquals(Boolean.TRUE, result);
        }
    }

    @Test
    public void testFloats() {
        final List<Float> values = Arrays.asList(new Float[] {
            null,
            1.0f,
            2.0f,
            3.0f,
            4.0f
        });
        test(Options.Floats.class, values);
    }

    @Test
    public void testInts() {
        final List<Integer> values = Arrays.asList(new Integer[] {
            null,
            1,
            2,
            3,
            4
        });
        test(Options.Integers.class, values);
    }

    @Test
    public void testLongs() {
        final List<Long> values = Arrays.asList(new Long[] {
            null,
            1L,
            2L,
            3L,
            4L
        });
        test(Options.Longs.class, values);
    }

    @Test
    public void testShorts() {
        final List<Short> values = Arrays.asList(new Short[] {
            null,
            1,
            2,
            3,
            4
        });
        test(Options.Shorts.class, values);
    }

    @Test
    public void testStrings() {
        final List<String> values = Arrays.asList(new String[] {
            null,
            "A",
            "B",
            "C",
            "D"
        });
        test(Options.Strings.class, values);
    }

    @Test
    public void testValidTypes() {
        final List<SomeType> values = Arrays.asList(new SomeType[] {
            null,
            new SomeType("A"),
            new SomeType("B"),
            new SomeType("C"),
            new SomeType("D")
        });
        test(Options.ValidTypes.class, values);
    }
}
