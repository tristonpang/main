package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

public class XmlAdaptedDoctor extends XmlAdaptedPerson {

    public XmlAdaptedDoctor(Doctor source) {
        super(source);
        medicalDepartment = source.getMedicalDepartment().deptName;
    }

    public static XmlAdaptedDoctor adaptToXml(Doctor source) {
        return new XmlAdaptedDoctor(source);
    }

    public static Doctor convertToDoctorModelType (Person source,String medicalDept) throws IllegalValueException {
        Person modelPerson = source;
        if (medicalDept == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicalDepartment.class.getSimpleName()));
        }
        if(!MedicalDepartment.isValidMedDept(medicalDept)) {
            throw new IllegalValueException(MedicalDepartment.MESSAGE_DEPTNAME_CONSTRAINTS);
        }
        final MedicalDepartment modelMedicalDept = new MedicalDepartment(medicalDept);
        return new Doctor(modelPerson.getName(), modelPerson.getPhone(), modelPerson.getEmail(),
                modelPerson.getAddress(), modelPerson.getTags(), modelPerson.getAppointment(), modelMedicalDept);
    }
}
