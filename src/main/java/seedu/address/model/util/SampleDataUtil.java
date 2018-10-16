package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.MedicalDepartment;
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

    // NOTE: Error when whitespace inserted Appointment
    public static final ArrayList<Appointment> SAMPLE_APPOINTMENT_LIST =
            new ArrayList<>(Arrays.asList(new Appointment("22.11.2018,1300,1400,Alice,Heart,Betty,S1234567A")));

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Patient(new Name("Alex Yeoh"), new Nric("S1234567A"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), SAMPLE_APPOINTMENT_LIST, new MedicalRecord("")),
            new Doctor(new Name("Bernice Yu"), new Nric("S1010101A"), new Phone("99272758"),
                    new Email("berniceyu" + "@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), SAMPLE_APPOINTMENT_LIST,
                    new MedicalDepartment("Neurology")),
            new Patient(new Name("Charlotte Oliveiro"), new Nric("S2020202B"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), SAMPLE_APPOINTMENT_LIST,
                    new MedicalRecord("Diagnosed with cough. " + "Dextromethorphan prescribed.")),
            new Patient(new Name("David Li"), new Nric("S9991114A"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"), SAMPLE_APPOINTMENT_LIST,
                    new MedicalRecord("Diagnosed with stage 3 testicular cancer. Orchiectomy for both testicles.")),
            new Doctor(new Name("Irfan Ibrahim"), new Nric("S3030303C"), new Phone("92492021"), new Email("irfan"
                    + "@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"),
                    SAMPLE_APPOINTMENT_LIST, new MedicalDepartment("Obstetrics")),
            new Doctor(new Name("Roy Balakrishnan"), new Nric("S4040404D"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), SAMPLE_APPOINTMENT_LIST,
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

    /**
     * Returns an Appointment list containing the list of strings given.
     */
    public static ArrayList<Appointment> getAppointmentsList(String... strings) {
        return Arrays.stream(strings)
                .map(Appointment::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
