package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MedicalRecordTest {

    @Test
    public void equals() {

        MedicalRecord medicalRecord = new MedicalRecord("12.12.2018, Diagnosis: flu, Treatment: tamiflu, Comments: -");

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
        MedicalRecord differentMedicalRecord = new MedicalRecord("13.13.2018 , Diagnosis: cough,"
                + " Treatment: dextromethorphan, Comments: -");
        assertFalse(medicalRecord.equals(differentMedicalRecord));
    }
}
