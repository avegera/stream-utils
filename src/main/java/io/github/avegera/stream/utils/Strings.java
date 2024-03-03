package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Objects;

import static io.github.avegera.stream.utils.Streams.safeStream;
import static java.util.stream.Collectors.joining;

/**
 * The class contains laconic method-aliases for safe stream operations with collections in Java.
 * The result of class methods operations is {@link java.lang.String}
 */
public class Strings {

    private Strings() {
        //empty private constructor
    }

    public static String joinToString(Collection<?> collection, String delimiter) {
        return safeStream(collection)
                .map(Objects::toString)
                .collect(joining(delimiter));
    }
}