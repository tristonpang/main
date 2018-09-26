package seedu.address.storage;

import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.NRIC;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.MedicalRecord;
import seedu.address.model.person.Person;

public class XmlAdaptedPatient extends XmlAdaptedPerson {

    public XmlAdaptedPatient(Patient source) {
        super(source);
        nric = source.getNric().code;
        medicalRecord = source.getMedicalRecord().value;
    }
    public static XmlAdaptedPatient adaptToXml(Patient source){
        return new XmlAdaptedPatient(source);
    }

    public static Patient convertToPatientModelType(Person source, String nric, String medicalRecords) throws IllegalValueException {
        Person person = source;

        if (medicalRecords == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicalRecord.class.getSimpleName()));
        }
        if (!MedicalRecord.isValidMedicalRecord(medicalRecords)) {
            throw new IllegalValueException(MedicalRecord.MESSAGE_MEDICAL_RECORD_CONSTRAINTS);
        }

        if(nric == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, NRIC.class.getSimpleName())));
        }
        if(!NRIC.isValidNric((nric))) {
            throw new IllegalValueException(NRIC.MESSAGE_NRIC_CONSTRAINTS);
        }

        final MedicalRecord modelMedicalRecords = new MedicalRecord(medicalRecords);
        final NRIC modelNric = new NRIC(nric);

        return new Patient(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getTags(), person.getAppointment(), modelNric, modelMedicalRecords);
    }
}
