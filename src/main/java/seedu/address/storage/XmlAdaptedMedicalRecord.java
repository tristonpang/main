package seedu.address.storage;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.MedicalRecord;

/**
 * JAXB-friendly adapted version of the MedicalRecord.
 */
@XmlRootElement(name = "medicalRecords")
public class XmlAdaptedMedicalRecord {

    @XmlElement
    private String medicalRecord;

    @XmlElement
    private String date;

    @XmlElement
    private String diagnosis;

    @XmlElement
    private String treatment;

    @XmlElement
    private String comments;

    /**
     * Constructs an XmlAdaptedMedicalRecord.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedicalRecord() {}

    /**
     * Constructs an {@code XmlAdaptedMedicalRecord} with the given medical record details.
     */
    public XmlAdaptedMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
        List<String> valueList = Arrays.asList(medicalRecord.split(","));
        this.date = valueList.get(0);
        this.diagnosis = valueList.get(1).substring(12);
        this.treatment = valueList.get(2).substring(12);
        this.comments = valueList.get(3).substring(11);
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
        // TODO: checker
        //if (!new MedicalRecord(medicalRecord).isValidMedicalRecord()) {
        //    throw new IllegalValueException("Invalid Medical Record");
        //}
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
