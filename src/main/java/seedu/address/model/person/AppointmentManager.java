package seedu.address.model.person;

import java.lang.reflect.Array;
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

    /**
     * Checks if there are any clashes between another appointment.
     *
     * @param appointment current appointment
     * @param otherAppointment  another appointment
     * @return Boolean if there is any clash between other appointment and current appointment
     */
    public static boolean isClash(Appointment appointment, Appointment otherAppointment) {
        if (appointment.isClash(otherAppointment)) {
            return true;
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

    /**
     *
     * @param appointmentList a list of Appointments.
     * @return a boolean stating if there exists any appointment ongoing at the current time.
     */
    public static boolean isAnyAppointmentOngoing(ArrayList<Appointment> appointmentList) {
        if (appointmentList == null) {
            return false;
        }
        // We first retrieve the current date and time in the same format as Appointment's.
        String[] dateAndTime = getCurrentDateAndTime().split(",");
        String currentDate = dateAndTime[0];
        String currentTime = dateAndTime[1];
        // System.out.println(currentDate + " " + currentTime);

        // We check if the current time lies in the time interval of any Appointment.
        for (Appointment appt : appointmentList) {
            if (appt.value.equals("")) {
                continue;
            } else if (appt.isOngoing(currentDate, currentTime)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param patientNric Nric of patient.
     * @param appointmentList Appointment List to be processed.
     * @return a new appointment list where all the appointments
     * with a patient that has the same Nric as input will be removed.
     */
    public static ArrayList<Appointment> removeAppointmentsOfPatient(Nric patientNric,
                                                                     ArrayList<Appointment> appointmentList) {
        ArrayList<Appointment> resultList = new ArrayList<>();
        for (Appointment appt : appointmentList) {
            if (!appt.getPatientNric().equals(patientNric)) {
                resultList.add(appt);
            }
        }
        return resultList;
    }

    /**
     *
     * @param doctorNric Nric of doctor.
     * @param appointmentList Appointment List to be processed.
     * @return a new appointment list where all the appointments
     * with a doctor that has the same Nric as input will be removed.
     */
    public static ArrayList<Appointment> removeAppointmentsOfDoctor(Nric doctorNric,
                                                                    ArrayList<Appointment> appointmentList) {
        ArrayList<Appointment> resultList = new ArrayList<>();
        for (Appointment appt : appointmentList) {
            if (!appt.getDoctorNric().equals(doctorNric)) {
                resultList.add(appt);
            }
        }
        return resultList;
    }

    /**
     * Extracts the current date and time from the java API for Singapore's timezone.
     * Afterwards, format of date and time will be changed to that of Appointment's for easy comparison.
     *
     * @return a String containing the current date and time that aligns with the same format as Appointment.
     */
    private static String getCurrentDateAndTime() {
        return Date.getCurrentDate().toString() + "," + Time.getCurrentTime().toString();
    }
}
