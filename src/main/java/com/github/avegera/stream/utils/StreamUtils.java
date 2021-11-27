package com.github.avegera.stream.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.avegera.stream.utils.SafeStreamUtils.safeStream;
import static com.github.avegera.stream.utils.SafeStreamUtils.safeStreamFunction;
import static java.util.stream.Collectors.*;

public class StreamUtils {

    private StreamUtils() {
        //empty private constructor
    }

    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        return safeStream(collection)
                .map(mapper)
                .collect(toList());
    }

    public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<T, R> mapper) {
        return safeStream(collection)
                .map(mapper)
                .collect(toSet());
    }

    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .filter(predicate)
                .collect(toList());
    }

    public static <T> Set<T> filterToSet(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .filter(predicate)
                .collect(toSet());
    }

    public static <T, R> List<R> flatMap(Collection<T> collection, Function<? super T, ? extends Stream<? extends R>> flatMapper) {
        return safeStream(collection)
                .flatMap(flatMapper)
                .collect(toList());
    }

    public static <T, R> Set<R> flatMapToSet(Collection<T> collection, Function<? super T, ? extends Stream<? extends R>> flatMapper) {
        return safeStream(collection)
                .flatMap(flatMapper)
                .collect(toSet());
    }

    public static <T, R> List<R> flatMapCollections(Collection<T> collection, Function<? super T, ? extends Collection<R>> flatMapper) {
        return safeStream(collection)
                .flatMap(safeStreamFunction(flatMapper))
                .collect(toList());
    }

    public static <T, R> Set<R> flatMapCollectionsToSet(Collection<T> collection, Function<? super T, ? extends Collection<R>> flatMapper) {
        return safeStream(collection)
                .flatMap(safeStreamFunction(flatMapper))
                .collect(toSet());
    }

    public static <T> List<T> distinct(Collection<T> collection) {
        return safeStream(collection)
                .distinct()
                .collect(toList());
    }

    public static <T> Set<T> distinctToSet(Collection<T> collection) {
        return safeStream(collection)
                .distinct()
                .collect(toSet());
    }

    public static <T> List<T> collectToList(Collection<T> collection) {
        return safeStream(collection)
                .collect(toList());
    }

    public static <T> Set<T> collectToSet(Collection<T> collection) {
        return safeStream(collection)
                .collect(toSet());
    }

    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        return safeStream(collection)
                .sorted(comparator)
                .collect(toList());
    }

    public static <T> Set<T> sortToSet(Collection<T> collection, Comparator<T> comparator) {
        return safeStream(collection)
                .sorted(comparator)
                .collect(toCollection(LinkedHashSet::new));
    }

    public static <T> long count(Collection<T> collection) {
        return safeStream(collection)
                .count();
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

    public static <T> boolean allMatch(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .allMatch(predicate);
    }

    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .anyMatch(predicate);
    }

    public static <T> boolean noneMatch(Collection<T> collection, Predicate<T> predicate) {
        return safeStream(collection)
                .noneMatch(predicate);
    }
}