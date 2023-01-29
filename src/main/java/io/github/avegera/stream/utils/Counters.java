package io.github.avegera.stream.utils;

import java.util.Collection;

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
}