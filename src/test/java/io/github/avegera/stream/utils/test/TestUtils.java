package io.github.avegera.stream.utils.test;

import io.github.avegera.stream.utils.test.domain.User;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {

    public static final String SIZE_SHOULD_BE_POSITIVE_INTEGER_NUMBER = "Size should be positive integer number";

    private static final Predicate<Integer> ALWAYS_TRUE = (i) -> true;

    private TestUtils() {
        //empty private constructor
    }

    public static <T> List<T> getList(int size, Function<Integer, T> constructor, Predicate<Integer> predicate) {
        return getStream(size, constructor, predicate).collect(toList());
    }

    public static <T> List<T> getList(int size, Function<Integer, T> constructor) {
        return getStream(size, constructor, ALWAYS_TRUE).collect(toList());
    }

    public static <T> List<T> getReversedList(int size, Function<Integer, T> constructor) {
        assertTrue(size > 0, SIZE_SHOULD_BE_POSITIVE_INTEGER_NUMBER);
        return IntStream.range(1, size + 1)
                .boxed()
                .map(i -> size + 1 - i)
                .map(constructor)
                .collect(toList());
    }

    public static <T> Set<T> getSet(int size, Function<Integer, T> constructor, Predicate<Integer> predicate) {
        return getStream(size, constructor, predicate).collect(toSet());
    }

    public static <T> Set<T> getSet(int size, Function<Integer, T> constructor) {
        return getStream(size, constructor, ALWAYS_TRUE).collect(toSet());
    }

    public static <T> List<T> mergeListsForEachSize(int size, Function<Integer, T> constructor) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.addAll(getList(i + 1, constructor));
        }
        return list;
    }

    public static <T> Set<T> mergeSetsForEachSize(int size, Function<Integer, T> constructor) {
        Set<T> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            set.addAll(getSet(i + 1, constructor));
        }
        return set;
    }

    private static <T> Stream<T> getStream(int size, Function<Integer, T> constructor, Predicate<Integer> predicate) {
        assertTrue(size > 0, SIZE_SHOULD_BE_POSITIVE_INTEGER_NUMBER);
        return IntStream.range(1, size + 1)
                .boxed()
                .filter(predicate)
                .map(constructor);
    }

    public static List<Integer> getNaturalNumbers(int size) {
        assertTrue(size > 0, SIZE_SHOULD_BE_POSITIVE_INTEGER_NUMBER);
        return IntStream.range(1, size + 1)
                .boxed()
                .collect(toList());
    }

    public static Set<Integer> getNaturalNumbersSet(int size) {
        assertTrue(size > 0, SIZE_SHOULD_BE_POSITIVE_INTEGER_NUMBER);
        return IntStream.range(1, size + 1)
                .boxed()
                .collect(toSet());
    }

    public static Collection<Object> getCollection(int size) {
        assertTrue(size > 0, SIZE_SHOULD_BE_POSITIVE_INTEGER_NUMBER);
        return IntStream.range(1, size + 1)
                .boxed()
                .map((i) -> new Object())
                .collect(toList());
    }

    public static List<Integer> getIntegers(int endInclusive) {
        return rangeClosed(1, endInclusive).boxed().collect(toList());
    }

    public static List<String> getStrings(int endInclusive) {
        return mapObjects(endInclusive, Objects::toString);
    }

    public static <T> List<T> mapObjects(int endInclusive, Function<Integer, T> mapper) {
        return rangeClosed(1, endInclusive).boxed().map(mapper).collect(toList());
    }

    public static List<User> getUsers(int endInclusive) {
        return rangeClosed(1, endInclusive).boxed().map(it -> new User(it, null)).collect(toList());
    }
}