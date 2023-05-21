package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.Streams.safeStream;
import static java.util.stream.Collectors.toList;

/**
 * The class contains laconic method-aliases for safety stream operations with collections in Java.
 * The result of class methods operations is {@link java.util.List}
 */
public class Lists {

    private Lists() {
        //empty private constructor
    }

    public static <T> List<T> collect(Collection<T> collection) {
        return safeStream(collection)
                .collect(toList());
    }

    public static <T> List<T> distinct(Collection<T> collection) {
        return safeStream(collection)
                .distinct()
                .collect(toList());
    }

    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .filter(predicate)
                .collect(toList());
    }

    public static <T, R> List<R> flatMap(Collection<T> collection, Function<T, ? extends Stream<R>> flatMapper) {
        return safeStream(collection)
                .flatMap(flatMapper)
                .collect(toList());
    }

    public static <T, R> List<R> flatMapCollections(Collection<T> collection, Function<T, ? extends Collection<R>> flatMapper) {
        return safeStream(collection)
                .flatMap(e -> safeStream(flatMapper.apply(e)))
                .collect(toList());
    }

    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        return safeStream(collection)
                .map(mapper)
                .collect(toList());
    }

    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        return safeStream(collection)
                .sorted(comparator)
                .collect(toList());
    }
}