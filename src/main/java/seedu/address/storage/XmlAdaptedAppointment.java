package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Date;
import seedu.address.model.Time;
import seedu.address.model.patient.Nric;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Name;

/**
 * JAXB-friendly adapted version of the Appointment.
 */
@XmlRootElement(name = "appointments")
public class XmlAdaptedAppointment {

    @XmlElement
    private String appointment;

    @XmlElement
    private String date;

    @XmlElement
    private String startTime;

    @XmlElement
    private String endTime;

    @XmlElement
    private String doctorName;

    @XmlElement
    private String doctorNric;

    @XmlElement
    private String patientName;

    @XmlElement
    private String patientNric;

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
        String[] parts = appointment.split(",");
        date = parts[0].trim();
        startTime = parts[1].trim();
        endTime = parts[2].trim();
        doctorName = parts[3].trim();
        doctorNric = parts[4].trim();
        patientName = parts[5].trim();
        patientNric = parts[6].trim();
    }

    /**
     * Converts a given Appointmetn into this class for JAXB use.
     *
     * @param appointment future changes to this will not affect the created
     */
    public XmlAdaptedAppointment(Appointment appointment) {
        String value = appointment.toString();
        this.appointment = value;
        String[] parts = value.split(",");
        date = parts[0].trim();
        startTime = parts[1].trim();
        endTime = parts[2].trim();
        doctorName = parts[3].trim();
        doctorNric = parts[4].trim();
        patientName = parts[5].trim();
        patientNric = parts[6].trim();
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
