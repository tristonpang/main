package seedu.address.storage;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;

@XmlRootElement(name = "appointments")
public class XmlAdaptedAppointment {

    @XmlValue
    private String appointment;
    
    public XmlAdaptedAppointment() {}
    
    public XmlAdaptedAppointment(String appointment) {
        this.appointment = appointment;
    }
    
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
