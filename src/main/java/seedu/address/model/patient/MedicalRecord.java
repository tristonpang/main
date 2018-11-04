package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Date;
import seedu.address.model.person.DisplayableAttribute;

/**
 * Represents a Person's Medical Record in the address book.
 */

public class MedicalRecord extends DisplayableAttribute {

    public static final String MESSAGE_MEDICAL_RECORD_CONSTRAINTS =
            "Person medical record can take any values, and it should not be blank";

    public static final String MESSAGE_INVALID_PREFIX_USED = "Only prefixes dg/, tr/ and c/ should be used for "
            + "diagnosis, treatment, and comments respectively. Diagnosis:, Treatment: and Comments: are invalid.";

    public static final MedicalRecord DEFAULT_MEDICAL_RECORD = new MedicalRecord("", "", "", "");

    public final String value;
    public final Date date;
    public final Diagnosis diagnosis;
    public final Treatment treatment;
    public final String comments;

    /**
     * Validates given medical record. Used during junit testing.
     *
     */
    public MedicalRecord(String medicalRecord) {
        requireNonNull(medicalRecord);
        this.value = medicalRecord;
        List<String> valueList = Arrays.asList(value.split("(, Diagnosis: |, Treatment: |, Comments: )"));
        this.date = new Date(valueList.get(0));
        this.diagnosis = new Diagnosis(valueList.get(1));
        this.treatment = new Treatment(valueList.get(2));
        this.comments = valueList.get(3);
    }

    /**
     * Validates given descriptions. Used when taking in inputs from parser.
     */
    public MedicalRecord(String date, String diagnosis, String treatment, String comments) {
        requireAllNonNull(date, diagnosis, treatment);
        String actualComments = comments;
        if (actualComments == null || actualComments.equals("")) {
            actualComments = "-";
        }
        this.date = new Date(date);
        this.diagnosis = new Diagnosis(diagnosis);
        this.treatment = new Treatment(treatment);
        this.comments = actualComments;
        this.value = date + "," + " Diagnosis: " + diagnosis + ", Treatment: " + treatment + ", Comments: "
                + actualComments;
    }

    public boolean isValidMedicalRecord() {
        return this.value != null;
    }

    /**
     * Checks if the user has input invalid prefix as a value for a field.
     * @param value the value of a field.
     * @return true if an invalid prefix is used in a value for a field. Otherwise return false.
     */
    public static boolean hasInvalidPrefix(String value) {
        return value.contains("Diagnosis:")
                || value.contains("Treatment:")
                || value.contains("Comments:");
    }

    public String getFailureReason() {
        if (!hasValidDate()) {
            return Date.getFailureReason(date.toString());
        } else if (!hasValidDiagnosis()) {
            return diagnosis.getFailureReason();
        } else if (!hasValidTreatment()) {
            return treatment.getFailureReason();
        } else if (!hasValidComments()) {
            return MESSAGE_INVALID_PREFIX_USED;
        } else {
            return "Medical record is valid.";
        }
    }

    public boolean isValid() {
        return hasValidDate()
                && hasValidDiagnosis()
                && hasValidTreatment()
                && hasValidComments();
    }


    public boolean hasValidDate() {
        return Date.isValidDate(this.date.toString());
    }

    public boolean hasValidDiagnosis() {
        return this.diagnosis.isValid();
    }

    public boolean hasValidTreatment() {
        return this.treatment.isValid();
    }

    public boolean hasValidComments() {
        return !hasInvalidPrefix(comments);
    }

    public String getDate() {
        return this.date.toString();
    }

    public String getDiagnosis() {
        return this.diagnosis.toString();
    }

    public String getTreatment() {
        return this.treatment.toString();
    }

    public String getComments() {
        return this.comments;
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
