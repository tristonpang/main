package seedu.address.model.patient;

/**
 * Represents a diagnosis in a medical record.
 */
public class Treatment {

    /*
     * The first character of the treatment must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TREATMENT_VALIDATION_REGEX = "[^\\s].*";

    public static final String MESSAGE_TREATMENT_CONSTRAINTS = "Treatments can take any values, "
            + "and should not be blank.";

    /** String representation of the treatment */
    private String treatment;

    public Treatment (String treatment) {
        this.treatment = treatment;
    }

    /**
     *
     * @return whether this treatment is a valid treatment.
     */
    public boolean isValid() {
        return this.treatment.matches(TREATMENT_VALIDATION_REGEX) && !MedicalRecord.hasInvalidPrefix(this.treatment);
    }

    public String getInvalidReason() {
        if (!this.treatment.matches(TREATMENT_VALIDATION_REGEX)) {
            return MESSAGE_TREATMENT_CONSTRAINTS;
        } else if (MedicalRecord.hasInvalidPrefix(this.treatment)) {
            return MedicalRecord.MESSAGE_INVALID_PREFIX_USED;
        } else {
            return "Treatment is valid.";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Treatment) {
            Treatment otherTreatment = (Treatment) obj;
            return treatment.equals(otherTreatment.treatment);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.treatment;
    }
}
