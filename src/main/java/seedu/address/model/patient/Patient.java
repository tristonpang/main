package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedPatient;
import seedu.address.storage.XmlAdaptedPerson;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private final Nric nric;
    private MedicalRecord medicalRecord;

    /**
     * Creates a new Patient object based on given details.
     * An empty {@code MedicalRecord} will be created by default for the patient.
     * All field must be present and non-null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Nric nric) {
        super(name, phone, email, address, tags);
        requireAllNonNull(nric);
        this.nric = nric;
        this.medicalRecord = new MedicalRecord("");
    }

    /**
     * Creates a new Patient object based on given details.
     * All field must be present and non-null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Appointment appointment,
                   Nric nric, MedicalRecord medicalRecord) {
        super(name, phone, email, address, tags, appointment);
        this.nric = nric;
        this.medicalRecord = medicalRecord;
    }

    public Nric getNric() {
        return nric;
    }

    public MedicalRecord getMedicalRecord() {
        return this.medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = requireNonNull(medicalRecord);
    }

    public boolean isSamePerson(Patient otherPerson) {
        return super.isSamePerson(otherPerson) && (this.nric.equals(otherPerson));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Patient) {
            Patient otherPatient = (Patient) obj;
            return (super.equals(otherPatient))
                    && (otherPatient.nric.equals(this.nric));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, medicalRecord, tags);
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
                .append(" Medical Records: ")
                .append(getMedicalRecord())
                .append(" Appointments: ")
                .append(getAppointment())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a Xml Adapted version of this patient object.
     * Overwrites the method in the super class, {@code Person}.
     * Asserts that this person is an instance of a Patient.
     */
    public XmlAdaptedPerson toXmlVersion(Person source) {
        assert source instanceof Patient;
        return XmlAdaptedPatient.adaptToXml((Patient) source);
    }
}
