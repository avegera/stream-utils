package com.github.avegera.stream.utils;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Stream.empty;

/**
 * Safe stream utils class contains laconic method-aliases for safe stream initialization from java collections.
 */
public class SafeStreamUtils {

    private SafeStreamUtils() {
        //empty private constructor
    }

    public static <T> Stream<T> safeStream(Collection<T> collection) {
        return collection != null && collection.size() > 0 ? collection.stream() : empty();
    }

    public static <T, R> Function<T, Stream<? extends R>> safeStreamFunction(Function<? super T, ? extends Collection<? extends R>> flatMapper) {
        return e -> safeStream(flatMapper.apply(e));
    }
}