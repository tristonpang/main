package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MedicalRecordTest {

    @Test
    public void equals() {

        MedicalRecord medicalRecord = new MedicalRecord("Hello");

        // same object -> returns true
        assertTrue(medicalRecord.equals(medicalRecord));

        // same values -> returns true
        MedicalRecord medicalRecordCopy = new MedicalRecord(medicalRecord.value);
        assertTrue(medicalRecord.equals(medicalRecordCopy));

        // different types -> returns false
        assertFalse(medicalRecord.equals(1));

        // null -> returns false
        assertFalse(medicalRecord.equals(null));

        // different person -> returns false
        MedicalRecord differentMedicalRecord = new MedicalRecord("Bye");
        assertFalse(medicalRecord.equals(differentMedicalRecord));
    }
}
