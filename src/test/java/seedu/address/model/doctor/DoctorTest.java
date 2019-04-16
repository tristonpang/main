package seedu.address.model.doctor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICAL_DEPARTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.DoctorBuilder;

public class DoctorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Doctor doctor = new DoctorBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        doctor.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(FIONA.isSamePerson(FIONA));

        // null -> returns false
        assertFalse(FIONA.isSamePerson(null));

        // different phone and email -> returns true
        Doctor editedFiona = new DoctorBuilder(FIONA).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(FIONA.isSamePerson(editedFiona));

        // different name -> returns true
        editedFiona = new DoctorBuilder(FIONA).withName(VALID_NAME_BOB).build();
        assertTrue(FIONA.isSamePerson(editedFiona));

        // different nric -> returns false
        editedFiona = new DoctorBuilder(FIONA).withNric(VALID_NRIC_BOB).build();
        assertFalse(FIONA.isSamePerson(editedFiona));

        // same name, same phone, different attributes -> returns true
        editedFiona = new DoctorBuilder(FIONA).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(FIONA.isSamePerson(editedFiona));

        // same name, same email, different attributes -> returns true
        editedFiona = new DoctorBuilder(FIONA).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(FIONA.isSamePerson(editedFiona));

        // same name, same phone, same email, different attributes -> returns true
        editedFiona = new DoctorBuilder(FIONA).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(FIONA.isSamePerson(editedFiona));

        // different name, different medical department, same attributes -> returns true
        editedFiona = new DoctorBuilder(FIONA).withName(VALID_NAME_BOB)
                .withMedicalDepartment(VALID_MEDICAL_DEPARTMENT).build();
        assertTrue(FIONA.isSamePerson(editedFiona));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Doctor fionaCopy = new DoctorBuilder(FIONA).build();
        assertTrue(FIONA.equals(fionaCopy));

        // same object -> returns true
        assertTrue(FIONA.equals(FIONA));

        // null -> returns false
        assertFalse(FIONA.equals(null));

        // different type -> returns false
        assertFalse(FIONA.equals(5));

        // different patient -> returns false
        assertFalse(FIONA.equals(GEORGE));

        // different name -> returns false
        Doctor editedFiona = new DoctorBuilder(FIONA).withName(VALID_NAME_BOB).build();
        assertFalse(FIONA.equals(editedFiona));

        // different phone -> returns false
        editedFiona = new DoctorBuilder(FIONA).withPhone(VALID_PHONE_BOB).build();
        assertFalse(FIONA.equals(editedFiona));

        // different email -> returns false
        editedFiona = new DoctorBuilder(FIONA).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(FIONA.equals(editedFiona));

        // different address -> returns false
        editedFiona = new DoctorBuilder(FIONA).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(FIONA.equals(editedFiona));

        // different tags -> returns false
        editedFiona = new DoctorBuilder(FIONA).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(FIONA.equals(editedFiona));

        // different medical department -> returns false
        editedFiona = new DoctorBuilder(FIONA).withMedicalDepartment(VALID_MEDICAL_DEPARTMENT).build();
        assertFalse(FIONA.equals(editedFiona));
    }
}
