package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.Streams.safeStream;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

/**
 * The class contains laconic method-aliases for safety stream operations with collections in Java.
 * The result of class methods operations is {@link java.util.Set}
 */
public class Sets {

    private Sets() {
        //empty private constructor
    }

    public static <T> Set<T> collectToSet(Collection<T> collection) {
        return safeStream(collection)
                .collect(toSet());
    }

    public static <T> Set<T> distinctToSet(Collection<T> collection) {
        return safeStream(collection)
                .distinct()
                .collect(toSet());
    }

    public static <T> Set<T> filterToSet(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .filter(predicate)
                .collect(toSet());
    }

    public static <T, R> Set<R> flatMapToSet(Collection<T> collection, Function<T, ? extends Stream<R>> flatMapper) {
        return safeStream(collection)
                .flatMap(flatMapper)
                .collect(toSet());
    }

    public static <T, R> Set<R> flatMapCollectionsToSet(Collection<T> collection, Function<T, ? extends Collection<R>> flatMapper) {
        return safeStream(collection)
                .flatMap(e -> safeStream(flatMapper.apply(e)))
                .collect(toSet());
    }

    public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<T, R> mapper) {
        return safeStream(collection)
                .map(mapper)
                .collect(toSet());
    }

    public static <T> Set<T> sortToSet(Collection<T> collection, Comparator<T> comparator) {
        return safeStream(collection)
                .sorted(comparator)
                .collect(toCollection(LinkedHashSet::new));
    }
}