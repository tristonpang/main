package seedu.address.storage;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;

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
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public String toModelType() throws IllegalValueException {
        // TODO: 25/9/2018 Create Appointment class and isValidAppt method
        /*if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }*/
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
