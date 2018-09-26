package seedu.address.storage;

import java.util.stream.Collectors;

import seedu.address.model.patient.Patient;

public class XmlAdaptedPatient extends XmlAdaptedPerson {
    public XmlAdaptedPatient(Patient source) {
        super(source);
        nric = source.getNric().code;
        appointments = source.getAppointments().stream()
                .map(XmlAdaptedAppointment::new)
                .collect(Collectors.toList());
        medicalRecords
    }
}
