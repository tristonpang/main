package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Represents a Person's Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    /** Number of parts of an appointment */
    private static int numberOfParts = 7;

    /** String value of whole appointment **/
    public final String value;
    /** Date of appointment */
    private String date;
    /** Starting time of appointment */
    private String startTime;
    /** Ending time of appointment */
    private String endTime;
    /** Name of doctor */
    private Name doctorName;
    /** Medical department of doctorName */
    private MedicalDepartment medicalDepartment;
    /** Name of patient */
    private Name patientName;
    /** nric of patient */
    private Nric patientNric;

    // Constructor used during junit testing.
    public Appointment(String appointment) {
        requireNonNull(appointment);
        value = appointment;
        String[] parts = value.split(",");
        if (parts.length == numberOfParts) {
            date = parts[0].trim();
            startTime = parts[1].trim();
            endTime = parts[2].trim();
            doctorName = new Name(parts[3].trim());
            medicalDepartment = new MedicalDepartment(parts[4].trim());
            patientName = new Name(parts[5].trim());
            patientNric = new Nric(parts[6].trim());
        }
    }

    // Constructor used when taking in inputs from parser.
    public Appointment(String date, String startTime, String endTime,
                       String doctorName, String department, String patientName, String nric) {
        value = date + "," + startTime + "," + endTime
                + "," + doctorName + "," + department + "," + patientName + "," + nric;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctorName = new Name(doctorName);
        this.medicalDepartment = new MedicalDepartment(department);
        this.patientName = new Name(patientName);
        this.patientNric = new Nric(nric);
    }

    /**
     * Checks if there are any clashes between another appointment
     * compared to this appointment.
     *
     * @param otherAppointment another appointment
     * @return Boolean if there is any clash.
     */
    public boolean isClash(Appointment otherAppointment) {
        // TODO: 1/10/2018 : Change this quick fix.
        if (this.value.equals("") || otherAppointment.value.equals("")) {
            return false;
        }
        // different or doctor means definitely no clash
        if (!date.equals(otherAppointment.date) || !doctorName.equals(otherAppointment.doctorName)) {
            return false;
        }

        int currentStartTime = Integer.parseInt(startTime.trim());
        int currentEndTime = Integer.parseInt(endTime.trim());
        int otherStartTime = Integer.parseInt(otherAppointment.startTime.trim());
        int otherEndTime = Integer.parseInt(otherAppointment.endTime.trim());

        // 3 Cases where other appointment clashes with current appointment
        if (otherStartTime <= currentStartTime && otherEndTime >= currentEndTime) {
            // Case 1: other appointment's start time is before current appointment's start time
            // and other appointment's end time is after current appointment's end time
            return true;
        } else if (otherStartTime >= currentStartTime && otherStartTime <= currentEndTime) {
            // Case 2: other appointment's start time is after current appointment's start time
            // and before current appointment's end time
            return true;
        } else if (otherEndTime <= currentEndTime && otherEndTime >= currentStartTime) {
            // Case 3: Other appointment's end time is before current appointment's end time
            // and after current appointment's start time
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if instance is a valid Appointment object.
     */
    public boolean isOfCorrectNumberOfParts() {
        String[] parts = value.split(",");
        if (value.equals("") || parts.length == Appointment.numberOfParts) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param person to be tested
     * @return boolean on whether the person is a valid doctor.
     */
    public boolean hasValidDoctor(Person person) {
        Doctor doctor = (Doctor) person;
        Name name = doctor.getName();
        MedicalDepartment medicalDepartment = doctor.getMedicalDepartment();
        if (doctorName.equals(name) && this.medicalDepartment.equals(medicalDepartment)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param person to be tested
     * @return boolean on whether the person is a valid patient.
     */
    public boolean hasValidPatient(Person person) {
        Patient patient = (Patient) person;
        Name name = patient.getName();
        Nric nric = patient.getNric();
        if (patientName.equals(name) && patientNric.equals(nric)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return boolean on whether the start and end times are valid.
     */
    public boolean hasValidStartandEndTime() {
        int currentStartTime = Integer.parseInt(startTime.trim());
        int currentEndTime = Integer.parseInt(endTime.trim());
        return (currentStartTime < currentEndTime);
    }

    public boolean hasValidNric() {
        return patientNric.isValidNric(patientNric.toString());
    }

    public boolean isValidAppointment() {
        if (value.equals("")) {
            // For junit testing.
            return true;
        }
        return isOfCorrectNumberOfParts() && hasValidStartandEndTime() && hasValidNric();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Appointment) {
            Appointment appointment = (Appointment) obj;
            return appointment.value.toUpperCase().equals(this.value.toUpperCase());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
