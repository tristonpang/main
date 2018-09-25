package seedu.address.model.department;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class MedicalDepartment {
    
    public static final String MESSAGE_DEPTNAME_CONSTRAINTS =
            "Medical Department should only contain alphabetic characters and spaces, and it should not be blank";
    public static final String DEPTNAME_VALIDATION_REGEX = "[a-zA-Z]+";
    
    public final String deptName;

    /**
     * Constructs a {@code MedicalDepartment}.
     *
     * @param deptName A valid medical department.
     */
    public MedicalDepartment(String deptName) {
        requireNonNull(deptName);
        checkArgument(isValidMedDept(deptName), MESSAGE_DEPTNAME_CONSTRAINTS);
        this.deptName = deptName.substring(0, 1).toUpperCase() + deptName.toLowerCase().substring(1);
    }

    /**
     * Checks if the string is a valid medical department name.
     * 
     * @param medDept Medical Department name.
     * @return True if it is a valid name as a medical department.
     */
    public static boolean isValidMedDept(String medDept) {
        return medDept.matches(DEPTNAME_VALIDATION_REGEX);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } 
        if (obj instanceof MedicalDepartment){
            MedicalDepartment department = (MedicalDepartment) obj;
            return (department.deptName.equals(this.deptName));
        } else {
            return false;
        }
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '['  + deptName + ']';
    }
}

// add r/doctor n/john e/john@john.com p/31312312 a/john street md/oncology