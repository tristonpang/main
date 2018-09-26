package seedu.address.model.patient;

import java.util.Set;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalRecord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class Patient extends Person {
    private final NRIC nric;

    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, NRIC nric) {
        super(name, phone, email, address, new MedicalRecord(""), new Appointment(""), tags);
        this.nric = nric;
    }

    public NRIC getNric() {
        return nric;
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
    public String toString() {
        return super.toString() + (" NRIC: " + this.nric);
    }
}
