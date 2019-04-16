package seedu.address.model.doctor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MedicalDepartmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicalDepartment(null));
    }

    @Test
    public void constructor_invalidMedicalDept_throwsIllegalArgumentException() {
        String invalidMedicalDept = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicalDepartment(invalidMedicalDept));
    }

    @Test
    public void isValidMedDept() {
        // null medical department
        Assert.assertThrows(NullPointerException.class, () -> MedicalDepartment.isValidMedDept(null));

        // invalid department
        assertFalse(MedicalDepartment.isValidMedDept("")); // empty string
        assertFalse(MedicalDepartment.isValidMedDept(" ")); // spaces only
        assertFalse(MedicalDepartment.isValidMedDept("^")); // only non-alphanumeric characters
        assertFalse(MedicalDepartment.isValidMedDept("ophthalmology*")); // contains non-alphanumeric characters
        assertFalse(MedicalDepartment.isValidMedDept("123456789")); // only numeric characters
        assertFalse(MedicalDepartment.isValidMedDept("heart123")); // alphanumeric characters

        // valid medical department
        assertTrue(MedicalDepartment.isValidMedDept("cardiology")); // alphabets only
        assertTrue(MedicalDepartment.isValidMedDept("anatomy and neurobiology  ")); // contains whitespace
        assertTrue(MedicalDepartment.isValidMedDept("Neurology")); // with capital letters
        assertTrue(MedicalDepartment.isValidMedDept("NEUROLOGY")); // all capital letters
        assertTrue(MedicalDepartment.isValidMedDept("Pulmonary Diseases and Critical Care Medicine")); // long dept name
    }
}
