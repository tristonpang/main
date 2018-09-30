package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Appointment SAMPLE_APPOINTMENT = new Appointment("22.11.2018,1300,1400,Alice,Heart,Betty,S1234567A");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Patient(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends"), SAMPLE_APPOINTMENT,
                    new Nric("S1234567A"), new MedicalRecord("")),
            new Doctor(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), getTagSet("colleagues", "friends"),
                    SAMPLE_APPOINTMENT, new MedicalDepartment("Neurology")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours"),
                    SAMPLE_APPOINTMENT, new Nric("S2222222B"), new MedicalRecord("Diagnosed with cough. "
                    + "Dextromethorphan prescribed.")),
            new Patient(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"), new Address(
                    "Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family"),
                    SAMPLE_APPOINTMENT, new Nric("S9991114A"), new MedicalRecord("Diagnosed with stage 3 testicular"
                    + " cancer. Orchiectomy for both testicles.")),
            new Doctor(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"), SAMPLE_APPOINTMENT,
                new MedicalDepartment("Obstetrics")),
            new Doctor(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"), SAMPLE_APPOINTMENT,
                    new MedicalDepartment("Cardiology"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
