package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Test;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentManager;

public class AppointmentTest {

    @Test
    public void equals() {
        Appointment appointment = new Appointment("22.11.2018,1300,1400,Jill,Jack");

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
        Appointment differentRemark = new Appointment("22.11.2018,1300,1400,Alice,Bob");
        assertFalse(appointment.equals(differentRemark));
    }

    @Test
    public void addAndDeleteTest() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        Appointment appt1 = new Appointment("22.11.2018","1300","1400","Alice","Bob");
        Appointment appt2 = new Appointment("22.11.2018","1300","1400","Jill","Jack");
        Appointment appt3 = new Appointment("22.11.2018","1300","1400","Jill","Jack");

        appointmentList = AppointmentManager.add(appointmentList, appt1);
        appointmentList = AppointmentManager.add(appointmentList, appt2);
        appointmentList = AppointmentManager.delete(appointmentList, appt3);

        ArrayList<Appointment> secondAppointmentList = new ArrayList<>();
        secondAppointmentList = AppointmentManager.add(appointmentList, appt1);
        assertTrue(appointmentList.equals(secondAppointmentList));
    }

    @Test
    public void noClashTest() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        // appointment 4 and 5 have different Doctors
        Appointment appt4 = new Appointment("22.11.2018","1300","1400","Alice","Bob");
        Appointment appt5 = new Appointment("22.11.2018","1300","1400","Jill","Jack");

        // appointment 6 and 7 have different
        Appointment appt6 = new Appointment("23.11.2018","1300","1400","Priscilia","Elaine");
        Appointment appt7 = new Appointment("22.11.2018","1300","1400","Priscilia","Elaine");

        appointmentList = AppointmentManager.add(appointmentList, appt4);
        appointmentList = AppointmentManager.add(appointmentList, appt6);
        assertFalse(AppointmentManager.isClash(appointmentList, appt5));
        assertFalse(AppointmentManager.isClash(appointmentList, appt7));
    }

    @Test
    public void clashTest() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        Appointment appt8 = new Appointment("22.11.2018","1300","1400","Jeff","Seid");

        // Case 1: new appointment's start time is before current appointment's start time
        // and new appointment's end time is after current appointment's end time
        Appointment appt9 = new Appointment("22.11.2018","1200","1500","Jeff","Seid");

        // Case 2: new appointment's start time is after current appointment's start time
        Appointment appt10 = new Appointment("22.11.2018","1330","1400","Jeff","Seid");

        // Case 3: new appointment's end time is after current appointment's end time
        Appointment appt11 = new Appointment("22.11.2018","1200","1330","Jeff","Seid");

        appointmentList = AppointmentManager.add(appointmentList, appt8);
        assertTrue(AppointmentManager.isClash(appointmentList, appt9));
        assertTrue(AppointmentManager.isClash(appointmentList, appt10));
        assertTrue(AppointmentManager.isClash(appointmentList, appt11));
    }
}
