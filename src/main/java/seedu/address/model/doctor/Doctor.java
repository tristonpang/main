package seedu.address.model.doctor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedDoctor;
import seedu.address.storage.XmlAdaptedPerson;

/**
 * Represents a Doctor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor extends Person {
    private final MedicalDepartment dept;

    /**
     * Creates a new Doctor object based on given details.
     * All field must be present and non-null.
     */
    public Doctor(Name name, Phone phone, Email email, Address address, Set<Tag> tags, MedicalDepartment dept) {
        super(name, phone, email, address, tags);
        requireAllNonNull(dept);
        this.dept = dept;
    }

    /**
     * Creates a new Doctor object based on given details.
     * All field must be present and non-null.
     */
    public Doctor(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Appointment appointment,
                  MedicalDepartment modelMedicalDept) {
        super(name, phone, email, address, tags, appointment);
        requireAllNonNull(modelMedicalDept);
        this.dept = modelMedicalDept;
    }

    /**
     * Returns Medical Department of this doctor.
     */
    public MedicalDepartment getMedicalDepartment () {
        return this.dept;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Doctor) {
            Doctor otherDoctor = (Doctor) obj;
            return (super.equals(otherDoctor)) && (otherDoctor.dept.equals(this.dept));
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString() + (" Department: " + this.dept) + (" Appointments: " + this.getAppointment());
    }

    /**
     * Returns a Xml Adapted version of this doctor object.
     * Overwrites the method in the super class, {@code Person}.
     * Asserts that this person is an instance of a Doctor.
     */
    public XmlAdaptedPerson toXmlVersion(Person source) {
        assert source instanceof Doctor;
        return XmlAdaptedDoctor.adaptToXml((Doctor) source);
    }
}
