package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.Streams.toStream;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

/**
 * The class contains laconic method-aliases for safe stream operations with collections in Java.
 * The result of class methods operations is {@link java.util.Set}
 */
public class Sets {

    private Sets() {
        //empty private constructor
    }

    public static <T> Set<T> collectToSet(Collection<T> collection) {
        return Streams.toStream(collection)
                .collect(toSet());
    }

    public static <T> Set<T> collectToSet(T[] array) {
        return toStream(array)
                .collect(toSet());
    }

    public static <T> Set<T> distinctToSet(Collection<T> collection) {
        return Streams.toStream(collection)
                .distinct()
                .collect(toSet());
    }

    public static <T> Set<T> distinctToSet(T[] array) {
        return toStream(array)
                .distinct()
                .collect(toSet());
    }

    public static <T> Set<T> filterToSet(Collection<T> collection, Predicate<T> predicate) {
        return Streams.toStream(collection)
                .filter(predicate)
                .collect(toSet());
    }

    public static <T> Set<T> filterToSet(T[] array, Predicate<T> predicate) {
        return toStream(array)
                .filter(predicate)
                .collect(toSet());
    }

    public static <T, R> Set<R> flatMapToSet(Collection<T> collection, Function<T, ? extends Stream<R>> flatMapper) {
        return Streams.toStream(collection)
                .flatMap(flatMapper)
                .collect(toSet());
    }

    public static <T, R> Set<R> flatMapToSet(T[] array, Function<T, ? extends Stream<R>> flatMapper) {
        return toStream(array)
                .flatMap(flatMapper)
                .collect(toSet());
    }

    public static <T, R> Set<R> flatMapCollectionsToSet(Collection<T> collection, Function<T, ? extends Collection<R>> flatMapper) {
        return Streams.toStream(collection)
                .flatMap(e -> Streams.toStream(flatMapper.apply(e)))
                .collect(toSet());
    }

    public static <T, R> Set<R> flatMapCollectionsToSet(T[] array, Function<T, ? extends Collection<R>> flatMapper) {
        return toStream(array)
                .flatMap(e -> Streams.toStream(flatMapper.apply(e)))
                .collect(toSet());
    }

    public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<T, R> mapper) {
        return Streams.toStream(collection)
                .map(mapper)
                .collect(toSet());
    }

    public static <T, R> Set<R> mapToSet(T[] array, Function<T, R> mapper) {
        return toStream(array)
                .map(mapper)
                .collect(toSet());
    }

    public static <T> Set<T> sortToSet(Collection<T> collection, Comparator<T> comparator) {
        return Streams.toStream(collection)
                .sorted(comparator)
                .collect(toCollection(LinkedHashSet::new));
    }

    public static <T> Set<T> sortToSet(T[] array, Comparator<T> comparator) {
        return toStream(array)
                .sorted(comparator)
                .collect(toCollection(LinkedHashSet::new));
    }
}