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
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    // NOTE: Error when whitespace inserted Appointment
    public static final ArrayList<Appointment> SAMPLE_APPOINTMENT_LIST_A =
            new ArrayList<>(Arrays.asList(
                    new Appointment("22.11.2018,1300,1400,Bernice Yu,S2810085Z,Alex Yeoh,S3038746E")));
    public static final ArrayList<Appointment> SAMPLE_APPOINTMENT_LIST_B =
            new ArrayList<>(Arrays.asList(
                    new Appointment("22.11.2018,1300,1400,Bernice Yu,S2810085Z,Alex Yeoh,S3038746E"),
                    new Appointment("22.11.2018,1401,1430,Bernice Yu,S2810085Z,Charlotte Oliveiro,S1861343C"),
                    new Appointment("22.11.2018,1431,1445,Bernice Yu,S2810085Z,David Li,S3860937H")));
    public static final ArrayList<Appointment> SAMPLE_APPOINTMENT_LIST_C =
            new ArrayList<>(Arrays.asList(
                    new Appointment("22.11.2018,1401,1430,Bernice Yu,S2810085Z,Charlotte Oliveiro,S1861343C")));
    public static final ArrayList<Appointment> SAMPLE_APPOINTMENT_LIST_D =
            new ArrayList<>(Arrays.asList(
                    new Appointment("22.11.2018,1431,1445,Bernice Yu,S2810085Z,David Li,S3860937H")));

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Patient(new Name("Alex Yeoh"), new Nric("S3038746E"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("hokkien only"), SAMPLE_APPOINTMENT_LIST_A,
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Doctor(new Name("Bernice Yu"), new Nric("S2810085Z"), new Phone("99272758"),
                    new Email("berniceyu" + "@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), SAMPLE_APPOINTMENT_LIST_B,
                    new MedicalDepartment("Neurology")),
            new Patient(new Name("Charlotte Oliveiro"), new Nric("S1861343C"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), SAMPLE_APPOINTMENT_LIST_C,
                    new MedicalRecord("22.22.2017", "cough",
                            "Dextromethorphan", "take thrice a day")),
            new Patient(new Name("David Li"), new Nric("S3860937H"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"), SAMPLE_APPOINTMENT_LIST_D,
                    new MedicalRecord("20.10.2018", "Stage 3 testicular cancer",
                            "Orchiectomy for both testicles", "15% chance of survival")),
            new Doctor(new Name("Irfan Ibrahim"), new Nric("S1536921C"), new Phone("92492021"), new Email("irfan"
                    + "@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"),
                    new MedicalDepartment("Obstetrics")),
            new Doctor(new Name("Roy Balakrishnan"), new Nric("S2271707C"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new MedicalDepartment("Cardiology")),
            new Doctor(new Name("House"), new Nric("S7166195D"), new Phone("92623317"),
                    new Email("house@example.com"), new Address("Blk 46 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new MedicalDepartment("Cardiology")),
            new Doctor(new Name("Jay Cutler"), new Nric("S7400037A"), new Phone("93324417"),
                    new Email("jay@example.com"), new Address("Blk 47 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new MedicalDepartment("Heart")),
            new Doctor(new Name("Cristiano Ronaldo"), new Nric("S0928611Z"), new Phone("92644178"),
                    new Email("cr7@example.com"), new Address("Blk 48 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new MedicalDepartment("Sports")),
            new Patient(new Name("Kenneth Goh"), new Nric("S2967846D"), new Phone("88438807"),
                    new Email("kennethgoh@example.com"), new Address("Blk 31 Hougang Street 29, #30-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Kenneth Yeoh"), new Nric("S9254631D"), new Phone("87438807"),
                    new Email("neth@example.com"), new Address("Blk 32 Serangoon Street 29, #29-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Nelvin Tan"), new Nric("S4485446E"), new Phone("87538807"),
                    new Email("nelvin@example.com"), new Address("Blk 33 Sengkang Street 29, #28-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Nelvin Yeoh"), new Nric("S1968289G"), new Phone("87538807"),
                    new Email("nelvinyeoh@example.com"), new Address("Blk 34 Changi Street 29, #27-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Gary Goh"), new Nric("S6837870B"), new Phone("87938807"),
                    new Email("ggyp@example.com"), new Address("Blk 35 Punggol Street 29, #26-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Gary Yeoh"), new Nric("S9887917Z"), new Phone("87438817"),
                    new Email("garyyeoh@example.com"), new Address("Blk 36 Sengkang Street 29, #25-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Triston Pang"), new Nric("S8447383I"), new Phone("87438827"),
                    new Email("trist@example.com"), new Address("Blk 37 Buangkok Street 29, #24-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Triston Yeoh"), new Nric("S6376673I"), new Phone("87438837"),
                    new Email("tristonyeoh@example.com"), new Address("Blk 38 Hougang Street 29, #23-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Selene Yeoh"), new Nric("S3024786H"), new Phone("87438847"),
                    new Email("seleneyeoh@example.com"), new Address("Blk 39 Serangoon Street 29, #22-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Celine Yeoh"), new Nric("S1942538Z"), new Phone("87438857"),
                    new Email("celineeoh@example.com"), new Address("Blk 40 Punggol Street 29, #21-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Priscilia Yeoh"), new Nric("S7467468B"), new Phone("87438867"),
                    new Email("prisciliahere@example.com"), new Address("Blk 41 Sengkang Street 29, #20-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Priscilla Yeoh"), new Nric("S4873782Z"), new Phone("87438877"),
                    new Email("prissh@example.com"), new Address("Blk 42 Buangkok Street 29, #19-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Elaine Yeoh"), new Nric("S5436755D"), new Phone("87438887"),
                    new Email("elaineyeoh@example.com"), new Address("Blk 43 Hougang Street 29, #18-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Sophia Tan"), new Nric("S9105236I"), new Phone("87438897"),
                    new Email("sophiatan@example.com"), new Address("Blk 44 Kovan Street 29, #17-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Daisy Yeoh"), new Nric("S9049716B"), new Phone("87431807"),
                    new Email("daisyyeoh@example.com"), new Address("Blk 45 Serangoon Street 29, #16-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Anna Yeoh"), new Nric("S6122266I"), new Phone("87432807"),
                    new Email("annayeoh@example.com"), new Address("Blk 46 Punggol Street 29, #15-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Anne Yeoh"), new Nric("S0142243Z"), new Phone("87433807"),
                    new Email("anneyeoh@example.com"), new Address("Blk 47 Sengkang Street 29, #14-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Alexander Pham"), new Nric("S1127477C"), new Phone("87434807"),
                    new Email("alexpham@example.com"), new Address("Blk 48 Buangkok Street 29, #13-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Alexandra Yeoh"), new Nric("S9185653J"), new Phone("87435807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 49 Hougang Street 29, #12-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Mary Yeoh"), new Nric("S7883647D"), new Phone("87436807"),
                    new Email("maryyeoh@example.com"), new Address("Blk 50 Kovan Street 29, #11-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Jane Pham"), new Nric("S0924992C"), new Phone("87437807"),
                    new Email("janepham@example.com"), new Address("Blk 51 Serangoon Street 29, #10-40"),
                    getTagSet("hokkien only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Harry Yeoh"), new Nric("S8836116D"), new Phone("87439807"),
                    new Email("harryyeoh@example.com"), new Address("Blk 52 Punggol Street 29, #09-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Wu Yayi"), new Nric("S0595823G"), new Phone("87938807"),
                    new Email("wuyayi@example.com"), new Address("Blk 53 Sengkang Street 29, #08-40"),
                    getTagSet("chinese only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", "")),
            new Patient(new Name("Megan Fox"), new Nric("S9476463G"), new Phone("87938807"),
                    new Email("meganfox@example.com"), new Address("Blk 54 Hougang Street 29, #07-40"),
                    getTagSet("english only"),
                    new MedicalRecord("12.12.2018", "flu", "tamiflu", ""))
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
