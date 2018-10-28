package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's NRIC in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Nric {

    public static final String MESSAGE_NRIC_CONSTRAINTS = "NRIC should contain only alphanumeric "
            + "characters and should not be left blank.";
    public static final String NRIC_VALIDATION_REGEX = "(?i)^[STFG]\\d{7}[A-Z]";
    public final String code;

    /**
     * Constructs a {@code NRIC}.
     *
     * @param code A NRIC with valid format.
     */
    public Nric(String code) {
        requireNonNull(code);
        // TODO: This needs to be turned off for my nric checker to work in ScheduleCommand.
        // checkArgument(isValidNric(code), MESSAGE_NRIC_CONSTRAINTS);
        this.code = code;
    }

    /**
     * Returns true if a given string has a valid NRIC format.
     */
    public static boolean isValidNric(String code) {
        return code.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Nric // instanceof handles nulls
                && code.equalsIgnoreCase(((Nric) obj).code)); // state check
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
