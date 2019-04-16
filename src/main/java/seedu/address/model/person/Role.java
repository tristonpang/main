package seedu.address.model.person;

import java.util.Arrays;

/**
 * Represents the roles that a person can be, i.e. Either a Patient or a Doctor.
 */
public enum Role {
    DOCTOR,
    PATIENT;

    public static final String MESSAGE_ROLE_CONSTRAINTS = "Role should only be 'Doctor' or 'Patient'";

    /**
     * Checks that the given string is a valid {@code Role}.
     *
     * @param role Given input to check.
     * @return True if given string is a valid role (case-insensitive).
     */
    public static boolean isValidRole(String role) {
        return Arrays.stream(Role.values()).anyMatch(modelRole -> modelRole.toString().equalsIgnoreCase(role));
    }
}
