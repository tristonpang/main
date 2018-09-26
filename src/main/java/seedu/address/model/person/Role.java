package seedu.address.model.person;

import java.lang.reflect.Array;
import java.util.Arrays;

public enum Role {
    DOCTOR,
    PATIENT;

    public static final String MESSAGE_ROLE_CONSTRAINTS = "Role should only be 'Doctor' or 'Patient'";

    public static boolean isValidRole(String role) {
        return Arrays.stream(Role.values()).anyMatch(modelRole -> modelRole.toString().equals(role.toUpperCase()));
    }
}
