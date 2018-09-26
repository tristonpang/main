package seedu.address.model.person;

public enum Role {
    DOCTOR, 
    PATIENT;
    
    public static final String MESSAGE_ROLE_CONSTRAINTS = "Roles should only [Patient]/[Doctor]" + 
            " and it should not be blank";
}
