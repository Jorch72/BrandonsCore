package com.brandon3055.brandonscore.utils;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Created by brandon3055 on 31/3/2016.
 */
public class ArrayUtils {

    public static String[] arrayToLowercase(String[] array) {
        String[] lowercaseArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            lowercaseArray[i] = array[i].toLowerCase(Locale.ENGLISH);
        }
        return lowercaseArray;
    }

    /**
     * Shift all objects in an array by the number of places specified.
     * <p/>
     * Example:
     * Object[] array = {1, 2, 3, 4, 5}
     * array = arrayShift(array, 1);
     * array now equals {5, 1, 2, 3, 4}
     * Shift can be a positive or negative number
     * <p/>
     * Shift is not restricted to any max or min value so it could for example be linked to a counter that
     * continuously increments.
     */
    public static Object[] arrayShift(Object[] input, int shift) {
        Object[] newArray = new Object[input.length];

        for (int i = 0; i < input.length; i++) {
            int newPos = (i + shift) % input.length;

            if (newPos < 0) {
                newPos += input.length;
            }

            newArray[newPos] = input[i];
        }

        return newArray;
    }

    public static <E> void forEach(E[] elements, Consumer<E> consumer) {
        for (E element : elements) {
            consumer.accept(element);
        }
    }

    public static long[] toPrimitive(final Long[] array) {
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static int[] toPrimitive(final Integer[] array) {
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static short[] toPrimitive(final Short[] array) {
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static byte[] toPrimitive(final Byte[] array) {
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static double[] toPrimitive(final Double[] array) {
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static float[] toPrimitive(final Float[] array) {
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static boolean[] toPrimitive(final Boolean[] array) {
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }


    public static long[] longListToArray(final List<Long> list) {
        final long[] result = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static int[] intListToArray(final List<Integer> list) {
        final int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static short[] shortListToArray(final List<Short> list) {
        final short[] result = new short[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static byte[] byteListToArray(final List<Byte> list) {
        final byte[] result = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static double[] doubleListToArray(final List<Double> list) {
        final double[] result = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static float[] floatListToArray(final List<Float> list) {
        final float[] result = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static boolean[] boolListToArray(final List<Boolean> list) {
        final boolean[] result = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}