package seedu.address.storage;

import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of the Doctor.
 */
public class XmlAdaptedDoctor extends XmlAdaptedPerson {

    /**
     * Constructs an XmlAdaptedDoctor.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDoctor() {}

    /**
     * Constructs an {@code XmlAdaptedDoctor} with the given doctor details.
     */
    public XmlAdaptedDoctor(Doctor source) {
        super(source);
        medicalDepartment = source.getMedicalDepartment().deptName;
    }

    /**
     * Constructs an {@code XmlAdaptedPatient} with the given person details.
     */
    public XmlAdaptedDoctor(String name, String phone, String email, String address,
                            List<XmlAdaptedTag> tags, String appointment, String medicalDepartment) {
        super(name, phone, email, address, tags, appointment);
        this.medicalDepartment = medicalDepartment;
        this.role = "Doctor";
    }

    /**
     * Overwritten method to convert doctor into this class for JAXB use.
     *
     * @param source Doctor object.
     * @return Xml Adapted version of the given Doctor object.
     */
    public static XmlAdaptedDoctor adaptToXml(Doctor source) {
        return new XmlAdaptedDoctor(source);
    }

    /**
     * Converts this jaxb-friendly adapted doctor object into the model's Doctor object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public static Doctor convertToDoctorModelType (Person source, String medicalDept) throws IllegalValueException {
        Person modelPerson = source;
        if (medicalDept == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicalDepartment.class.getSimpleName()));
        }
        if (!MedicalDepartment.isValidMedDept(medicalDept)) {
            throw new IllegalValueException(MedicalDepartment.MESSAGE_DEPTNAME_CONSTRAINTS);
        }
        final MedicalDepartment modelMedicalDept = new MedicalDepartment(medicalDept);
        return new Doctor(modelPerson.getName(), modelPerson.getPhone(), modelPerson.getEmail(),
                modelPerson.getAddress(), modelPerson.getTags(), modelPerson.getAppointmentList(), modelMedicalDept);
    }
}
