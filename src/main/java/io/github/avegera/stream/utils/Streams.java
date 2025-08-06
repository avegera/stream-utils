package io.github.avegera.stream.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static java.util.stream.Stream.empty;

/**
 * The class contains laconic method-aliases for safe stream operations with collections in Java.
 * The result of class methods operations is {@link java.util.stream.Stream}
 */
public class Streams {

    private Streams() {
        //empty private constructor
    }

    public static <T> Stream<T> toStream(Collection<T> collection) {
        return collection != null && !collection.isEmpty() ? collection.stream() : empty();
    }

    @SafeVarargs
    public static <T> Stream<T> toStream(T... array) {
        return array != null && array.length > 0 ? Arrays.stream(array) : empty();
    }
}