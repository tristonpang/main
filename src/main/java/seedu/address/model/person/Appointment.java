package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.model.doctor.Doctor;
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
    private String doctor;
    /** Medical department of doctor */
    private String medicalDepartment;
    /** Name of patient */
    private String patient;
    /** nric of patient */
    private String patientNric;

    public Appointment(String appointment) {
        requireNonNull(appointment);
        value = appointment;
        String[] parts = value.split(",");
        if (parts.length == numberOfParts) {
            date = parts[0];
            startTime = parts[1];
            endTime = parts[2];
            doctor = parts[3];
            medicalDepartment = parts[4];
            patient = parts[5];
            patientNric = parts[6];
        }
    }

    public Appointment(String date, String startTime, String endTime,
                       String doctor, String department, String patient, String nric) {
        value = date + "," + startTime + "," + endTime
                + "," + doctor + "," + department + "," + patient + "," + nric;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.medicalDepartment = department;
        this.patient = patient;
        this.patientNric = nric;
    }

    public String getPatient() {
        return this.patient;
    }

    public String getDoctor() {
        return this.doctor;
    }

    /**
     * Checks if there are any clashes between another appointment
     * compared to this appointment.
     *
     * @param otherAppointment another appointment
     * @return Boolean if there is any clash.
     */
    public boolean isClash(Appointment otherAppointment) {
        if (!date.equals(otherAppointment.date) || !doctor.equals(otherAppointment.doctor)) {
            return false;
        }
        int currentStartTime = Integer.parseInt(startTime.trim());
        int currentEndTime = Integer.parseInt(endTime.trim());
        int otherStartTime = Integer.parseInt(otherAppointment.startTime.trim());
        int otherEndTime = Integer.parseInt(otherAppointment.endTime.trim());

        // 3 Cases where other appointment clashes with current appointment
        if (otherStartTime < currentStartTime && otherEndTime > currentEndTime) {
            // Case 1: other appointment's start time is before current appointment's start time
            // and other appointment's end time is after current appointment's end time
            return true;
        } else if (otherStartTime > currentStartTime) {
            // Case 2: other appointment's start time is after current appointment's start time
            return true;
        } else if (otherEndTime < currentEndTime) {
            // Case 3: Other appointment's end time is before current appointment's end time
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
        if (value == "" || parts.length == Appointment.numberOfParts) {
            return true;
        }
        return false;
    }

    public boolean hasValidDoctor(Person person) {
        Doctor doctor = (Doctor) person;
        String name = doctor.getName().fullName;
        String medicalDepartment = doctor.getMedicalDepartment().deptName;
        if (this.doctor.equals(name) && this.medicalDepartment.equals(medicalDepartment)) {
            return true;
        }
        return false;
    }

    public boolean hasValidPatient(Person person) {
        Patient patient = (Patient) person;
        String name = patient.getName().fullName;
        String nric = patient.getNric().code;
        if (this.patient.equals(name) && patientNric.equals(nric)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Appointment) {
            Appointment appointment = (Appointment) obj;
            return appointment.value.equals(value);
            /* return (appointment.date.equals(date)
                    && appointment.startTime.equals(startTime)
                    && appointment.endTime.equals(endTime)
                    && appointment.doctor.equals(doctor)
                    && appointment.patient.equals(patient)); */
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
