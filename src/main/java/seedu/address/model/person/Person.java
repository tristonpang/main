package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedPerson;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Nric nric;

    // Common Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private Appointment appointment = new Appointment("");
    private ArrayList<Appointment> appointmentList = new ArrayList<>();

    /**
     * Every field must be present and not null.
     * An empty {@code Appointment} will be created by default.
     */
    public Person(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, nric, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Person(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                  Appointment appointment) {
        requireAllNonNull(name, phone, email, address, tags, nric, appointment);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.appointment = appointment;
        this.tags.addAll(tags);
        appointmentList.add(appointment);
    }

    public Person(Name name, Nric nric, Phone phone, Email email, Address address,
                  Set<Tag> tags, ArrayList<Appointment> appointmentList) {
        requireAllNonNull(name, phone, email, address, tags, appointmentList);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.appointmentList = appointmentList;
        this.tags.addAll(tags);

        // Set appointment to be the last scheduled appointment
        if (!this.appointmentList.isEmpty()) {
            appointment = this.appointmentList.get(this.appointmentList.size() - 1);
        }
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Nric getNric() {
        return nric;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public ArrayList<Appointment> getAppointmentList() {
        return appointmentList;
    }
    /**
     * Empties out the AppointmentList to assist junit testing.
     * This prevents appointments collected from different tests to clash.
     */
    public void clearAppointmentList() {
        appointmentList = new ArrayList<>();
    }

    /**
     * Checks for clash with the appointment that is to be scheduled.
     */
    public boolean hasClash(Appointment newAppointment) {
        return AppointmentManager.isClash(appointmentList, newAppointment);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getNric().equals(this.getNric());
    }

    /**
     * Returns an Xml version of this Person instance.
     * This method is to be overwritten by {@code Patient} and {@code Doctor} class.
     */
    public XmlAdaptedPerson toXmlVersion(Person source) {
        return XmlAdaptedPerson.adaptToXml(source);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getNric().equals(getNric())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, nric, tags, appointmentList);
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
                .append(" Latest Appointment: ")
                .append(getAppointment())
                .append(" Appointment List: ")
                .append(getAppointmentList())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
