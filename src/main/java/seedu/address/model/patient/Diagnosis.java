package seedu.address.model.patient;

/**
 * Represents a diagnosis in a medical record.
 */
public class Diagnosis {

    /*
     * The first character of the diagnosis must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DIAGNOSIS_VALIDATION_REGEX = "[^\\s].*";

    public static final String MESSAGE_DIAGNOSIS_CONSTRAINTS = "Diagnosis can take any values, "
            + "and should not be blank.";

    /** String representation of the diagnosis */
    private String diagnosis;

    public Diagnosis (String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * @return whether this diagnosis is a valid diagnosis.
     */
    public boolean isValid() {
        return this.diagnosis.matches(DIAGNOSIS_VALIDATION_REGEX) && !MedicalRecord.hasInvalidPrefix(this.diagnosis);
    }

    public String getInvalidReason() {
        if (!this.diagnosis.matches(DIAGNOSIS_VALIDATION_REGEX)) {
            return MESSAGE_DIAGNOSIS_CONSTRAINTS;
        } else if (MedicalRecord.hasInvalidPrefix(this.diagnosis)) {
            return MedicalRecord.MESSAGE_INVALID_PREFIX_USED;
        } else {
            return "Diagnosis is valid";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Diagnosis) {
            Diagnosis otherDiagnosis = (Diagnosis) obj;
            return diagnosis.equals(otherDiagnosis.diagnosis);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.diagnosis;
    }
}
