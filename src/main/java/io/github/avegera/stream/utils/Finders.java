package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Optional;

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

    public static <T> T findAnyOrDefault(Collection<T> collection, T defaultValue) {
        return safeStream(collection)
                .findAny()
                .orElse(defaultValue);
    }

    public static <T> T findAnyOrNull(Collection<T> collection) {
        return safeStream(collection)
                .findAny()
                .orElse(null);
    }

    public static <T> Optional<T> findFirst(Collection<T> collection) {
        return safeStream(collection)
                .findFirst();
    }

    public static <T> T findFirstOrDefault(Collection<T> collection, T defaultValue) {
        return safeStream(collection)
                .findFirst()
                .orElse(defaultValue);
    }

    public static <T> T findFirstOrNull(Collection<T> collection) {
        return safeStream(collection)
                .findFirst()
                .orElse(null);
    }
}