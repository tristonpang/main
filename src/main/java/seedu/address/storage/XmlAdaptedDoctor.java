package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of the Doctor.
 */
public class XmlAdaptedDoctor extends XmlAdaptedPerson {
    
    public XmlAdaptedDoctor(Doctor source){
        super(source);
        medicalDepartment = source.getMedicalDepartment().deptName;
        appointments = source.getAppointments().stream()
                .map(XmlAdaptedAppointment::new)
                .collect(Collectors.toList());
    }
    
    public static XmlAdaptedDoctor adaptToXml(Doctor source){
        return new XmlAdaptedDoctor(source);
    }
    
    public static Doctor convertToDoctorModelType(Person source, String medicalDepart,
                                                  List<XmlAdaptedAppointment> appts) throws IllegalValueException {
        Person person = source;
        final List<String> doctorAppointments = new ArrayList<>();
        for (XmlAdaptedAppointment appointment : appts) {
            doctorAppointments.add(appointment.toModelType());
        }

        if (medicalDepart == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicalDepartment.class.getSimpleName()));
        }
        if (!MedicalDepartment.isValidMedDept(medicalDepart)) {
            throw new IllegalValueException(MedicalDepartment.MESSAGE_DEPTNAME_CONSTRAINTS);
        }
        
        final MedicalDepartment modelDepartment = new MedicalDepartment(medicalDepart);
        
        final Set<String> modelAppointments = new HashSet<>(doctorAppointments);
        return new Doctor(person, modelDepartment, modelAppointments);
    }
}
