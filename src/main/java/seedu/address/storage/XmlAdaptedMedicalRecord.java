package seedu.address.storage;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.MedicalRecord;

/**
 * JAXB-friendly adapted version of the MedicalRecord.
 */
@XmlRootElement(name = "medicalRecords")
public class XmlAdaptedMedicalRecord {

    @XmlValue
    private String medicalRecord;

    /**
     * Constructs an XmlAdaptedMedicalRecord.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedicalRecord() {}

    /**
     * Constructs an {@code XmlAdaptedMedicalRecord} with the given medical record details.
     */
    public XmlAdaptedMedicalRecord(String medicalRecord) {
        String value = medicalRecord;
        this.medicalRecord = value;
    }

    /**
     * Converts a given medicalRecord into this class for JAXB use.
     *
     * @param medicalRecord future changes to this will not affect the created
     */
    public XmlAdaptedMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord.toString();
    }

    /**
     * Converts this jaxb-friendly adapted medicalRecord object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public String toModelType() throws IllegalValueException {
        if (!new MedicalRecord(medicalRecord).isValidPreviousMedicalRecord()) {
            throw new IllegalValueException("Invalid Medical Record");
        }
        return medicalRecord;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedicalRecord)) {
            return false;
        }

        return medicalRecord.equals(((XmlAdaptedMedicalRecord) other).medicalRecord);
    }
}
