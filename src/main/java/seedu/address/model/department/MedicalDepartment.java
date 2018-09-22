package seedu.address.model.department;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class MedicalDepartment {
    
    public static final String MESSAGE_DEPTNAME_CONSTRAINTS = "Medical Departments should be alphabetical";
    public static final String DEPTNAME_VALIDATION_REGEX = "\\p{Alpha}";
    
    public final String deptName;

    /**
     * Constructs a {@code MedicalDepartment}.
     *
     * @param deptName A valid medical department.
     */
    public MedicalDepartment(String deptName) {
        requireNonNull(deptName);
        checkArgument(isValidMedDept(deptName), MESSAGE_DEPTNAME_CONSTRAINTS);
        this.deptName = deptName;
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
        return '[' + "Department: " + deptName + ']';
    }
}
