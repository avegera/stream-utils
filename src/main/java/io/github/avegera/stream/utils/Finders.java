package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.avegera.stream.utils.Streams.toStream;

/**
 * The class contains laconic method-aliases for safely performing search operations in Java-collections.
 */
public class Finders {

    private Finders() {
        //empty private constructor
    }

    public static <T> Optional<T> findAny(Collection<T> collection) {
        return Streams.toStream(collection)
                .findAny();
    }

    @SafeVarargs
    public static <T> Optional<T> findAny(T... array) {
        return toStream(array)
                .findAny();
    }

    public static <T> T findAnyOrDefault(Collection<T> collection, T defaultValue) {
        return Streams.toStream(collection)
                .findAny()
                .orElse(defaultValue);
    }

    public static <T> T findAnyOrDefault(T[] array, T defaultValue) {
        return toStream(array)
                .findAny()
                .orElse(defaultValue);
    }

    public static <T> T findAnyOrNull(Collection<T> collection) {
        return Streams.toStream(collection)
                .findAny()
                .orElse(null);
    }

    @SafeVarargs
    public static <T> T findAnyOrNull(T... array) {
        return toStream(array)
                .findAny()
                .orElse(null);
    }

    public static <T> Optional<T> findFirst(Collection<T> collection) {
        return Streams.toStream(collection)
                .findFirst();
    }

    @SafeVarargs
    public static <T> Optional<T> findFirst(T... array) {
        return toStream(array)
                .findFirst();
    }

    public static <T> T findFirstOrDefault(Collection<T> collection, T defaultValue) {
        return Streams.toStream(collection)
                .findFirst()
                .orElse(defaultValue);
    }

    public static <T> T findFirstOrDefault(T[] array, T defaultValue) {
        return toStream(array)
                .findFirst()
                .orElse(defaultValue);
    }

    public static <T> T findFirstOrNull(Collection<T> collection) {
        return Streams.toStream(collection)
                .findFirst()
                .orElse(null);
    }

    @SafeVarargs
    public static <T> T findFirstOrNull(T... array) {
        return toStream(array)
                .findFirst()
                .orElse(null);
    }

    public static <T> T findFirstOrNull(Collection<T> collection, Predicate<T> predicate) {
        return Streams.toStream(collection)
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public static <T> T findFirstOrNull(T[] array, Predicate<T> predicate) {
        return toStream(array)
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}