package seedu.address.model.doctor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentManager;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
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
    public static final String IS_AVAILABLE = "AVAILABLE";
    public static final String NOT_AVAILABLE = "BUSY";

    private final MedicalDepartment dept;

    /**
     * Creates a new Doctor object based on given details.
     * All field must be present and non-null.
     */
    public Doctor(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                  MedicalDepartment dept) {
        super(name, nric, phone, email, address, tags);
        requireAllNonNull(dept);
        this.dept = dept;
    }

    /**
     * Creates a new Doctor object based on given details.
     * All field must be present and non-null.
     */
    public Doctor(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                  ArrayList<Appointment> appointmentList, MedicalDepartment modelMedicalDept) {
        super(name, nric, phone, email, address, tags, appointmentList);
        requireAllNonNull(modelMedicalDept);
        this.dept = modelMedicalDept;
    }

    /**
     * Creates a new Doctor object based on given details.
     * All field must be present and non-null.
     */
    public Doctor(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                  Appointment appointment, MedicalDepartment modelMedicalDept) {
        super(name, nric, phone, email, address, tags, appointment);
        requireAllNonNull(modelMedicalDept);
        this.dept = modelMedicalDept;
    }

    /**
     * Returns Medical Department of this doctor.
     */
    public MedicalDepartment getMedicalDepartment() {
        return this.dept;
    }

    /**
     * Returns the availability status of the Doctor, whether free or busy at the moment.
     */
    public String currentAvailStatus() {
        if (AppointmentManager.isAnyAppointmentOngoing(this.getAppointmentList())) {
            return NOT_AVAILABLE;
        }
        return IS_AVAILABLE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Doctor) {
            Doctor otherDoctor = (Doctor) obj;
            return super.equals(otherDoctor) && otherDoctor.getMedicalDepartment().equals(getMedicalDepartment());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getNric(), getPhone(), getEmail(), getAddress(), dept, getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" NRIC: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Medical Department: ")
                .append(getMedicalDepartment())
                .append(" Latest Appointment: ")
                .append(getAppointment())
                .append(" AppointmentList: ")
                .append(getAppointmentList())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
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
