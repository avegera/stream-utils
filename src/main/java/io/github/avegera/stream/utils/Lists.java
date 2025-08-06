package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.Streams.toStream;
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
        return Streams.toStream(collection)
                .collect(toList());
    }

    public static <T> List<T> collect(T[] array) {
        return toStream(array)
                .collect(toList());
    }

    public static <T> List<T> distinct(Collection<T> collection) {
        return Streams.toStream(collection)
                .distinct()
                .collect(toList());
    }

    public static <T> List<T> distinct(T[] array) {
        return toStream(array)
                .distinct()
                .collect(toList());
    }

    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        return Streams.toStream(collection)
                .filter(predicate)
                .collect(toList());
    }

    public static <T> List<T> filter(T[] array, Predicate<T> predicate) {
        return toStream(array)
                .filter(predicate)
                .collect(toList());
    }

    public static <T, R> List<R> flatMap(Collection<T> collection, Function<T, ? extends Stream<R>> flatMapper) {
        return Streams.toStream(collection)
                .flatMap(flatMapper)
                .collect(toList());
    }

    public static <T, R> List<R> flatMap(T[] array, Function<T, ? extends Stream<R>> flatMapper) {
        return toStream(array)
                .flatMap(flatMapper)
                .collect(toList());
    }

    public static <T, R> List<R> flatMapCollections(Collection<T> collection, Function<T, ? extends Collection<R>> flatMapper) {
        return Streams.toStream(collection)
                .flatMap(e -> Streams.toStream(flatMapper.apply(e)))
                .collect(toList());
    }

    public static <T, R> List<R> flatMapCollections(T[] array, Function<T, ? extends Collection<R>> flatMapper) {
        return toStream(array)
                .flatMap(e -> Streams.toStream(flatMapper.apply(e)))
                .collect(toList());
    }

    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        return Streams.toStream(collection)
                .map(mapper)
                .collect(toList());
    }

    public static <T, R> List<R> map(T[] array, Function<T, R> mapper) {
        return toStream(array)
                .map(mapper)
                .collect(toList());
    }

    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        return Streams.toStream(collection)
                .sorted(comparator)
                .collect(toList());
    }

    public static <T> List<T> sort(T[] array, Comparator<T> comparator) {
        return toStream(array)
                .sorted(comparator)
                .collect(toList());
    }
}