package com.github.avegera.stream.utils.test;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@UtilityClass
public class StreamAssertions {

    public static <T> void assertEmptyStream(Stream<T> stream) {
        assertNotNull(stream);
        assertEquals(0, stream.count());
    }

    public static <T> void assertCollectionIsEmpty(Collection<T> collection) {
        assertNotNull(collection);
        assertEquals(0, collection.size());
    }

    public static <T> void assertIterableEquals(Iterable<T> iterable, Stream<T> stream) {
        if (iterable == null && stream == null) {
            return;
        }
        assertNotNull(stream);
        assertNotNull(iterable);
        assertIteratorEquals(iterable.iterator(), stream.iterator());
    }

    public static <T> void assertIterableEquals(Iterable<T> first, Iterable<T> second) {
        if (first == null && second == null) {
            return;
        }
        assertNotNull(first);
        assertNotNull(second);
        assertIteratorEquals(first.iterator(), second.iterator());
    }

    private static <T> void assertIteratorEquals(Iterator<T> iterator1, Iterator<T> iterator2) {
        while (iterator1.hasNext() && iterator2.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }
        assertTrue(!iterator1.hasNext() && !iterator2.hasNext());
    }

    public static <T> void assertEqualsIgnoringOrder(Collection<T> first, Collection<T> second) {
        assertTrue(CollectionUtils.isEqualCollection(first, second));
    }
}