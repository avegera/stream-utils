package io.github.avegera.stream.utils.test;

import io.github.avegera.stream.utils.test.domain.Organization;
import lombok.experimental.UtilityClass;

import java.util.function.Predicate;

@UtilityClass
public class PredicateUtils {

    public static final Predicate<Integer> INTEGER_IS_EVEN = i -> i % 2 == 0;

    public static final Predicate<Integer> INTEGER_IS_ODD = i -> i % 2 == 1;

    public static final Predicate<Organization> ORG_WITH_EVEN_ID = org -> INTEGER_IS_EVEN.test(org.getId());

    public static final Predicate<Organization> ORG_WITH_ODD_ID = org -> INTEGER_IS_ODD.test(org.getId());

    public static final Predicate<Organization> ORG_WITH_NEGATIVE_ID = org -> org.getId() < 0;

    public static final Predicate<?> ALWAYS_TRUE = (o) -> true;
}