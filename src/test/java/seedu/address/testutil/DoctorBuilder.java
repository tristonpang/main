package seedu.address.testutil;

import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.MedicalDepartment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Doctor objects.
 */
public class DoctorBuilder extends PersonBuilder {

    public static final String DEFAULT_MEDICAL_DEPARTMENT = "Cardiology";

    private MedicalDepartment medicalDepartment;

    public DoctorBuilder() {
        super();
        medicalDepartment = new MedicalDepartment(DEFAULT_MEDICAL_DEPARTMENT);
    }


    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        super(doctorToCopy);
        medicalDepartment = doctorToCopy.getMedicalDepartment();
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
    public DoctorBuilder withNric(String nric) {
        this.nric = new Nric(nric);
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
        appointmentList = SampleDataUtil.getAppointmentsList(appointment);
        return this;
    }

    @Override
    public DoctorBuilder withAppointments(String ... appointments) {
        appointmentList = SampleDataUtil.getAppointmentsList(appointments);
        return this;
    }

    @Override
    public Doctor build() {
        if (appointmentList != null) {
            return new Doctor(name, nric, phone, email, address, tags, appointmentList, medicalDepartment);
        }
        return new Doctor(name, nric, phone, email, address, tags, medicalDepartment);
    }

}
