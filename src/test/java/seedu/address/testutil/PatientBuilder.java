package seedu.address.testutil;

import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder extends PersonBuilder {

    public static final String DEFAULT_MEDICAL_RECORD = ", Diagnosis: , Treatment: , Comments: -";

    private MedicalRecord medicalRecord;

    public PatientBuilder() {
        super();
        medicalRecord = new MedicalRecord(DEFAULT_MEDICAL_RECORD);
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        super(patientToCopy);
        medicalRecord = patientToCopy.getMedicalRecord();
    }

    @Override
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    @Override
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
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
        appointmentList = SampleDataUtil.getAppointmentsList(appointment);
        return this;
    }

    @Override
    public PatientBuilder withAppointments(String ... appointments) {
        appointmentList = SampleDataUtil.getAppointmentsList(appointments);
        return this;
    }

    /**
     * Sets the {@code MedicalRecord} of the {@code Patient} that we are building.
     */
    public PatientBuilder withMedicalRecord(String medicalRecord) {
        this.medicalRecord = new MedicalRecord(medicalRecord);
        return this;
    }

    @Override
    public Patient build() {
        if (appointmentList != null) {
            return new Patient(name, nric, phone, email, address, tags, appointmentList, medicalRecord);
        }
        return new Patient(name, nric, phone, email, address, tags, medicalRecord);
    }
}
