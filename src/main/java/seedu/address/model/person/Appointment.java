package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Appointment {

    /** String value of whole appointment **/
    public final String value;
    /** Date of appointment */
    private String date;
    /** Starting time of appointment */
    private String startTime;
    /** Ending time of appointment */
    private String endTime;
    /** Name of doctor */
    private String doctor; // change to class
    /** Name of patient */
    private String patient; // change to class
    /** Number of parts of an appointment */
    public static int numberOfParts = 5;

    public Appointment(String appointment) {
        requireNonNull(appointment);
//        if (!appointment.equals("")) {
//            String[] parts = appointment.split(",");
//            date = parts[0];
//            startTime = parts[1];
//            endTime = parts[2];
//            doctor = parts[3];
//            patient = parts[4];
//        }
        value = appointment;
    }

    public Appointment(String date, String startTime, String endTime, String doctor, String patient) {
        value = date + "," + startTime + "," + endTime + "," + doctor + "," + patient;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.patient = patient;
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

    public boolean isValid() {
        String[] parts = value.split(",");
        if (value == "" || parts.length == Appointment.numberOfParts) {
            return true;
        }
        return false;
    }

    public String getPatient() {
        return this.patient;
    }

    public String getDoctor() {
        return this.doctor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Appointment) {
            Appointment appointment = (Appointment) obj;
            return appointment.value.equals(value);
//            return (appointment.date.equals(date)
//                    && appointment.startTime.equals(startTime)
//                    && appointment.endTime.equals(endTime)
//                    && appointment.doctor.equals(doctor)
//                    && appointment.patient.equals(patient));
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