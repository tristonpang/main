package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    // @Test
    // public void constructor_invalidNric_throwsIllegalArgumentException() {
    //     String invalidNric = "";
    //     Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    // }

    @Test
    public void isValidNric() {
        // null nric
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("^")); // only non-alphanumeric characters
        assertFalse(Nric.isValidNric("s1234567*")); // contains non-alphanumeric characters
        assertFalse(Nric.isValidNric("s123*567B")); // contains non-alphanumeric characters
        assertFalse(Nric.isValidNric("s12345670")); // does not end with a character
        assertFalse(Nric.isValidNric("P1234567a")); // does not start with a valid character
        assertFalse(Nric.isValidNric("11234567A")); // does not start with a character
        assertFalse(Nric.isValidNric("s12345673333333J")); // more than 7 digits
        assertFalse(Nric.isValidNric("s12J")); // less than 7 digits
        assertFalse(Nric.isValidNric("ssss1234567j")); // more than 2 starting characters
        assertFalse(Nric.isValidNric("s1234567ttttttt")); // more than 2 ending characters
        assertFalse(Nric.isValidNric("s1234   567t")); // whitespaces in between nric code

        // valid nric
        assertTrue(Nric.isValidNric("S1234567A")); // all caps
        assertTrue(Nric.isValidNric("s2234567a")); // no caps
        assertTrue(Nric.isValidNric("s1235567A")); // last character in caps
        assertTrue(Nric.isValidNric("S9234567a")); // first character in caps
        assertTrue(Nric.isValidNric("T1234567A")); // start with F
        assertTrue(Nric.isValidNric("F1234567A")); // start with T
        assertTrue(Nric.isValidNric("G1234567A")); // start with G
    }
}
