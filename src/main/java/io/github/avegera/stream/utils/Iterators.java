package io.github.avegera.stream.utils;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.avegera.stream.utils.Streams.safeStream;


/**
 * For-each iterators method-aliases.
 * <p>
 * The class contains laconic method-aliases for safe iteration through the collections in Java.
 * The result of class methods operations is {@link java.lang.Void}.
 */
public class Iterators {

    private Iterators() {
        //empty private constructor
    }

    public static <T> void forEach(Collection<T> collection, Consumer<T> consumer) {
        safeStream(collection)
                .forEach(consumer);
    }

    public static <T, R> void setForEach(Collection<T> collection, BiConsumer<T, R> setter, Function<T, R> valueExtractor) {
        safeStream(collection)
                .forEach(item -> setter.accept(item, valueExtractor.apply(item)));
    }

    public static <T, R> void setValueForEach(Collection<T> collection, BiConsumer<T, R> setter, R value) {
        safeStream(collection)
                .forEach(item -> setter.accept(item, value));
    }

    //TODO: ADR whe different naming
}