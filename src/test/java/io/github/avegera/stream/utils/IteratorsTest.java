package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import io.github.avegera.stream.utils.test.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.avegera.stream.utils.Iterators.*;
import static io.github.avegera.stream.utils.test.TestUtils.getIntegers;
import static io.github.avegera.stream.utils.test.TestUtils.getUsers;
import static java.util.function.Function.identity;
import static java.util.stream.IntStream.rangeClosed;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IteratorsTest {

    private static final String TEST_ZIP_CODE = "testZipCode";

    @Nested
    @DisplayName("For each method")
    class ForEach {

        @Nested
        @DisplayName("with consumer argument")
        class WithConsumer {

            @Nested
            @DisplayName("throws null pointer exception")
            class ThrowsNullPointerException {

                @Test
                @DisplayName("for nullable consumer")
                void forNullableMapper() {
                    List<Object> list = new ArrayList<>();
                    assertThrows(NullPointerException.class, () -> forEach(list, null));
                }
            }

            @Nested
            @DisplayName("do nothing")
            class DoNothing {

                @Test
                @DisplayName("for nullable collection")
                void forNullableCollection() {
                    List<Integer> hashCodes = new ArrayList<>();
                    forEach(null, item -> hashCodes.add(item.hashCode()));
                    assertThat(hashCodes).isEmpty();
                }

                @Test
                @DisplayName("for empty list")
                void forEmptyList() {
                    List<Integer> hashCodes = new ArrayList<>();
                    forEach(new ArrayList<>(), item -> hashCodes.add(item.hashCode()));
                    assertThat(hashCodes).isEmpty();
                }

                @Test
                @DisplayName("for empty set")
                void forEmptySet() {
                    List<Integer> hashCodes = new ArrayList<>();
                    forEach(new HashSet<>(), item -> hashCodes.add(item.hashCode()));
                    assertThat(hashCodes).isEmpty();
                }
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("executes provided consumer for each element in collection")
            void executesConsumersForEachElement(int size) {
                List<Integer> list = getIntegers(size);
                AtomicInteger sum = new AtomicInteger(0);

                forEach(list, sum::getAndAdd);

                int expectedSum = rangeClosed(1, size).sum();
                assertThat(sum.intValue()).isEqualTo(expectedSum);
            }
        }

        @Nested
        @DisplayName("with bi-consumer and value arguments")
        class WithBiConsumerAndValue {

            @Nested
            @DisplayName("throws null pointer exception")
            class ThrowsNullPointerException {

                @Test
                @DisplayName("for nullable bi-consumer")
                void forNullableBiConsumer() {
                    List<Integer> list = getIntegers(10);
                    assertThrows(NullPointerException.class, () -> setValueForEach(list, null, new Object()));
                }
            }

            @Nested
            @DisplayName("do nothing")
            class DoNothing {

                @Test
                @DisplayName("for nullable collection")
                void forNullableCollection() {
                    List<Integer> hashCodes = new ArrayList<>();
                    setValueForEach(null, (item, value) -> hashCodes.add(item.hashCode() + value), 1);
                    assertThat(hashCodes).isEmpty();
                }

                @Test
                @DisplayName("for empty list")
                void forEmptyList() {
                    List<Integer> hashCodes = new ArrayList<>();
                    setValueForEach(new ArrayList<>(), (item, value) -> hashCodes.add(item.hashCode() + value), 1);
                    assertThat(hashCodes).isEmpty();
                }

                @Test
                @DisplayName("for empty set")
                void forEmptySet() {
                    List<Integer> hashCodes = new ArrayList<>();
                    setValueForEach(new HashSet<>(), (item, value) -> hashCodes.add(item.hashCode() + value), 1);
                    assertThat(hashCodes).isEmpty();
                }
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("executes provided bi-consumerfor each element in collection")
            void executesConsumersForEachElement(int size) {
                List<User> users = getUsers(size);

                setValueForEach(users, User::setZipCode, TEST_ZIP_CODE);

                for (User user : users) {
                    assertThat(user.getZipCode()).isEqualTo(TEST_ZIP_CODE);
                }
            }
        }

        @Nested
        @DisplayName("with bi-consumer and mapper arguments")
        class WithBiConsumerAndMapper {

            @Nested
            @DisplayName("throws null pointer exception")
            class ThrowsNullPointerException {

                @Test
                @DisplayName("for nullable bi-consumer")
                void forNullableBiConsumer() {
                    List<Integer> list = getIntegers(10);
                    assertThrows(NullPointerException.class, () -> setForEach(list, null, identity()));
                }

                @Test
                @DisplayName("for nullable mapper")
                void forNullableMapper() {
                    List<Integer> list = getIntegers(10);
                    assertThrows(NullPointerException.class, () -> setForEach(list, (item, value) -> {
                    }, null));
                }
            }

            @Nested
            @DisplayName("do nothing")
            class DoNothing {

                @Test
                @DisplayName("for nullable collection")
                void forNullableCollection() {
                    List<Integer> hashCodes = new ArrayList<>();
                    setForEach(null, (item, value) -> hashCodes.add(value), Object::hashCode);
                    assertThat(hashCodes).isEmpty();
                }

                @Test
                @DisplayName("for empty list")
                void forEmptyList() {
                    List<Integer> hashCodes = new ArrayList<>();
                    setForEach(new ArrayList<>(), (item, value) -> hashCodes.add(value), Object::hashCode);
                    assertThat(hashCodes).isEmpty();
                }

                @Test
                @DisplayName("for empty set")
                void forEmptySet() {
                    List<Integer> hashCodes = new ArrayList<>();
                    Set<Integer> collection = new HashSet<>();
                    setForEach(collection, (item, value) -> hashCodes.add(value), Object::hashCode);
                    assertThat(hashCodes).isEmpty();
                }
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("executes provided bi-consumer with mapper result for each element in collection")
            void executesConsumersForEachElement(int size) {
                List<User> users = getUsers(size);

                setForEach(users, User::setZipCode, user -> TEST_ZIP_CODE + user.getId());

                for (User user : users) {
                    assertThat(user.getZipCode()).isEqualTo(TEST_ZIP_CODE + user.getId());
                }
            }
        }
    }
}