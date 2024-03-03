package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.function.Predicate;

import static io.github.avegera.stream.utils.Streams.safeStream;

/**
 * The class contains laconic method-aliases for safely performing search operations in Java-collections.
 * The result of the class methods execution is a boolean value.
 */
public class Matchers {

    private Matchers() {
        //empty private constructor
    }

    public static <T> boolean allMatch(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .allMatch(predicate);
    }

    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .anyMatch(predicate);
    }

    public static <T> boolean noneMatch(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .noneMatch(predicate);
    }
}