package seedu.address.testutil;

import java.util.HashSet;

import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.NRIC;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalRecord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Doctor objects.
 */
public class DoctorBuilder extends PersonBuilder {

    public static final String DEFAULT_MEDICAL_DEPARTMENT = "Cardiology";

    private MedicalDepartment medicalDepartment;

    public DoctorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        medicalDepartment = new MedicalDepartment(DEFAULT_MEDICAL_DEPARTMENT);
        appointment = new Appointment(DEFAULT_APPOINTMENT);
        tags = new HashSet<>();
    }


    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        name = doctorToCopy.getName();
        phone = doctorToCopy.getPhone();
        email = doctorToCopy.getEmail();
        address = doctorToCopy.getAddress();
        medicalDepartment = doctorToCopy.getMedicalDepartment();
        tags = new HashSet<>(doctorToCopy.getTags());
        appointment = doctorToCopy.getAppointment();
    }

    /**
     * Sets the {@code MedicalDepartment} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withMedicalDepartment(String medicalDepartment) {
        this.medicalDepartment = new MedicalDepartment(medicalDepartment);
        return this;
    }

    @Override
    public DoctorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    @Override
    public DoctorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    @Override
    public DoctorBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    @Override
    public DoctorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    @Override
    public DoctorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    @Override
    public DoctorBuilder withAppointment(String appointment) {
        this.appointment = new Appointment(appointment);
        return this;
    }

    @Override
    public Doctor build() {
        return new Doctor(name, phone, email, address, tags, appointment, medicalDepartment);
    }

}
