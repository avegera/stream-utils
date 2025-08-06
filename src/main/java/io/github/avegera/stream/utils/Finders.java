package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.avegera.stream.utils.Streams.safeStream;

/**
 * The class contains laconic method-aliases for safely performing search operations in Java-collections.
 */
public class Finders {

    private Finders() {
        //empty private constructor
    }

    public static <T> Optional<T> findAny(Collection<T> collection) {
        return safeStream(collection)
                .findAny();
    }

    @SafeVarargs
    public static <T> Optional<T> findAny(T... array) {
        return safeStream(array)
                .findAny();
    }

    public static <T> T findAnyOrDefault(Collection<T> collection, T defaultValue) {
        return safeStream(collection)
                .findAny()
                .orElse(defaultValue);
    }

    public static <T> T findAnyOrDefault(T[] array, T defaultValue) {
        return safeStream(array)
                .findAny()
                .orElse(defaultValue);
    }

    public static <T> T findAnyOrNull(Collection<T> collection) {
        return safeStream(collection)
                .findAny()
                .orElse(null);
    }

    @SafeVarargs
    public static <T> T findAnyOrNull(T... array) {
        return safeStream(array)
                .findAny()
                .orElse(null);
    }

    public static <T> Optional<T> findFirst(Collection<T> collection) {
        return safeStream(collection)
                .findFirst();
    }

    @SafeVarargs
    public static <T> Optional<T> findFirst(T... array) {
        return safeStream(array)
                .findFirst();
    }

    public static <T> T findFirstOrDefault(Collection<T> collection, T defaultValue) {
        return safeStream(collection)
                .findFirst()
                .orElse(defaultValue);
    }

    public static <T> T findFirstOrDefault(T[] array, T defaultValue) {
        return safeStream(array)
                .findFirst()
                .orElse(defaultValue);
    }

    public static <T> T findFirstOrNull(Collection<T> collection) {
        return safeStream(collection)
                .findFirst()
                .orElse(null);
    }

    @SafeVarargs
    public static <T> T findFirstOrNull(T... array) {
        return safeStream(array)
                .findFirst()
                .orElse(null);
    }

    public static <T> T findFirstOrNull(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public static <T> T findFirstOrNull(T[] array, Predicate<T> predicate) {
        return safeStream(array)
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}