package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import java.util.Objects;
import java.util.Set;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalRecord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedPatient;
import seedu.address.storage.XmlAdaptedPerson;

public class Patient extends Person {
    private final NRIC nric;
    private MedicalRecord medicalRecord;

    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, NRIC nric) {
        super(name, phone, email, address, tags);
        this.nric = nric;
        this.medicalRecord = new MedicalRecord("");
    }

    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Appointment appointment,
                   NRIC nric, MedicalRecord medicalRecord) {
        super(name, phone, email, address, tags, appointment);
        this.nric = nric;
        this.medicalRecord = medicalRecord;
    }

    public NRIC getNric() {
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
            Patient otherDoctor = (Patient) obj;
            return (super.equals(otherDoctor))
                    && (otherDoctor.nric.equals(this.nric));
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
                .append(" Medical Records: " )
                .append(getMedicalRecord())
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public XmlAdaptedPerson toXmlVersion(Person source) {
        return XmlAdaptedPatient.adaptToXml((Patient) source);
    }
}
