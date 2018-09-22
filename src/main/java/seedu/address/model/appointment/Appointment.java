package seedu.address.model.appointment;

import java.util.ArrayList;

public class Appointment {

    private String date;
    private String startTime;
    private String endTime;
    private String doctor; // change to class
    private String patient; // change to class

    public Appointment(String date, String startTime, String endTime, String doctor, String patient) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.patient = patient;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Appointment) {
            Appointment appointment = (Appointment) obj;
            return (appointment.date.equals(date) &&
                    appointment.startTime.equals(startTime) &&
                    appointment.endTime.equals(endTime) &&
                    appointment.doctor.equals(doctor) &&
                    appointment.patient.equals(patient));
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String toDisplay = "";
        toDisplay += date + "|";
        toDisplay += startTime + "|";
        toDisplay += endTime + "|";
        toDisplay += doctor + "|";
        toDisplay += patient;
        return toDisplay;
    }

}