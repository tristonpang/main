package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Date;
import seedu.address.model.Time;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.MedicalDepartment;
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
    private Date date;
    /** Starting time of appointment */
    private Time startTime;
    /** Ending time of appointment */
    private Time endTime;
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
            date = new Date(parts[0].trim());
            startTime = new Time(parts[1].trim());
            endTime = new Time(parts[2].trim());
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
        this.date = new Date(date);
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
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
        //if (this.value.equals("") || otherAppointment.value.equals("")) {
        //    return false;
        //}

        // different or doctor means definitely no clash
        if (!date.equals(otherAppointment.date) || !doctorName.equals(otherAppointment.doctorName)) {
            return false;
        }

        Time otherStartTime = otherAppointment.startTime;
        Time otherEndTime = otherAppointment.endTime;

        // 3 Cases where other appointment clashes with current appointment
        if (otherStartTime.comesBefore(startTime) && otherEndTime.comesAfter(endTime)) {
            // Case 1: other appointment's start time is before current appointment's start time
            // and other appointment's end time is after current appointment's end time
            return true;
        } else if (otherStartTime.comesAfter(startTime) && otherStartTime.comesBefore(endTime)) {
            // Case 2: other appointment's start time is after current appointment's start time
            // and before current appointment's end time
            return true;
        } else if (otherEndTime.comesBefore(endTime) && otherEndTime.comesAfter(startTime)) {
            // Case 3: Other appointment's end time is before current appointment's end time
            // and after current appointment's start time
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param time a given time input
     * @return whether the appointment is ongoing at the given date and time.
     */
    public boolean isOngoing(String date, String time) {
        // If different date, we know for sure Appointment is not ongoing.
        if (!this.date.equals(date)) {
            return false;
        }
        Time givenTime = new Time(time);
        return (givenTime.comesAfter(startTime) && givenTime.comesBefore(endTime));
    }

    /**
     * Returns true if instance is a valid Appointment object.
     */
    public boolean isOfCorrectNumberOfParts() {
        String[] parts = value.split(",");
        return ("".equals(value) || parts.length == Appointment.numberOfParts);
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
     * @param person to be tested.
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
     * @return boolean on whether the start time comes strictly before end time.
     */
    public boolean hasValidStartandEndTime() {
        return (startTime.comesBeforeStrictly(endTime));
    }

    /**
     *
     * @return boolean on whether nric of patient is valid.
     */
    public boolean hasValidNric() {
        return patientNric.isValidNric(patientNric.toString());
    }

    /**
     *
     * @return boolean on whether date of appointment is valid.
     */
    public boolean hasValidDate() {
        return date.isValid();
    }

    /**
     *
     * @return boolean on whether start time of appointment is valid.
     */
    public boolean hasValidStartTime() {
        return startTime.isValid();
    }

    /**
     *
     * @return boolean on whether end time of appointment is valid.
     */
    public boolean hasValidEndTime() {
        return endTime.isValid();
    }

    /**
     * Test only used in junit testing.
     *
     * @return whether an appointment is valid or not.
     */
    public boolean isValidAppointment() {
        if ("".equals(value)) {
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
