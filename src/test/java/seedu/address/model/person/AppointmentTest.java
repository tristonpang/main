package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppointmentTest {

    @Test
    public void equals() {
        Appointment appointment = new Appointment("Hello");

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // same values -> returns true
        Appointment appointmentCopy = new Appointment(appointment.value);
        assertTrue(appointment.equals(appointmentCopy));

        // different types -> returns false
        assertFalse(appointment.equals(1));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different appointment -> returns false
        Appointment differentAppointment = new Appointment("Bye");
        assertFalse(appointment.equals(differentAppointment));
    }
}