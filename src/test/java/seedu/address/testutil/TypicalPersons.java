package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    // Set all Persons with empty appointments instead of actual Appointments
    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withMedicalRecord(", Diagnosis: , Treatment: , Comments: -")
            .withNric("S3305985Z").withTags("friends").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withNric("S1215130F")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withMedicalRecord(", Diagnosis: , Treatment: , Comments: -")
            .withTags("owesMoney", "friends").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withMedicalRecord(", Diagnosis: , Treatment: , Comments: -")
            .withEmail("heinz@example.com")
            .withAddress("wall street").withNric("S7426958C").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier").withPhone("87652599")
            .withMedicalRecord(", Diagnosis: , Treatment: , Comments: -")
            .withEmail("cornelia@example.com")
            .withAddress("10th street").withNric("S8066331E").withTags("friends").build();
    public static final Doctor ELLE = new DoctorBuilder().withName("Elle Meyer").withNric("S6977714G")
            .withPhone("9482224").withMedicalDepartment("Oncology").withEmail("werner@example.com")
            .withAddress("michegan ave").build();
    public static final Doctor FIONA = new DoctorBuilder().withName("Fiona Kunz").withNric("S5319783C")
            .withPhone("9482427").withMedicalDepartment("Cardiology").withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Doctor GEORGE = new DoctorBuilder().withName("George Best").withNric("S5882198E")
            .withPhone("9482442").withMedicalDepartment("Neurology").withEmail("anna@example.com")
            .withAddress("4th street").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withNric("S9028878D").withEmail("stefan@example.com").withAddress("little india").build();
    public static final Doctor IDA = new DoctorBuilder().withName("Ida Mueller").withNric("S5026428I")
            .withPhone("8482131").withMedicalDepartment("Physiology").withEmail("hans@example.com")
            .withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withNric(VALID_NRIC_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withNric(VALID_NRIC_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
