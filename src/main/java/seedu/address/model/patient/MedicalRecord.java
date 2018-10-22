package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
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
        checkArgument(isValidMedicalRecord(medicalRecord), MESSAGE_MEDICAL_RECORD_CONSTRAINTS);
        requireNonNull(medicalRecord);
        this.value = medicalRecord;
        List<String> valueList = Arrays.asList(value.split(","));
        this.date = new Date(valueList.get(0));
        this.diagnosis = new Diagnosis(valueList.get(1).substring(12));
        this.treatment = new Treatment(valueList.get(2).substring(12));
        this.comments = valueList.get(3).substring(11);
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

    public static boolean isValidMedicalRecord(String medicalRecord) {
        return medicalRecord != null;
    }

    public String getFailureReason() {
        assert(!isValid());
        String reason;
        if (!hasValidDate()) {
            reason = date.getFailureReason();
        } else if (!hasValidDiagnosis()) {
            reason = diagnosis.getFailureReason();
        } else {
            reason = treatment.getFailureReason();
        }
        return reason;
    }
    public boolean isValid() {
        return hasValidDate()
                && hasValidDiagnosis()
                && hasValidTreatment();
    }


    public boolean hasValidDate() {
        return this.date.isValid();
    }

    public boolean hasValidDiagnosis() {
        return this.diagnosis.isValid();
    }

    public boolean hasValidTreatment() {
        return this.treatment.isValid();
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
