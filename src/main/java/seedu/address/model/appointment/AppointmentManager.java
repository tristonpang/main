package seedu.address.model.appointment;

import java.util.ArrayList;

public class AppointmentManager {

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

    public static ArrayList<Appointment> add(ArrayList<Appointment> appointmentList, Appointment appointment) {
        appointmentList.add(appointment);
        return appointmentList;
    }
    public static ArrayList<Appointment> delete(ArrayList<Appointment> appointmentList, Appointment appointment) {
        appointmentList.remove(appointment);
        return appointmentList;
    }
}
