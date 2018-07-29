package com.merakianalytics.clinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.merakianalytics.clinic.annotations.ExpectedResultType;
import com.merakianalytics.clinic.exceptions.ClinicGetHelpException;
import com.merakianalytics.clinic.exceptions.ClinicParseException;

public class TestCommandExecute {
    private static void test(final Class<?> testMethodContainer, final String[] values) {
        final Set<String> set = new HashSet<>(Arrays.asList(values));

        for(final Method method : testMethodContainer.getDeclaredMethods()) {
            final Command command = Command.get(null, method);

            if(!command.getOptions()[0].isMultiArgument()) {
                for(final String value : values) {
                    assertEquals(value, Objects.toString(command.execute("--input", value)));
                }
            } else {
                final String[] input = new String[values.length + 1];
                input[0] = "--input";
                for(int i = 0; i < values.length; i++) {
                    input[i + 1] = values[i];
                }

                final Object result = command.execute(input);

                if(method.getReturnType().isArray()) {
                    assertEquals(values.length, Array.getLength(result));
                    for(int i = 0; i < values.length; i++) {
                        assertEquals(values[i], Objects.toString(Array.get(result, i)));
                    }
                }

                if(Collection.class.isAssignableFrom(method.getReturnType())) {
                    final Collection<?> collection = (Collection<?>)result;
                    final Class<?> type = method.getAnnotation(ExpectedResultType.class).value();
                    assertEquals(values.length, collection.size());

                    if(List.class.isAssignableFrom(method.getReturnType())) {
                        final List<?> list = (List<?>)collection;
                        for(int i = 0; i < values.length; i++) {
                            assertEquals(type, list.get(i).getClass());
                            assertEquals(values[i], Objects.toString(list.get(i)));
                        }
                    } else {
                        for(final Object item : collection) {
                            assertEquals(type, item.getClass());
                            assertTrue(set.contains(Objects.toString(item)));
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testBadParse() {
        for(final Method method : Commands.Parse.class.getDeclaredMethods()) {
            final Command command = Command.get(null, method);

            assertThrows(ClinicGetHelpException.class, () -> {
                command.execute("--help");
            });

            assertThrows(ClinicParseException.class, () -> {
                command.execute("--bad-option");
            });

            assertThrows(ClinicParseException.class, () -> {
                command.execute("not-an-option");
            });

            // Missing required --one
            assertThrows(ClinicParseException.class, () -> {
                command.execute("--two");
            });

            // Requires value
            assertThrows(ClinicParseException.class, () -> {
                command.execute("--one");
            });

            // Can't convert to proper type
            assertThrows(ClinicParseException.class, () -> {
                command.execute("--one", "hello");
            });

            // Too many arguments
            assertThrows(ClinicParseException.class, () -> {
                command.execute("--one", "h", "abc");
            });

            // Giving argument to flag
            assertThrows(ClinicParseException.class, () -> {
                command.execute("--one", "h", "--two", "abc");
            });

            // Requires at least one
            assertThrows(ClinicParseException.class, () -> {
                command.execute("--one", "h", "--two", "--three");
            });
        }
    }

    @Test
    public void testBooleans() {
        final String[] values = new String[] {
            "true",
            "false"
        };

        test(Commands.Booleans.class, values);
    }

    @Test
    public void testBytes() {
        final String[] values = new String[] {
            "1",
            "2",
            "3",
            "4"
        };

        test(Commands.Bytes.class, values);
    }

    @Test
    public void testCharacters() {
        final String[] values = new String[] {
            "A",
            "B",
            "C",
            "D"
        };

        test(Commands.Characters.class, values);
    }

    @Test
    public void testDoubles() {
        final String[] values = new String[] {
            "1.0",
            "2.0",
            "3.0",
            "4.0"
        };

        test(Commands.Doubles.class, values);
    }

    @Test
    public void testFloats() {
        final String[] values = new String[] {
            "1.0",
            "2.0",
            "3.0",
            "4.0"
        };

        test(Commands.Floats.class, values);
    }

    @Test
    public void testIntegers() {
        final String[] values = new String[] {
            "1",
            "2",
            "3",
            "4"
        };

        test(Commands.Integers.class, values);
    }

    @Test
    public void testLongs() {
        final String[] values = new String[] {
            "1",
            "2",
            "3",
            "4"
        };

        test(Commands.Longs.class, values);
    }

    @Test
    public void testMutipleArguments() {
        final String[] arguments = new String[] {
            "--one",
            "'test'",
            "--two",
            "123",
            "--three",
            "42.0",
            "--four",
            "\"TEST\"",
            "--five",
            "11",
            "245.5",
            "--six",
            "52",
            "5",
            "--seven",
            "151",
            "6432",
            "--eight",
            "g",
            "9",
            "--nine",
            "111",
            "64",
            "--ten",
            "true",
            "false"
        };

        final String one = "test";
        final int two = 123;
        final Float three = 42.0f;
        final SomeType four = new SomeType("TEST");
        final double[] five = new double[] {11.0, 245.5};
        final List<Long> six = new ArrayList<>();
        six.add(52L);
        six.add(5L);
        final ArrayList<Short> seven = new ArrayList<>();
        seven.add((short)151);
        seven.add((short)6432);
        final Set<Character> eight = new HashSet<>();
        eight.add('g');
        eight.add('9');
        final HashSet<Byte> nine = new HashSet<>();
        nine.add((byte)111);
        nine.add((byte)64);
        final boolean[] ten = new boolean[] {true, false};

        final int hashCode = Commands.hash(one, two, three, four, five, six, seven, eight, nine, ten);

        for(final Method method : Commands.MultipleArguments.class.getDeclaredMethods()) {
            final Command command = Command.get(null, method);
            assertEquals(hashCode, command.execute(arguments));
        }
    }

    @Test
    public void testShorts() {
        final String[] values = new String[] {
            "1",
            "2",
            "3",
            "4"
        };

        test(Commands.Shorts.class, values);
    }

    @Test
    public void testStrings() {
        final String[] values = new String[] {
            "A",
            "B",
            "C",
            "D"
        };

        test(Commands.Strings.class, values);
    }

    @Test
    public void testValidTypes() {
        final String[] values = new String[] {
            "A",
            "B",
            "C",
            "D"
        };

        test(Commands.ValidTypes.class, values);
    }
}
