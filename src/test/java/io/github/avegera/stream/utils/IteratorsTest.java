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
    @DisplayName("forEach(Collection<T>, Consumer<T>)")
    class ForEachCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable consumer")
            void forNullableConsumer() {
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
                forEach((List<Object>) null, item -> hashCodes.add(item.hashCode()));
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

        @Nested
        @DisplayName("executes consumer for each element")
        class ExecutesConsumerForEachElement {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of integers")
            void forCollectionOfIntegers(int size) {
                List<Integer> list = getIntegers(size);
                AtomicInteger sum = new AtomicInteger(0);

                forEach(list, sum::getAndAdd);

                int expectedSum = rangeClosed(1, size).sum();
                assertThat(sum.intValue()).isEqualTo(expectedSum);
            }
        }
    }

    @Nested
    @DisplayName("forEach(T[], Consumer<T>)")
    class ForEachArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable consumer")
            void forNullableConsumer() {
                Integer[] array = {1, 2, 3};
                assertThrows(NullPointerException.class, () -> forEach(array, null));
            }
        }

        @Nested
        @DisplayName("do nothing")
        class DoNothing {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Integer> hashCodes = new ArrayList<>();
                forEach((Integer[]) null, item -> hashCodes.add(item.hashCode()));
                assertThat(hashCodes).isEmpty();
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Integer> hashCodes = new ArrayList<>();
                forEach(new Integer[]{}, item -> hashCodes.add(item.hashCode()));
                assertThat(hashCodes).isEmpty();
            }
        }

        @Nested
        @DisplayName("executes consumer for each element")
        class ExecutesConsumerForEachElement {

            @Test
            @DisplayName("for array of integers")
            void forArrayOfIntegers() {
                Integer[] array = {1, 2, 3, 4, 5};
                AtomicInteger sum = new AtomicInteger(0);

                forEach(array, sum::getAndAdd);

                int expectedSum = 15; // 1 + 2 + 3 + 4 + 5
                assertThat(sum.intValue()).isEqualTo(expectedSum);
            }
        }
    }

    @Nested
    @DisplayName("setForEach(Collection<T>, BiConsumer<T, R>, Function<T, R>)")
    class SetForEachCollection {

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
                assertThrows(NullPointerException.class, () -> setForEach(list, (item, value) -> {}, null));
            }
        }

        @Nested
        @DisplayName("do nothing")
        class DoNothing {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Integer> hashCodes = new ArrayList<>();
                setForEach((List<Object>) null, (item, value) -> hashCodes.add(value), Object::hashCode);
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

        @Nested
        @DisplayName("executes bi-consumer with mapper result for each element")
        class ExecutesBiConsumerWithMapperResultForEachElement {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of users")
            void forCollectionOfUsers(int size) {
                List<User> users = getUsers(size);

                setForEach(users, User::setZipCode, user -> TEST_ZIP_CODE + user.getId());

                for (User user : users) {
                    assertThat(user.getZipCode()).isEqualTo(TEST_ZIP_CODE + user.getId());
                }
            }
        }
    }

    @Nested
    @DisplayName("setForEach(T[], BiConsumer<T, R>, Function<T, R>)")
    class SetForEachArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable bi-consumer")
            void forNullableBiConsumer() {
                Integer[] array = {1, 2, 3};
                assertThrows(NullPointerException.class, () -> setForEach(array, null, identity()));
            }

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                Integer[] array = {1, 2, 3};
                assertThrows(NullPointerException.class, () -> setForEach(array, (item, value) -> {}, null));
            }
        }

        @Nested
        @DisplayName("do nothing")
        class DoNothing {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Integer> hashCodes = new ArrayList<>();
                setForEach((Integer[]) null, (item, value) -> hashCodes.add(value), Object::hashCode);
                assertThat(hashCodes).isEmpty();
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Integer> hashCodes = new ArrayList<>();
                setForEach(new Integer[]{}, (item, value) -> hashCodes.add(value), Object::hashCode);
                assertThat(hashCodes).isEmpty();
            }
        }

        @Nested
        @DisplayName("executes bi-consumer with mapper result for each element")
        class ExecutesBiConsumerWithMapperResultForEachElement {

            @Test
            @DisplayName("for array of users")
            void forArrayOfUsers() {
                User[] users = getUsers(5).toArray(new User[0]);

                setForEach(users, User::setZipCode, user -> TEST_ZIP_CODE + user.getId());

                for (User user : users) {
                    assertThat(user.getZipCode()).isEqualTo(TEST_ZIP_CODE + user.getId());
                }
            }
        }
    }

    @Nested
    @DisplayName("setValueForEach(Collection<T>, BiConsumer<T, R>, R)")
    class SetValueForEachCollection {

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
                setValueForEach((List<Object>) null, (item, value) -> hashCodes.add(item.hashCode() + value), 1);
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

        @Nested
        @DisplayName("executes bi-consumer with value for each element")
        class ExecutesBiConsumerWithValueForEachElement {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of users")
            void forCollectionOfUsers(int size) {
                List<User> users = getUsers(size);

                setValueForEach(users, User::setZipCode, TEST_ZIP_CODE);

                for (User user : users) {
                    assertThat(user.getZipCode()).isEqualTo(TEST_ZIP_CODE);
                }
            }
        }
    }

    @Nested
    @DisplayName("setValueForEach(T[], BiConsumer<T, R>, R)")
    class SetValueForEachArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable bi-consumer")
            void forNullableBiConsumer() {
                Integer[] array = {1, 2, 3};
                assertThrows(NullPointerException.class, () -> setValueForEach(array, null, new Object()));
            }
        }

        @Nested
        @DisplayName("do nothing")
        class DoNothing {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Integer> hashCodes = new ArrayList<>();
                setValueForEach((Integer[]) null, (item, value) -> hashCodes.add(item.hashCode() + value), 1);
                assertThat(hashCodes).isEmpty();
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Integer> hashCodes = new ArrayList<>();
                setValueForEach(new Integer[]{}, (item, value) -> hashCodes.add(item.hashCode() + value), 1);
                assertThat(hashCodes).isEmpty();
            }
        }

        @Nested
        @DisplayName("executes bi-consumer with value for each element")
        class ExecutesBiConsumerWithValueForEachElement {

            @Test
            @DisplayName("for array of users")
            void forArrayOfUsers() {
                User[] users = getUsers(5).toArray(new User[0]);

                setValueForEach(users, User::setZipCode, TEST_ZIP_CODE);

                for (User user : users) {
                    assertThat(user.getZipCode()).isEqualTo(TEST_ZIP_CODE);
                }
            }
        }
    }
}