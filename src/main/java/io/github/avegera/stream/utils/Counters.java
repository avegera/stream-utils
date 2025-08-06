package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.function.Predicate;

import static io.github.avegera.stream.utils.Streams.safeStream;

/**
 * The class contains laconic method-aliases for safety count operations with collections in Java.
 * The result of the class methods execution is a long value.
 */
public class Counters {

    private Counters() {
        //empty private constructor
    }

    public static <T> long count(Collection<T> collection) {
        return safeStream(collection)
                .count();
    }

    @SafeVarargs
    public static <T> long count(T... array) {
        return safeStream(array)
                .count();
    }

    public static <T> long count(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .filter(predicate)
                .count();
    }

    public static <T> long count(T[] array, Predicate<T> predicate) {
        return safeStream(array)
                .filter(predicate)
                .count();
    }
}