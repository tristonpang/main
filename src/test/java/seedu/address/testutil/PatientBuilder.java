package seedu.address.testutil;

import java.util.HashSet;

import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder extends PersonBuilder {

    public static final String DEFAULT_MEDICAL_RECORD = "";
    public static final String DEFAULT_NRIC = "S1234567A";

    private MedicalRecord medicalRecord;
    private Nric nric;

    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        medicalRecord = new MedicalRecord(DEFAULT_MEDICAL_RECORD);
        nric = new Nric(DEFAULT_NRIC);
        appointment = new Appointment(DEFAULT_APPOINTMENT);
        tags = new HashSet<>();
    }


    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        medicalRecord = patientToCopy.getMedicalRecord();
        appointment = patientToCopy.getAppointment();
        nric = patientToCopy.getNric();
        tags = new HashSet<>(patientToCopy.getTags());
    }

    @Override
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    @Override
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    @Override
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    @Override
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    @Override
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    @Override
    public PatientBuilder withAppointment(String appointment) {
        this.appointment = new Appointment(appointment);
        return this;
    }

    /**
     * Sets the {@code MedicalRecord} of the {@code Patient} that we are building.
     */
    public PatientBuilder withMedicalRecord(String medicalRecord) {
        this.medicalRecord = new MedicalRecord(medicalRecord);
        return this;
    }

    /**
     * Sets the {@code MedicalRecord} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    @Override
    public Patient build() {
        return new Patient(name, phone, email, address, tags, appointment, nric, medicalRecord);
    }

}
