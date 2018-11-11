package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedPatientTest {
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_FORMAT_NRIC = "SJ123324B";
    private static final String INVALID_LENGTH_NRIC = "S1222233322324B";
    private static final String INVALID_CODE_NRIC = "S1234567V";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_APPOINTMENT = "21.11.18,1300,1400,Jack,S2932195G,Pauline,S1740595J";
    private static final String VALID_MEDICAL_RECORD = BENSON.getMedicalRecord().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPatient person = new XmlAdaptedPatient(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(INVALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(null, VALID_NRIC, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNricFormat_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, INVALID_FORMAT_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Nric.MESSAGE_NRIC_CONSTRAINTS;
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNricLength_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, INVALID_LENGTH_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Nric.MESSAGE_NRIC_INVALID_LENGTH;
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNricCode_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, INVALID_CODE_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Nric.MESSAGE_NRIC_INVALID;
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPatient person =
                new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPatient person =
                new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_PHONE, null, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPatient person =
                new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL, null,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MEDICAL_RECORD, invalidTags, VALID_APPOINTMENT);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullAppointment_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MEDICAL_RECORD, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Appointment.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
