package seedu.address.storage;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Appointment;

/**
 * JAXB-friendly adapted version of the Appointment.
 */
@XmlRootElement(name = "appointments")
public class XmlAdaptedAppointment {

    @XmlValue
    private String appointment;

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String appointment) {
        this.appointment = appointment;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param appointment future changes to this will not affect the created
     */
    public XmlAdaptedAppointment(Appointment appointment) {
        String value = appointment.toString();
        this.appointment = value;
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public String toModelType() throws IllegalValueException {
        if (!new Appointment(appointment).isValidAppointment()) {
            throw new IllegalValueException("Invalid Appointment Format");
        }
        return appointment;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        return appointment.equals(((XmlAdaptedAppointment) other).appointment);
    }
}
