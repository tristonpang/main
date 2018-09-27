package seedu.address.model.person;

import java.util.ArrayList;

/**
 * Manages {@code Appointment} objects to check for clashes.
 */
public class AppointmentManager {

    /**
     * Checks if there are any clashes between another appointment
     * compared to all the appoinments in the appointment list.
     *
     * @param appointmentList list of appointments
     * @param otherAppointment  another appointment
     * @return Boolean if there is any clash between other appointment and the list of appointments.
     */
    public static boolean isClash(ArrayList<Appointment> appointmentList, Appointment otherAppointment) {
        for (Appointment currentAppointment : appointmentList) {
            if (currentAppointment.isClash(otherAppointment)) {
                return true;
            }
        }
        return false;
    }

    public static void displaySchedule(ArrayList<Appointment> appointmentList) {
        return; // connect to UI somehow?!?
    }

    /**
     * Adds an appointment to a list of appointments.
     *
     * @param appointmentList list of appointments
     * @param appointment  another appointment
     * @return appointment list with the new appointment added.
     */
    public static ArrayList<Appointment> add(ArrayList<Appointment> appointmentList, Appointment appointment) {
        appointmentList.add(appointment);
        return appointmentList;
    }

    /**
     * Deletes an appointment from a list of appointments if it
     * matches any appointment in the list.
     *
     * @param appointmentList list of appointments
     * @param appointment  another appointment
     * @return appointment list without the matched appointment if it is found.
     */
    public static ArrayList<Appointment> delete(ArrayList<Appointment> appointmentList, Appointment appointment) {
        appointmentList.remove(appointment);
        return appointmentList;
    }
}
