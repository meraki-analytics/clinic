package com.merakianalytics.clinic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.merakianalytics.clinic.annotations.ExpectedCommand;
import com.merakianalytics.clinic.annotations.ExpectedResultType;

public abstract class Commands {
    public static abstract class Basic {
        @ExpectedCommand(name = "package-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        static void packageCommand() {
            return;
        }

        @ExpectedCommand(name = "private-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        private static void privateCommand() {
            return;
        }

        @ExpectedCommand(name = "protected-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        protected static void protectedCommand() {
            return;
        }

        @ExpectedCommand(name = "public-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        public static void publicCommand() {
            return;
        }
    }

    public static abstract class Booleans {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static boolean[] array(final boolean... input) {
            return input;
        }

        @ExpectedResultType(Boolean.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Boolean> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Boolean.class) final ArrayList<Boolean> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Boolean[] arrayWrapper(final Boolean... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static boolean basic(final boolean input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Boolean basicWrapper(final Boolean input) {
            return input;
        }

        @ExpectedResultType(Boolean.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Boolean> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Boolean.class) final HashSet<Boolean> input) {
            return input;
        }

        @ExpectedResultType(Boolean.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Boolean> list(@com.merakianalytics.clinic.annotations.Option(type = Boolean.class) final List<Boolean> input) {
            return input;
        }

        @ExpectedResultType(Boolean.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Boolean> set(@com.merakianalytics.clinic.annotations.Option(type = Boolean.class) final Set<Boolean> input) {
            return input;
        }
    }

    public static abstract class Bytes {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static byte[] array(final byte... input) {
            return input;
        }

        @ExpectedResultType(Byte.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Byte> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Byte.class) final ArrayList<Byte> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Byte[] arrayWrapper(final Byte... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static byte basic(final byte input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Byte basicWrapper(final Byte input) {
            return input;
        }

        @ExpectedResultType(Byte.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Byte> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Byte.class) final HashSet<Byte> input) {
            return input;
        }

        @ExpectedResultType(Byte.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Byte> list(@com.merakianalytics.clinic.annotations.Option(type = Byte.class) final List<Byte> input) {
            return input;
        }

        @ExpectedResultType(Byte.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Byte> set(@com.merakianalytics.clinic.annotations.Option(type = Byte.class) final Set<Byte> input) {
            return input;
        }
    }

    public static abstract class Characters {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static char[] array(final char... input) {
            return input;
        }

        @ExpectedResultType(Character.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Character> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Character.class) final ArrayList<Character> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Character[] arrayWrapper(final Character... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static char basic(final char input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Character basicWrapper(final Character input) {
            return input;
        }

        @ExpectedResultType(Character.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Character> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Character.class) final HashSet<Character> input) {
            return input;
        }

        @ExpectedResultType(Character.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Character> list(@com.merakianalytics.clinic.annotations.Option(type = Character.class) final List<Character> input) {
            return input;
        }

        @ExpectedResultType(Character.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Character> set(@com.merakianalytics.clinic.annotations.Option(type = Character.class) final Set<Character> input) {
            return input;
        }
    }

    public static abstract class Doubles {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static double[] array(final double... input) {
            return input;
        }

        @ExpectedResultType(Double.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Double> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Double.class) final ArrayList<Double> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Double[] arrayWrapper(final Double... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static double basic(final double input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Double basicWrapper(final Double input) {
            return input;
        }

        @ExpectedResultType(Double.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Double> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Double.class) final HashSet<Double> input) {
            return input;
        }

        @ExpectedResultType(Double.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Double> list(@com.merakianalytics.clinic.annotations.Option(type = Double.class) final List<Double> input) {
            return input;
        }

        @ExpectedResultType(Double.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Double> set(@com.merakianalytics.clinic.annotations.Option(type = Double.class) final Set<Double> input) {
            return input;
        }
    }

    public static abstract class Final {
        @ExpectedCommand(name = "package-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        static final void packageCommand() {
            return;
        }

        @ExpectedCommand(name = "private-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        private static final void privateCommand() {
            return;
        }

        @ExpectedCommand(name = "protected-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        protected static final void protectedCommand() {
            return;
        }

        @ExpectedCommand(name = "public-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        public static final void publicCommand() {
            return;
        }
    }

    public static abstract class Floats {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static float[] array(final float... input) {
            return input;
        }

        @ExpectedResultType(Float.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Float> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Float.class) final ArrayList<Float> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Float[] arrayWrapper(final Float... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static float basic(final float input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Float basicWrapper(final Float input) {
            return input;
        }

        @ExpectedResultType(Float.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Float> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Float.class) final HashSet<Float> input) {
            return input;
        }

        @ExpectedResultType(Float.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Float> list(@com.merakianalytics.clinic.annotations.Option(type = Float.class) final List<Float> input) {
            return input;
        }

        @ExpectedResultType(Float.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Float> set(@com.merakianalytics.clinic.annotations.Option(type = Float.class) final Set<Float> input) {
            return input;
        }
    }

    public static abstract class Integers {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static int[] array(final int... input) {
            return input;
        }

        @ExpectedResultType(Integer.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Integer> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Integer.class) final ArrayList<Integer> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Integer[] arrayWrapper(final Integer... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static int basic(final int input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Integer basicWrapper(final Integer input) {
            return input;
        }

        @ExpectedResultType(Integer.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Integer> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Integer.class) final HashSet<Integer> input) {
            return input;
        }

        @ExpectedResultType(Integer.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Integer> list(@com.merakianalytics.clinic.annotations.Option(type = Integer.class) final List<Integer> input) {
            return input;
        }

        @ExpectedResultType(Integer.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Integer> set(@com.merakianalytics.clinic.annotations.Option(type = Integer.class) final Set<Integer> input) {
            return input;
        }
    }

    public static abstract class Longs {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static long[] array(final long... input) {
            return input;
        }

        @ExpectedResultType(Long.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Long> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Long.class) final ArrayList<Long> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Long[] arrayWrapper(final Long... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static long basic(final long input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Long basicWrapper(final Long input) {
            return input;
        }

        @ExpectedResultType(Long.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Long> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Long.class) final HashSet<Long> input) {
            return input;
        }

        @ExpectedResultType(Long.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Long> list(@com.merakianalytics.clinic.annotations.Option(type = Long.class) final List<Long> input) {
            return input;
        }

        @ExpectedResultType(Long.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Long> set(@com.merakianalytics.clinic.annotations.Option(type = Long.class) final Set<Long> input) {
            return input;
        }
    }

    public static abstract class MultipleArguments {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static int many(final String one, final int two, final Float three, final SomeType four, final double[] five,
            @com.merakianalytics.clinic.annotations.Option(type = Long.class) final List<Long> six,
            @com.merakianalytics.clinic.annotations.Option(type = Short.class) final ArrayList<Short> seven,
            @com.merakianalytics.clinic.annotations.Option(type = Character.class) final Set<Character> eight,
            @com.merakianalytics.clinic.annotations.Option(type = Byte.class) final HashSet<Byte> nine, final boolean... ten) {
            return hash(one, two, three, four, five, six, seven, eight, nine, ten);
        }
    }

    public static abstract class Native {
        @ExpectedCommand(name = "package-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        static native void packageCommand();

        @ExpectedCommand(name = "private-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        private static native void privateCommand();

        @ExpectedCommand(name = "protected-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        protected static native void protectedCommand();

        @ExpectedCommand(name = "public-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        public static native void publicCommand();
    }

    public static abstract class NonStatic {
        @com.merakianalytics.clinic.annotations.Command
        void packageCommand() {
            return;
        }

        @com.merakianalytics.clinic.annotations.Command
        private void privateCommand() {
            return;
        }

        @com.merakianalytics.clinic.annotations.Command
        protected void protectedCommand() {
            return;
        }

        @com.merakianalytics.clinic.annotations.Command
        public void publicCommand() {
            return;
        }
    }

    public static abstract class Parse {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static void basic(@com.merakianalytics.clinic.annotations.Option(required = true) final char one,
            @com.merakianalytics.clinic.annotations.Option(flag = true) final boolean two, final String[] three, final int four) {
            return;
        }
    }

    public static abstract class Shorts {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static short[] array(final short... input) {
            return input;
        }

        @ExpectedResultType(Short.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<Short> arrayList(@com.merakianalytics.clinic.annotations.Option(type = Short.class) final ArrayList<Short> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Short[] arrayWrapper(final Short... input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static short basic(final short input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Short basicWrapper(final Short input) {
            return input;
        }

        @ExpectedResultType(Short.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<Short> hashSet(@com.merakianalytics.clinic.annotations.Option(type = Short.class) final HashSet<Short> input) {
            return input;
        }

        @ExpectedResultType(Short.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<Short> list(@com.merakianalytics.clinic.annotations.Option(type = Short.class) final List<Short> input) {
            return input;
        }

        @ExpectedResultType(Short.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<Short> set(@com.merakianalytics.clinic.annotations.Option(type = Short.class) final Set<Short> input) {
            return input;
        }
    }

    public static abstract class StrictFP {
        @ExpectedCommand(name = "package-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        static strictfp void packageCommand() {
            return;
        }

        @ExpectedCommand(name = "private-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        private static strictfp void privateCommand() {
            return;
        }

        @ExpectedCommand(name = "protected-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        protected static strictfp void protectedCommand() {
            return;
        }

        @ExpectedCommand(name = "public-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        public static strictfp void publicCommand() {
            return;
        }
    }

    public static abstract class Strings {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static String[] array(final String... input) {
            return input;
        }

        @ExpectedResultType(String.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<String> arrayList(@com.merakianalytics.clinic.annotations.Option(type = String.class) final ArrayList<String> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static String basic(final String input) {
            return input;
        }

        @ExpectedResultType(String.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<String> hashSet(@com.merakianalytics.clinic.annotations.Option(type = String.class) final HashSet<String> input) {
            return input;
        }

        @ExpectedResultType(String.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<String> list(@com.merakianalytics.clinic.annotations.Option(type = String.class) final List<String> input) {
            return input;
        }

        @ExpectedResultType(String.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<String> set(@com.merakianalytics.clinic.annotations.Option(type = String.class) final Set<String> input) {
            return input;
        }
    }

    public static abstract class Synchronized {
        @ExpectedCommand(name = "package-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        static synchronized void packageCommand() {
            return;
        }

        @ExpectedCommand(name = "private-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        private static synchronized void privateCommand() {
            return;
        }

        @ExpectedCommand(name = "protected-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        protected static synchronized void protectedCommand() {
            return;
        }

        @ExpectedCommand(name = "public-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        public static synchronized void publicCommand() {
            return;
        }
    }

    public static abstract class ValidTypes {
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static SomeType[] array(final SomeType... input) {
            return input;
        }

        @ExpectedResultType(SomeType.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static ArrayList<SomeType> arrayList(@com.merakianalytics.clinic.annotations.Option(type = SomeType.class) final ArrayList<SomeType> input) {
            return input;
        }

        @com.merakianalytics.clinic.annotations.AutoCommand
        public static SomeType basic(final SomeType input) {
            return input;
        }

        @ExpectedResultType(SomeType.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static HashSet<SomeType> hashSet(@com.merakianalytics.clinic.annotations.Option(type = SomeType.class) final HashSet<SomeType> input) {
            return input;
        }

        @ExpectedResultType(SomeType.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static List<SomeType> list(@com.merakianalytics.clinic.annotations.Option(type = SomeType.class) final List<SomeType> input) {
            return input;
        }

        @ExpectedResultType(SomeType.class)
        @com.merakianalytics.clinic.annotations.AutoCommand
        public static Set<SomeType> set(@com.merakianalytics.clinic.annotations.Option(type = SomeType.class) final Set<SomeType> input) {
            return input;
        }
    }

    public static abstract class WithDefaultCommand {
        @ExpectedCommand(name = "package-command", help = TestCommon.NULL_STRING, defaultCommand = true)
        @com.merakianalytics.clinic.annotations.Command(defaultCommand = true)
        static void packageCommand() {
            return;
        }

        @ExpectedCommand(name = "private-command", help = TestCommon.NULL_STRING, defaultCommand = true)
        @com.merakianalytics.clinic.annotations.Command(defaultCommand = true)
        private static void privateCommand() {
            return;
        }

        @ExpectedCommand(name = "protected-command", help = TestCommon.NULL_STRING, defaultCommand = true)
        @com.merakianalytics.clinic.annotations.Command(defaultCommand = true)
        protected static void protectedCommand() {
            return;
        }

        @ExpectedCommand(name = "public-command", help = TestCommon.NULL_STRING, defaultCommand = true)
        @com.merakianalytics.clinic.annotations.Command(defaultCommand = true)
        public static void publicCommand() {
            return;
        }
    }

    public static abstract class WithHelp {
        @ExpectedCommand(name = "package-command", help = "8ee44584-ae43-4ac4-8b21-efc6ab31899e", defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(help = "8ee44584-ae43-4ac4-8b21-efc6ab31899e")
        static void packageCommand() {
            return;
        }

        @ExpectedCommand(name = "private-command", help = "a0f91db2-11c7-47ca-ae84-667545f64c5a", defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(help = "a0f91db2-11c7-47ca-ae84-667545f64c5a")
        private static void privateCommand() {
            return;
        }

        @ExpectedCommand(name = "protected-command", help = "2d049024-63ce-4572-bc74-8f0ab1e20ff8", defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(help = "2d049024-63ce-4572-bc74-8f0ab1e20ff8")
        protected static void protectedCommand() {
            return;
        }

        @ExpectedCommand(name = "public-command", help = "04a2cd1d-91aa-4144-bfec-7f3413cba61b", defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(help = "04a2cd1d-91aa-4144-bfec-7f3413cba61b")
        public static void publicCommand() {
            return;
        }
    }

    public static abstract class WithName {
        @ExpectedCommand(name = "8ee44584-ae43-4ac4-8b21-efc6ab31899e", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(name = "8ee44584-ae43-4ac4-8b21-efc6ab31899e")
        static void packageCommand() {
            return;
        }

        @ExpectedCommand(name = "a0f91db2-11c7-47ca-ae84-667545f64c5a", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(name = "a0f91db2-11c7-47ca-ae84-667545f64c5a")
        private static void privateCommand() {
            return;
        }

        @ExpectedCommand(name = "2d049024-63ce-4572-bc74-8f0ab1e20ff8", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(name = "2d049024-63ce-4572-bc74-8f0ab1e20ff8")
        protected static void protectedCommand() {
            return;
        }

        @ExpectedCommand(name = "04a2cd1d-91aa-4144-bfec-7f3413cba61b", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command(name = "04a2cd1d-91aa-4144-bfec-7f3413cba61b")
        public static void publicCommand() {
            return;
        }
    }

    public static abstract class WithReturn {
        @ExpectedCommand(name = "package-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        static String packageCommand() {
            return null;
        }

        @ExpectedCommand(name = "private-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        private static String privateCommand() {
            return null;
        }

        @ExpectedCommand(name = "protected-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        protected static String protectedCommand() {
            return null;
        }

        @ExpectedCommand(name = "public-command", help = TestCommon.NULL_STRING, defaultCommand = false)
        @com.merakianalytics.clinic.annotations.Command
        public static String publicCommand() {
            return null;
        }
    }

    public static int hash(final String one, final int two, final Float three, final SomeType four, final double[] five, final List<Long> six,
        final ArrayList<Short> seven, final Set<Character> eight, final HashSet<Byte> nine, final boolean... ten) {
        return Objects.hashCode(one) + Objects.hashCode(two) + Objects.hashCode(three) + Objects.hashCode(four) + Arrays.hashCode(five) + Objects.hashCode(six)
            + Objects.hashCode(seven) + Objects.hashCode(eight) + Objects.hashCode(nine) + Arrays.hashCode(ten);
    }
}
