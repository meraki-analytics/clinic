package com.merakianalytics.clinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.merakianalytics.clinic.exceptions.ClinicAnnotationException;

class TestCommand {
    private static void assertParametersExtractedCorrectly(final Method method, final Command command) {
        final com.merakianalytics.clinic.annotations.ExpectedCommand expected =
            method.getAnnotation(com.merakianalytics.clinic.annotations.ExpectedCommand.class);

        assertEquals(method, command.getMethod());
        assertEquals(TestCommon.NULL_STRING.equals(expected.name()) ? null : expected.name(), command.getName());
        assertEquals(TestCommon.NULL_STRING.equals(expected.help()) ? null : expected.help(), command.getHelp());
        assertEquals(expected.defaultCommand(), command.isDefaultCommand());
    }

    @Test
    public void testGetBasic() {
        for(final Method method : Commands.Basic.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetFinal() {
        for(final Method method : Commands.Final.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetNative() {
        for(final Method method : Commands.Native.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetNonStatic() {
        for(final Method method : Commands.NonStatic.class.getDeclaredMethods()) {
            assertThrows(ClinicAnnotationException.class, () -> {
                Command.get(null, method);
            });
        }
    }

    @Test
    public void testGetStrictFP() {
        for(final Method method : Commands.StrictFP.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetSynchronized() {
        for(final Method method : Commands.Synchronized.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetWithDefaultCommand() {
        for(final Method method : Commands.WithDefaultCommand.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetWithHelp() {
        for(final Method method : Commands.WithHelp.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetWithName() {
        for(final Method method : Commands.WithName.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }

    @Test
    public void testGetWithReturn() {
        for(final Method method : Commands.WithReturn.class.getDeclaredMethods()) {
            assertParametersExtractedCorrectly(method, Command.get(null, method));
        }
    }
}
