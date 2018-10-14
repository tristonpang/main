package seedu.address.model.person;

import java.lang.reflect.Array;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        // We first retrieve the current date and time in the same format as Appointment's.
        String[] dateAndTime = getCurrentDateAndTime().split(",");
        String currentDate = dateAndTime[0];
        String currentTime = dateAndTime[1];
        System.out.println(currentDate + " " + currentTime);

        // We check if the current time lies in the time interval of any Appointment.
        for (Appointment appt : appointmentList) {
            if (appt.isOngoing(currentDate, currentTime)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts the current date and time from the java API for Singapore's timezone.
     * Afterwards, format of date and time will be changed to that of Appointment's for easy comparison.
     *
     * @return a String containing the current date and time that aligns with the same format as Appointment.
     */
    private static String getCurrentDateAndTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Singapore"));
        String stringZonedDateTime = zonedDateTime.toString();
        // Splitting output from API into a date part and a time part.
        String[] dateAndTimeParts = stringZonedDateTime.split("T");

        // Reformatting the order of the date.
        String currentDate = dateAndTimeParts[0];
        String[] dateParts = currentDate.split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        currentDate = day+"."+month+"."+year;

        // Reformatting the oder of the time.
        String currentTime = dateAndTimeParts[1];
        String[] currentTimeParts = currentTime.split(":");
        String hour = currentTimeParts[0];
        String minute = currentTimeParts[1];
        currentTime = hour + minute;

        return currentDate + "," + currentTime;
    }
}
