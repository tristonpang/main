package seedu.address.storage;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of the Patient.
 */
public class XmlAdaptedPatient extends XmlAdaptedPerson {

    /**
     * Constructs an {@code XmlAdaptedPatient} with the given patient details.
     */
    public XmlAdaptedPatient(Patient source) {
        super(source);
        nric = source.getNric().code;
        medicalRecord = source.getMedicalRecord().value;
    }

    /**
     * Constructs an {@code XmlAdaptedPatient} with the given person details.
     */
    public XmlAdaptedPatient(String name, String nric, String phone, String email, String address,
                             String medicalRecord, List<XmlAdaptedTag> tags, String appointment) {
        super(name, nric, phone, email, address, tags, appointment);
        this.medicalRecord = medicalRecord;
        this.role = "Patient";
    }

    /**
     * Overwritten method to convert patient into this class for JAXB use.
     *
     * @param source Patient object.
     * @return Xml Adapted version of the given Patient object.
     */
    public static XmlAdaptedPatient adaptToXml(Patient source) {
        return new XmlAdaptedPatient(source);
    }

    /**
     * Converts this jaxb-friendly adapted patient object into the model's Patient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public static Patient convertToPatientModelType(Person source, String medicalRecords) throws IllegalValueException {
        Person person = source;

        if (medicalRecords == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicalRecord.class.getSimpleName()));
        }
        if (!MedicalRecord.isValidMedicalRecord(medicalRecords)) {
            throw new IllegalValueException(MedicalRecord.MESSAGE_MEDICAL_RECORD_CONSTRAINTS);
        }

        final MedicalRecord modelMedicalRecords = new MedicalRecord(medicalRecords);

        return new Patient(person.getName(), person.getNric(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), person.getAppointment(), modelMedicalRecords);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPatient)) {
            return false;
        }

        XmlAdaptedPatient otherPatient = (XmlAdaptedPatient) other;
        return Objects.equals(name, otherPatient.name)
                && Objects.equals(nric, otherPatient.nric)
                && Objects.equals(phone, otherPatient.phone)
                && Objects.equals(email, otherPatient.email)
                && Objects.equals(address, otherPatient.address)
                && tagged.equals(otherPatient.tagged)
                && Objects.equals(medicalRecord, otherPatient.medicalRecord);
    }
}
