package com.merakianalytics.clinic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public abstract class TestCommon {
    public abstract class HashCodes {
        public static final int ARRAY_LIST = 1;
        public static final int BOOLEAN_ARRAY = 1;
        public static final int BOOLEAN_ARRAY_LIST_T_T_F_F = 38818817;
        public static final int BOOLEAN_ARRAY_T_T_F_F = 38818817;
        public static final int BOOLEAN_HASH_SET_T_T_F_F = 2468;
        public static final int BYTE_0 = 0;
        public static final int BYTE_1 = 1;
        public static final int BYTE_ARRAY = 1;
        public static final int BYTE_ARRAY_1_2_3_4 = 955331;
        public static final int BYTE_ARRAY_LIST_1_2_3_4 = 955331;
        public static final int BYTE_HASH_SET_1_2_3_4 = 10;
        public static final int CHAR_A = 65;
        public static final int CHAR_ARRAY = 1;
        public static final int CHAR_ARRAY_A_B_C_D = 2925507;
        public static final int CHAR_ARRAY_LIST_A_B_C_D = 2925507;
        public static final int CHAR_HASH_SET_A_B_C_D = 266;
        public static final int CHAR_NULL = 0;
        public static final int DOUBLE_0 = 0;
        public static final int DOUBLE_1 = 1072693248;
        public static final int DOUBLE_ARRAY = 1;
        public static final int DOUBLE_ARRAY_1_2_3_4 = -1155131519;
        public static final int DOUBLE_ARRAY_LIST_1_2_3_4 = -1155131519;
        public static final int DOUBLE_HASH_SET_1_2_3_4 = 524288;
        public static final int FALSE = 1237;
        public static final int FLOAT_0 = 0;
        public static final int FLOAT_1 = 1065353216;
        public static final int FLOAT_ARRAY = 1;
        public static final int FLOAT_ARRAY_1_2_3_4 = -657582207;
        public static final int FLOAT_ARRAY_LIST_1_2_3_4 = -657582207;
        public static final int FLOAT_HASH_SET_1_2_3_4 = 4194304;
        public static final int HASH_SET = 0;
        public static final int INT_0 = 0;
        public static final int INT_1 = 1;
        public static final int INT_ARRAY = 1;
        public static final int INT_ARRAY_1_2_3_4 = 955331;
        public static final int INT_ARRAY_LIST_1_2_3_4 = 955331;
        public static final int INT_HASH_SET_1_2_3_4 = 10;
        public static final int LONG_0 = 0;
        public static final int LONG_1 = 1;
        public static final int LONG_ARRAY = 1;
        public static final int LONG_ARRAY_1_2_3_4 = 955331;
        public static final int LONG_ARRAY_LIST_1_2_3_4 = 955331;
        public static final int LONG_HASH_SET_1_2_3_4 = 10;
        public static final int NULL = 0;
        public static final int SHORT_0 = 0;
        public static final int SHORT_1 = 1;
        public static final int SHORT_ARRAY = 1;
        public static final int SHORT_ARRAY_1_2_3_4 = 955331;
        public static final int SHORT_ARRAY_LIST_1_2_3_4 = 955331;
        public static final int SHORT_HASH_SET_1_2_3_4 = 10;
        public static final int SOME_TYPE_A = 96;
        public static final int SOME_TYPE_ARRAY = 1;
        public static final int SOME_TYPE_ARRAY_A_B_C_D = 3879811;
        public static final int SOME_TYPE_ARRAY_LIST_A_B_C_D = 3879811;
        public static final int SOME_TYPE_HASH_SET_A_B_C_D = 390;
        public static final int STRING_A = 65;
        public static final int STRING_ARRAY = 1;
        public static final int STRING_ARRAY_A_B_C_D = 2925507;
        public static final int STRING_ARRAY_LIST_A_B_C_D = 2925507;
        public static final int STRING_HASH_SET_A_B_C_D = 266;
        public static final int TRUE = 1231;
    }

    public static final String NULL_STRING = "null";

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Class<?> coerce(final Class<?> type) {
        if(type.isPrimitive()) {
            if(boolean.class.equals(type)) {
                return Boolean.class;
            }
            if(byte.class.equals(type)) {
                return Byte.class;
            }
            if(char.class.equals(type)) {
                return Character.class;
            }
            if(double.class.equals(type)) {
                return Double.class;
            }
            if(float.class.equals(type)) {
                return Float.class;
            }
            if(int.class.equals(type)) {
                return Integer.class;
            }
            if(long.class.equals(type)) {
                return Long.class;
            }
            if(short.class.equals(type)) {
                return Short.class;
            }
        }
        if(Collection.class.isAssignableFrom(type) && type.isInterface()) {
            return Default.implementation((Class<? extends Collection>)type);
        }
        return type;
    }

    public static int hashCode(final Object object) {
        if(object == null) {
            return Objects.hashCode(null);
        }

        if(!object.getClass().isArray()) {
            return object.hashCode();
        }

        final Class<?> component = object.getClass().getComponentType();
        if(boolean.class.equals(component)) {
            return Arrays.hashCode((boolean[])object);
        }
        if(byte.class.equals(component)) {
            return Arrays.hashCode((byte[])object);
        }
        if(char.class.equals(component)) {
            return Arrays.hashCode((char[])object);
        }
        if(double.class.equals(component)) {
            return Arrays.hashCode((double[])object);
        }
        if(float.class.equals(component)) {
            return Arrays.hashCode((float[])object);
        }
        if(int.class.equals(component)) {
            return Arrays.hashCode((int[])object);
        }
        if(long.class.equals(component)) {
            return Arrays.hashCode((long[])object);
        }
        if(short.class.equals(component)) {
            return Arrays.hashCode((short[])object);
        }
        if(component.isArray()) {
            return Arrays.deepHashCode((Object[])object);
        }
        return Arrays.hashCode((Object[])object);
    }
}
