package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Nric;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "SJ123324B";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_APPOINTMENT = "FAKE APPOINTMENT"; // will change this later stage
    private static final String VALID_MEDICAL_DEPARTMENT = GEORGE.getMedicalDepartment().toString();
    private static final String VALID_MEDICAL_RECORD = BENSON.getMedicalRecord().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPatient(BENSON);
        assertEquals(BENSON, person.toModelType());

        person = new XmlAdaptedDoctor(GEORGE);
        assertEquals(GEORGE, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPatient patient =
                new XmlAdaptedPatient(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);

        XmlAdaptedDoctor doctor =
                new XmlAdaptedDoctor(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_MEDICAL_DEPARTMENT, VALID_APPOINTMENT);
        expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPatient patient = new XmlAdaptedPatient(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);

        XmlAdaptedDoctor doctor = new XmlAdaptedDoctor(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_MEDICAL_DEPARTMENT, VALID_APPOINTMENT);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPatient patient =
                new XmlAdaptedPatient(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson patient = new XmlAdaptedPatient(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);

        XmlAdaptedPerson doctor = new XmlAdaptedDoctor(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                VALID_APPOINTMENT, VALID_MEDICAL_DEPARTMENT);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPatient patient =
                new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);

        XmlAdaptedDoctor doctor =
                new XmlAdaptedDoctor(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_NRIC,
                        VALID_MEDICAL_DEPARTMENT);
        expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPatient patient = new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);

        XmlAdaptedDoctor doctor = new XmlAdaptedDoctor(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS,
                VALID_APPOINTMENT, VALID_MEDICAL_DEPARTMENT);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPatient patient =
                new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);

        XmlAdaptedDoctor doctor =
                new XmlAdaptedDoctor(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        VALID_APPOINTMENT, VALID_MEDICAL_DEPARTMENT);
        expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPatient patient = new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_MEDICAL_RECORD, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);

        XmlAdaptedDoctor doctor = new XmlAdaptedDoctor(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS,
                VALID_APPOINTMENT, VALID_MEDICAL_DEPARTMENT);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullMedicalRecord_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_TAGS, VALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                MedicalRecord.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, null, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        XmlAdaptedPatient person = new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_MEDICAL_RECORD, VALID_TAGS, INVALID_NRIC, VALID_APPOINTMENT);
        String expectedMessage = String.format(Nric.MESSAGE_NRIC_CONSTRAINTS,
                Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_nullMedicalDepartment_throwsIllegalValueException() {
        XmlAdaptedDoctor doctor = new XmlAdaptedDoctor(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                VALID_APPOINTMENT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                MedicalDepartment.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPatient patient =
                new XmlAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_MEDICAL_RECORD,
                        invalidTags, VALID_NRIC, VALID_APPOINTMENT);
        Assert.assertThrows(IllegalValueException.class, patient::toModelType);

        XmlAdaptedDoctor doctor =
                new XmlAdaptedDoctor(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        invalidTags, VALID_APPOINTMENT, VALID_MEDICAL_DEPARTMENT);
        Assert.assertThrows(IllegalValueException.class, doctor::toModelType);
    }

}
