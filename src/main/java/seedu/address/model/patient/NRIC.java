package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class NRIC {
    public static final String MESSAGE_NRIC_CONSTRAINTS = "NRIC should contain only alphanumeric " +
            "characters and should not be left blank.";
    public static final String NRIC_VALIDATION_REGEX = "(?i)^[STFG]\\d{7}[A-Z]";
    public final String code;
    public NRIC(String code) {
        requireNonNull(code);
        checkArgument(isValidNric(code), MESSAGE_NRIC_CONSTRAINTS);
        this.code = code;
    }
    public boolean isValidNric(String code) {
        return code.matches(NRIC_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof NRIC // instanceof handles nulls
                && code.equals(((NRIC) obj).code)); // state check
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
