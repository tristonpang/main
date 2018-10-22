package seedu.address.model.patient;

/**
 * Represents a diagnosis in a medical record.
 */
public class Treatment {

    /** String representation of the treatment */
    private String treatment;

    /*
     * Treatment should be alphanumeric
     */
    public static final String TREATMENT_VALIDATION_REGEX = "\\p{Alnum}+";

    public static final String MESSAGE_TREATMENT_CONSTRAINTS = "Treatment should be alphanumeric.";

    public Treatment (String treatment) {
        this.treatment = treatment;
    }

    /**
     *
     * @return whether this treatment is a valid treatment.
     */
    public boolean isValid() {
        return this.treatment.matches(TREATMENT_VALIDATION_REGEX);
    }

    public String getFailureReason() {
        assert(!isValid());
        return MESSAGE_TREATMENT_CONSTRAINTS;
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
