package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;

/**
 * Represents a Person's Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment extends DisplayableAttribute {

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
    private Nric doctorNric;
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
            doctorNric = new Nric(parts[4].trim());
            patientName = new Name(parts[5].trim());
            patientNric = new Nric(parts[6].trim());
        }
    }

    public Appointment(Date date, Time startTime, Time endTime, Name doctorName, Nric doctorNric, Name patientName,
                       Nric patientNric) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctorName = doctorName;
        this.doctorNric = doctorNric;
        this.patientName = patientName;
        this.patientNric = patientNric;

        value = date.toString() + "," + startTime.toString() + "," + endTime.toString()
                + "," + doctorName.toString() + "," + doctorNric.toString() + "," + patientName.toString()
                + "," + patientNric.toString();
    }

    // Constructor used when taking in inputs from parser.
    public Appointment(String date, String startTime, String endTime,
                       String doctorName, String doctorNric, String patientName, String patientNric) {
        value = date + "," + startTime + "," + endTime
                + "," + doctorName + "," + doctorNric + "," + patientName + "," + patientNric;
        this.date = new Date(date);
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
        this.doctorName = new Name(doctorName);
        this.doctorNric = new Nric(doctorNric);
        this.patientName = new Name(patientName);
        this.patientNric = new Nric(patientNric);
    }

    public String getDateString() {
        return date.toString();
    }

    public String getStartTimeString() {
        return startTime.toString();
    }

    public String getEndTimeString() {
        return endTime.toString();
    }

    public String getDoctorNameString() {
        return doctorName.toString();
    }

    public String getDoctorNricString() {
        return doctorNric.toString();
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Name getDoctorName() {
        return doctorName;
    }

    public Nric getDoctorNric() {
        return doctorNric;
    }

    public String getPatientNameString() {
        return patientName.toString();
    }

    public Name getPatientName() {
        return patientName;
    }

    public Nric getPatientNric() {
        return patientNric;
    }

    public String getPatientNricString() {
        return patientNric.toString();
    }

    /**
     * Checks if there are any clashes between another appointment
     * compared to this appointment.
     *
     * @param otherAppointment another appointment
     * @return Boolean if there is any clash.
     */
    public boolean isClash(Appointment otherAppointment) {
        // different date means definitely no clash
        if (!date.equals(otherAppointment.date)) {
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
        if (!this.date.equals(new Date(date))) {
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
        Nric doctorNric = doctor.getNric();
        return doctorName.equals(name) && this.doctorNric.equals(doctorNric);
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
        return patientName.equals(name) && patientNric.equals(nric);
    }

    /**
     *
     * @return boolean on whether the start time comes strictly before end time.
     */
    public boolean hasValidStartandEndTime() {
        return (startTime.comesStrictlyBefore(endTime));
    }

    /**
     * Check only used in junit testing.
     *
     * @return whether an appointment is valid or not.
     */
    public boolean isValidAppointment() {
        return isOfCorrectNumberOfParts() && hasValidStartandEndTime();
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
