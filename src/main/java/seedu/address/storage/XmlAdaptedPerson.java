package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    protected String role;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String nric;
    @XmlElement(required = true)
    protected String phone;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String address;
    @XmlElement
    protected List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    protected String medicalRecord;
    @XmlElement
    protected List<XmlAdaptedMedicalRecord> medicalRecordLibrary = new ArrayList<>();

    @XmlElement
    protected String medicalDepartment;

    @XmlElement(required = true)
    protected String appointment;
    @XmlElement(required = true)
    protected List<XmlAdaptedAppointment> appointmentList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String nric, String phone, String email, String address,
                            List<XmlAdaptedTag> tagged, String appointment) {
        this.name = name;
        this.phone = phone;
        this.nric = nric;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.appointment = appointment;
        appointmentList.add(new XmlAdaptedAppointment(appointment));
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        role = source.getClass().getSimpleName();
        name = source.getName().fullName;
        nric = source.getNric().code;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        appointment = source.getAppointment().value;
        appointmentList = source.getAppointmentList().stream()
                .map(XmlAdaptedAppointment::new)
                .collect(Collectors.toList());
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts given person into this class for JAXB use.
     * This method is to be overwritten by {@code XmlAdaptedPatient} and {@code XmlAdaptedDoctor} class.
     */
    public static XmlAdaptedPerson adaptToXml(Person source) {
        return new XmlAdaptedPerson(source);
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_ROLE_CONSTRAINTS);
        }
        final Role modelRole = Role.valueOf(role.toUpperCase());

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        if (appointment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Appointment.class.getSimpleName()));
        }

        final ArrayList<Appointment> modelApptList = new ArrayList<>();
        for (XmlAdaptedAppointment appt : this.appointmentList) {
            try {
                modelApptList.add(new Appointment(appt.toModelType()));
            } catch (IllegalValueException e) {
                throw e;
            }
        }

        final ArrayList<MedicalRecord> modelMedicalRecordLibrary = new ArrayList<>();
        for (XmlAdaptedMedicalRecord medicalRecord : this.medicalRecordLibrary) {
            try {
                modelMedicalRecordLibrary.add(new MedicalRecord(medicalRecord.toModelType()));
            } catch (IllegalValueException e) {
                throw e;
            }
        }
        final Address modelAddress = new Address(address);
        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (modelRole.equals(Role.DOCTOR)) {
            return XmlAdaptedDoctor.convertToDoctorModelType(new Person(modelName, modelNric, modelPhone, modelEmail,
                    modelAddress, modelTags, modelApptList), medicalDepartment);
        } else {
            assert modelRole.equals(Role.PATIENT); // person must be a patient if he/she is not a doctor.
            return XmlAdaptedPatient.convertToPatientModelType(new Person(modelName, modelNric, modelPhone, modelEmail,
                    modelAddress, modelTags, modelApptList), medicalRecord, modelMedicalRecordLibrary);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(nric, otherPerson.nric)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
