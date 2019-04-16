package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class AppointmentTest {

    @Test
    public void equals() {
        Appointment appointment = new Appointment("22.11.2018,1300,1400,Jill,S6219609B,Jack,S3869036A");

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // same values -> returns true
        Appointment remarkCopy = new Appointment(appointment.value);
        assertTrue(appointment.equals(remarkCopy));

        // different types -> returns false
        assertFalse(appointment.equals(1));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different remark -> returns false
        Appointment differentRemark = new Appointment("22.11.2018,1300,1400,Alice,S6219609B,Bob,S0654313H");
        assertFalse(appointment.equals(differentRemark));
    }

    @Test
    public void addAndDelete_newAppointment_success() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        Appointment appt1 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt2 = new Appointment("22.11.2018", "1300", "1400",
                "Jill", "S6219609B", "Jack", "S0654313H");
        Appointment appt3 = new Appointment("22.11.2018", "1300", "1400",
                "Jill", "S6219609B", "Jack", "S0654313H");

        appointmentList = AppointmentManager.add(appointmentList, appt1);
        appointmentList = AppointmentManager.add(appointmentList, appt2);
        appointmentList = AppointmentManager.delete(appointmentList, appt3);

        ArrayList<Appointment> secondAppointmentList = new ArrayList<>();
        secondAppointmentList = AppointmentManager.add(appointmentList, appt1);
        assertTrue(appointmentList.equals(secondAppointmentList));
    }

    @Test
    public void isClash_appointmentThatClashesWithCurrentAppointments_failure() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        // appointment 4 and 5 have different Doctors
        Appointment appt4 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt5 = new Appointment("22.11.2018", "1300", "1400",
                "Jill", "S6219609B", "Jack", "S0654313H");

        // appointment 6 and 7 have different dates
        Appointment appt6 = new Appointment("23.11.2018", "1300", "1400",
                "Priscilia", "S6219609B", "Elaine", "S3869036A");
        Appointment appt7 = new Appointment("24.11.2018", "1300", "1400",
                "Priscilia", "S6219609B", "Elaine", "S3869036A");

        // appointment 8 and 9 have different timings that do not clash
        Appointment appt8 = new Appointment("22.11.2018", "1430", "1445",
                "Priscilia", "S6219609B", "Elaine", "S3869036A");
        Appointment appt9 = new Appointment("22.11.2018", "1500", "1600",
                "Priscilia", "S6219609B", "Elaine", "S3869036A");

        appointmentList = AppointmentManager.add(appointmentList, appt4);
        appointmentList = AppointmentManager.add(appointmentList, appt6);
        appointmentList = AppointmentManager.add(appointmentList, appt8);

        assertTrue(AppointmentManager.isClash(appointmentList, appt5));
        assertFalse(AppointmentManager.isClash(appointmentList, appt7));
        assertFalse(AppointmentManager.isClash(appointmentList, appt9));
    }

    @Test
    public void isClash_appointmentThatClashesWithCurrentAppointmentsBasedOnTimings_success() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        Appointment appt8 = new Appointment("22.11.2018", "1300", "1400",
                "Jeff", "S6219609B", "Seid", "S3869036A");

        // Case 1: new appointment's start time is before current appointment's start time
        // and new appointment's end time is after current appointment's end time
        Appointment appt9 = new Appointment("22.11.2018", "1200", "1500",
                "Jeff", "S6219609B", "Seid", "S3869036A");

        // Case 2: new appointment's start time is after current appointment's start time
        Appointment appt10 = new Appointment("22.11.2018", "1330", "1400",
                "Jeff", "S6219609B", "Seid", "S3869036A");

        // Case 3: new appointment's end time is after current appointment's end time
        Appointment appt11 = new Appointment("22.11.2018", "1200", "1330",
                "Jeff", "S6219609B", "Seid", "S3869036A");

        // Case 4: new appointment's start time is current appointment's end time
        Appointment appt12 = new Appointment("22.11.2018", "1359", "1401",
                "Jeff", "S6219609B", "Seid", "S3869036A");

        // Case 5: new appointment's start and end time are exactly the same as current
        Appointment appt13 = new Appointment("22.11.2018", "1300", "1400",
                "Jeff", "S6219609B", "Seid", "S3869036A");

        appointmentList = AppointmentManager.add(appointmentList, appt8);
        assertTrue(AppointmentManager.isClash(appointmentList, appt9));
        assertTrue(AppointmentManager.isClash(appointmentList, appt10));
        assertTrue(AppointmentManager.isClash(appointmentList, appt11));
        assertTrue(AppointmentManager.isClash(appointmentList, appt12));
        assertTrue(AppointmentManager.isClash(appointmentList, appt13));
    }

    @Test
    public void isClash_appointmentThatClashesWithCurrentAppointmentsBasedOnMoreTimings_success() {
        Appointment appt1 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt2 = new Appointment("22.11.2018", "1401", "1405",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt3 = new Appointment("22.11.2018", "1300", "1330",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt4 = new Appointment("22.11.2018", "1330", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");

        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appt1);
        appointments.add(appt2);
        appointments.add(appt3);

        assertTrue(AppointmentManager.isClash(appointments, appt4));
    }

    @Test
    public void isAnyAppointmentOngoing_appointmentList_failure() {
        Appointment appt1 = new Appointment("13.10.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt2 = new Appointment("13.10.2018", "0600", "0700",
                "Alice", "S6219609B", "Bob", "S3869036A");
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appt1);
        appointments.add(appt2);
        // Those appointments are in all in the past.
        assertFalse(AppointmentManager.isAnyAppointmentOngoing(appointments));
    }

    @Test
    public void removeAppointmentsOfDoctor_nricGiven_success() {
        Appointment appt1 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt2 = new Appointment("22.11.2018", "1401", "1405",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt3 = new Appointment("22.11.2018", "1300", "1330",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt4 = new Appointment("22.11.2018", "1330", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appt1);
        appointments.add(appt2);
        appointments.add(appt3);
        appointments.add(appt4);
        ArrayList<Appointment> resultAppointments =
                AppointmentManager.removeAppointmentsOfDoctor(new Nric("S6219609B"), appointments);
        assertTrue(resultAppointments.isEmpty());
    }

    @Test
    public void removeAppointmentsOfPatient_nricGiven_success() {
        Appointment appt1 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt2 = new Appointment("22.11.2018", "1401", "1405",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt3 = new Appointment("22.11.2018", "1300", "1330",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt4 = new Appointment("22.11.2018", "1330", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appt1);
        appointments.add(appt2);
        appointments.add(appt3);
        appointments.add(appt4);
        ArrayList<Appointment> resultAppointments =
                AppointmentManager.removeAppointmentsOfPatient(new Nric("S3869036A"), appointments);
        assertTrue(resultAppointments.isEmpty());
    }

    @Test
    public void changePatientNameAndNric_oldNameAndoldNricAndNewNameAndNewNric_success() {
        Appointment appt1 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt2 = new Appointment("22.11.2018", "1401", "1405",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt3 = new Appointment("22.11.2018", "1300", "1330",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt4 = new Appointment("22.11.2018", "1330", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appt1);
        appointments.add(appt2);
        appointments.add(appt3);
        appointments.add(appt4);
        Appointment appt5 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Elaine", "S1065265J");
        Appointment appt6 = new Appointment("22.11.2018", "1401", "1405",
                "Alice", "S6219609B", "Elaine", "S1065265J");
        Appointment appt7 = new Appointment("22.11.2018", "1300", "1330",
                "Alice", "S6219609B", "Elaine", "S1065265J");
        Appointment appt8 = new Appointment("22.11.2018", "1330", "1400",
                "Alice", "S6219609B", "Elaine", "S1065265J");
        ArrayList<Appointment> testAppointments = new ArrayList<>();
        testAppointments.add(appt5);
        testAppointments.add(appt6);
        testAppointments.add(appt7);
        testAppointments.add(appt8);
        ArrayList<Appointment> resultAppointments =
                AppointmentManager.changePatientNameAndNric(new Name("Bob"), new Nric("S3869036A"),
                        new Name("Elaine"), new Nric("S1065265J"), appointments);
        assertTrue(resultAppointments.equals(testAppointments));
    }

    @Test
    public void changeDoctorNameAndNric_oldNameAndoldNricAndNewNameAndNewNric_success() {
        Appointment appt1 = new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt2 = new Appointment("22.11.2018", "1401", "1405",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt3 = new Appointment("22.11.2018", "1300", "1330",
                "Alice", "S6219609B", "Bob", "S3869036A");
        Appointment appt4 = new Appointment("22.11.2018", "1330", "1400",
                "Alice", "S6219609B", "Bob", "S3869036A");
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appt1);
        appointments.add(appt2);
        appointments.add(appt3);
        appointments.add(appt4);
        Appointment appt5 = new Appointment("22.11.2018", "1300", "1400",
                "Elaine", "S1065265J", "Bob", "S3869036A");
        Appointment appt6 = new Appointment("22.11.2018", "1401", "1405",
                "Elaine", "S1065265J", "Bob", "S3869036A");
        Appointment appt7 = new Appointment("22.11.2018", "1300", "1330",
                "Elaine", "S1065265J", "Bob", "S3869036A");
        Appointment appt8 = new Appointment("22.11.2018", "1330", "1400",
                "Elaine", "S1065265J", "Bob", "S3869036A");
        ArrayList<Appointment> testAppointments = new ArrayList<>();
        testAppointments.add(appt5);
        testAppointments.add(appt6);
        testAppointments.add(appt7);
        testAppointments.add(appt8);
        ArrayList<Appointment> resultAppointments =
                AppointmentManager.changeDoctorNameAndNric(new Name("Alice"), new Nric("S6219609B"),
                        new Name("Elaine"), new Nric("S1065265J"), appointments);
        assertTrue(resultAppointments.equals(testAppointments));
    }
}
