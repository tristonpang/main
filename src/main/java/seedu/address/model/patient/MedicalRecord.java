package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person's Medical Record in the address book.
 */

public class MedicalRecord {

    public static final String MESSAGE_MEDICAL_RECORD_CONSTRAINTS =
            "Person medical record can take any values, and it should not be blank";

    public final String value;

    /**
     * Validates given medical record. Used during junit testing.
     *
     */
    public MedicalRecord(String medicalRecord) {
        requireNonNull(medicalRecord);
        checkArgument(isValidMedicalRecord(medicalRecord), MESSAGE_MEDICAL_RECORD_CONSTRAINTS);
        this.value = medicalRecord;
    }

    /**
     * Validates given descriptions. Used when taking in inputs from parser.
     */
    public MedicalRecord(String date, String diagnosis, String treatment, String comments) {
        requireAllNonNull(date, diagnosis, treatment);
        if (comments == null || comments == "") {
            comments = "-";
        }
        this.value = date + "," + " Diagnosis: " + diagnosis + ", Treatment: " + treatment + ", Comments: "
                + comments;
    }

    public static boolean isValidMedicalRecord(String medicalRecord) {
        return medicalRecord != null;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalRecord // instanceof handles nulls
                && this.value.equals(((MedicalRecord) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
