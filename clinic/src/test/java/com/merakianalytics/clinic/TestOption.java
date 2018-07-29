package com.merakianalytics.clinic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Parameter;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.merakianalytics.clinic.exceptions.ClinicAnnotationException;

public class TestOption {
    private static void assertParametersExtractedCorrectly(final Parameter parameter, final Option option) {
        final com.merakianalytics.clinic.annotations.ExpectedOption expected =
            parameter.getAnnotation(com.merakianalytics.clinic.annotations.ExpectedOption.class);

        assertEquals(parameter, option.getParameter());
        assertArrayEquals(expected.names(), option.getNames());
        assertEquals(expected.required(), option.isRequired());
        final Object defaultValue = option.getDefaultValue();
        if(defaultValue != null) {
            assertEquals(TestCommon.coerce(parameter.getType()), defaultValue.getClass());
            if(parameter.getType().isArray()) {
                assertEquals(parameter.getType().getComponentType(), defaultValue.getClass().getComponentType());
            }
            if(Collection.class.isAssignableFrom(parameter.getType())) {
                final Collection<?> collection = (Collection<?>)defaultValue;
                for(final Object item : collection) {
                    assertEquals(expected.type(), item.getClass());
                }
            }
        }
        assertEquals(expected.defaultValueHashCode(), TestCommon.hashCode(defaultValue));
        assertEquals(TestCommon.NULL_STRING.equals(expected.help()) ? null : expected.help(), option.getHelp());
        assertEquals(expected.showDefault(), option.isShowDefault());
        assertEquals(expected.flag(), option.isFlag());
        assertEquals(expected.multiArgument(), option.isMultiArgument());
    }

    @Test
    public void testBadType() {
        for(final Parameter parameter : Options.BadType.class.getDeclaredMethods()[0].getParameters()) {
            assertThrows(ClinicAnnotationException.class, () -> {
                Option.get(parameter);
            });
        }

        for(final Parameter parameter : Options.BadType.NoDefault.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testBooleans() {
        for(final Parameter parameter : Options.Booleans.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testBytes() {
        for(final Parameter parameter : Options.Bytes.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testChars() {
        for(final Parameter parameter : Options.Chars.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testDoubles() {
        for(final Parameter parameter : Options.Doubles.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testFlag() {
        for(final Parameter parameter : Options.Flag.NonBoolean.class.getDeclaredMethods()[0].getParameters()) {
            assertThrows(ClinicAnnotationException.class, () -> {
                Option.get(parameter);
            });
        }

        for(final Parameter parameter : Options.Flag.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testFloats() {
        for(final Parameter parameter : Options.Floats.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testHelp() {
        for(final Parameter parameter : Options.Help.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testIntegers() {
        for(final Parameter parameter : Options.Integers.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testLongs() {
        for(final Parameter parameter : Options.Longs.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testMultiArgument() {
        for(final Parameter parameter : Options.MultiArgument.Interface.class.getDeclaredMethods()[0].getParameters()) {
            assertThrows(ClinicAnnotationException.class, () -> {
                Option.get(parameter);
            });
        }

        for(final Parameter parameter : Options.MultiArgument.AbstractClass.class.getDeclaredMethods()[0].getParameters()) {
            assertThrows(ClinicAnnotationException.class, () -> {
                Option.get(parameter);
            });
        }

        for(final Parameter parameter : Options.MultiArgument.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testNames() {
        for(final Parameter parameter : Options.Names.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testRequired() {
        for(final Parameter parameter : Options.Required.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testShorts() {
        for(final Parameter parameter : Options.Shorts.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testShowDefault() {
        for(final Parameter parameter : Options.ShowDefault.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testStrings() {
        for(final Parameter parameter : Options.Strings.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }

    @Test
    public void testValidType() {
        for(final Parameter parameter : Options.ValidTypes.class.getDeclaredMethods()[0].getParameters()) {
            assertParametersExtractedCorrectly(parameter, Option.get(parameter));
        }
    }
}
