package seedu.address.model.person;

import java.util.ArrayList;

/**
 * Manages {@code Appointment} objects to check for clashes.
 */
public class AppointmentManager {

    private static final String PSEUDO_DOCTOR_NAME = "JOHN DOE";
    private static final String PSEUDO_DOCTOR_NRIC = "S2401932B";
    private static final String PSEUDO_PATIENT_NAME = "BOB";
    private static final String PSEUDO_PATIENT_NRIC = "S8758412G";

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
        return appointment.isClash(otherAppointment);
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
     * @return a boolean stating if there exists any appointment ongoing at the current datetime.
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
     * @param appointmentList a list of Appointments.
     * @return a boolean stating if there exists any appointment ongoing base on given date time.
     */
    public static boolean isAnyAppointmentOngoing(ArrayList<Appointment> appointmentList, Date date, Time startTime,
                                                  Time endTime) {
        if (appointmentList == null) {
            return false;
        }

        // System.out.println(currentDate + " " + currentTime);

        // We check if the current time lies in the time interval of any Appointment.
        for (Appointment appt : appointmentList) {
            if (appt.value.equals("")) {
                continue;
            } else if (appt.isClash(new Appointment(date.toString(), startTime.toString(), endTime.toString(),
                    PSEUDO_DOCTOR_NAME, PSEUDO_DOCTOR_NRIC, PSEUDO_PATIENT_NAME, PSEUDO_PATIENT_NRIC))) {
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
        assert Nric.isValidNric(patientNric.toString()) : "patient's nric should be valid";
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
        assert Nric.isValidNric(doctorNric.toString()) : "doctor's nric should be valid";
        ArrayList<Appointment> resultList = new ArrayList<>();
        for (Appointment appt : appointmentList) {
            if (!appt.getDoctorNric().equals(doctorNric)) {
                resultList.add(appt);
            }
        }
        return resultList;
    }

    /**
     *
     * @param oldPatientName Name of patient before change.
     * @param oldPatientNric Nric of patient before change.
     * @param newPatientName Name of patient after change.
     * @param newPatientNric Nric of patient after change.
     * @param appointmentList Appointment List of the patient.
     * @return a new Appointment List where the appointments with the previous
     * patient's name and nric will be updated to reflect the new change.
     */
    public static ArrayList<Appointment> changePatientNameAndNric(Name oldPatientName, Nric oldPatientNric,
                                                                  Name newPatientName, Nric newPatientNric,
                                                                  ArrayList<Appointment> appointmentList) {
        assert Name.isValidName(oldPatientName.toString()) : "old patient's name should be valid";
        assert Name.isValidName(newPatientName.toString()) : "new patient's name should be valid";
        assert Nric.isValidNric(oldPatientNric.toString()) : "old patient's nric should be valid";
        assert Nric.isValidNric(newPatientNric.toString()) : "new patient's nric should be valid";

        ArrayList<Appointment> newAppointmentList = new ArrayList<>();
        for (Appointment appt : appointmentList) {
            if (appt.getPatientName().equals(oldPatientName) && appt.getPatientNric().equals(oldPatientNric)) {
                Appointment newAppt = new Appointment(appt.getDate(), appt.getStartTime(), appt.getEndTime(),
                        appt.getDoctorName(), appt.getDoctorNric(), newPatientName, newPatientNric);
                newAppointmentList.add(newAppt);
            } else {
                newAppointmentList.add(appt);
            }
        }
        return newAppointmentList;
    }

    /**
     *
     * @param oldDoctorName Name of doctor before change.
     * @param oldDoctorNric Nric of doctor before change.
     * @param newDoctorName Name of doctor after change.
     * @param newDoctorNric Nric of doctor after change.
     * @param appointmentList Appointment List of the doctor.
     * @return a new Appointment List where the appointments with the previous
     * doctor's name and nric will be updated to reflect the new change.
     */
    public static ArrayList<Appointment> changeDoctorNameAndNric(Name oldDoctorName, Nric oldDoctorNric,
                                                                  Name newDoctorName, Nric newDoctorNric,
                                                                  ArrayList<Appointment> appointmentList) {
        assert Name.isValidName(oldDoctorName.toString()) : "old doctor's name should be valid";
        assert Name.isValidName(newDoctorName.toString()) : "new doctor's name should be valid";
        assert Nric.isValidNric(oldDoctorNric.toString()) : "old doctor's nric should be valid";
        assert Nric.isValidNric(newDoctorNric.toString()) : "new doctor's nric should be valid";

        ArrayList<Appointment> newAppointmentList = new ArrayList<>();
        for (Appointment appt : appointmentList) {
            if (appt.getDoctorName().equals(oldDoctorName) && appt.getDoctorNric().equals(oldDoctorNric)) {
                Appointment newAppt = new Appointment(appt.getDate(), appt.getStartTime(), appt.getEndTime(),
                        newDoctorName, newDoctorNric, appt.getPatientName(), appt.getPatientNric());
                newAppointmentList.add(newAppt);
            } else {
                newAppointmentList.add(appt);
            }
        }
        return newAppointmentList;
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
