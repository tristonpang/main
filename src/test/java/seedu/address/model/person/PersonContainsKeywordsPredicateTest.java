package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GLOBAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.PatientBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Map<Prefix, List<String>> firstPredicateKeywordList = Map.of(PREFIX_GLOBAL, Collections.singletonList("first"));
        Map<Prefix, List<String>> secondPredicateKeywordList = Map.of(PREFIX_GLOBAL, Arrays.asList("first", "second"));

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of(PREFIX_GLOBAL, Collections.singletonList("Alice")));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Map.of(PREFIX_GLOBAL, Arrays.asList("Alice", "Bob")));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Map.of(PREFIX_GLOBAL, Arrays.asList("Bob", "Carol")));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Map.of(PREFIX_GLOBAL, Arrays.asList("aLIce", "bOB")));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Map.of(PREFIX_GLOBAL,
                Collections.emptyList()));
        assertFalse(predicate.test(new PatientBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Map.of(PREFIX_GLOBAL, Arrays.asList("Carol")));
        assertFalse(predicate.test(new PatientBuilder().withName("Alice Bob").build()));

        // Search by phone, email and address, but not name
        predicate = new PersonContainsKeywordsPredicate(Map.of(PREFIX_GLOBAL,
                Arrays.asList("12345", "alice@email.com", "Main", "Street")));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
