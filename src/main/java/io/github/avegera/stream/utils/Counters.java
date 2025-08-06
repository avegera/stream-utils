package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.function.Predicate;

import static io.github.avegera.stream.utils.Streams.toStream;

/**
 * The class contains laconic method-aliases for safety count operations with collections in Java.
 * The result of the class methods execution is a long value.
 */
public class Counters {

    private Counters() {
        //empty private constructor
    }

    public static <T> long count(Collection<T> collection) {
        return Streams.toStream(collection)
                .count();
    }

    @SafeVarargs
    public static <T> long count(T... array) {
        return toStream(array)
                .count();
    }

    public static <T> long count(Collection<T> collection, Predicate<T> predicate) {
        return Streams.toStream(collection)
                .filter(predicate)
                .count();
    }

    public static <T> long count(T[] array, Predicate<T> predicate) {
        return toStream(array)
                .filter(predicate)
                .count();
    }
}