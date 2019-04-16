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

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        /* null nric */
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        /* invalid nric */
        // empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("")));
        // spaces only
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric(" ")));
        // only non-alphanumericcharacters
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("^")));
        // contains non-alphanumeric characters
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("F2435126*")));
        // contains non-alphanumeric characters
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("F243*126L")));
        // does not end with a character
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("F2435126")));
        // does not start with a valid character
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("P2435126L")));
        // does not start with a character
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("2435126L")));
        // more than 7 digits
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("s123456733333J")));
        // less than 7 digits
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("s12J")));
        // more than 2 starting characters
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("FFFFF2435126L")));
        // more than 2 ending characters
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("F2435126LLLLLL")));
        // whitespaces in between nric code
        Assert.assertThrows(IllegalArgumentException.class, () -> assertFalse(Nric.isValidNric("F243    5126L")));

        /* valid nric */
        assertTrue(Nric.isValidNric("S2200025Z")); // all caps
        assertTrue(Nric.isValidNric("s2200025z")); // no caps
        assertTrue(Nric.isValidNric("s2200025Z")); // last character in caps
        assertTrue(Nric.isValidNric("S2200025z")); // first character in caps
        assertTrue(Nric.isValidNric("F2435126L")); // start with F
        assertTrue(Nric.isValidNric("T3569534B")); // start with T
        assertTrue(Nric.isValidNric("G6189344Q")); // start with G
    }
}
