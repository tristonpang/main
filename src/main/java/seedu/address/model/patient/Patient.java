package seedu.address.model.patient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedPatient;
import seedu.address.storage.XmlAdaptedPerson;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private ArrayList<MedicalRecord> medicalRecordLibrary = new ArrayList<>();
    private MedicalRecord latestMedicalRecord;

    /**
     * Creates a new Patient object based on given details.
     * An empty {@code MedicalRecord} will be created by default for the patient.
     * All field must be present and non-null.
     */
    public Patient(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, nric, phone, email, address, tags);
        this.latestMedicalRecord = MedicalRecord.DEFAULT_MEDICAL_RECORD;
        this.medicalRecordLibrary = new ArrayList<>();
    }

    /**
     * Creates a Patient object based on given details.
     * All field must be present and non-null.
     */
    public Patient(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                   ArrayList<Appointment> appointmentList, MedicalRecord medicalRecord) {
        super(name, nric, phone, email, address, tags, appointmentList);
        this.latestMedicalRecord = medicalRecord;
        if (!latestMedicalRecord.equals(MedicalRecord.DEFAULT_MEDICAL_RECORD)) {
            this.medicalRecordLibrary.add(medicalRecord);
        }
    }

    public Patient(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                   MedicalRecord medicalRecord) {
        super(name, nric, phone, email, address, tags);
        this.latestMedicalRecord = medicalRecord;
        if (!latestMedicalRecord.equals(MedicalRecord.DEFAULT_MEDICAL_RECORD)) {
            this.medicalRecordLibrary.add(medicalRecord);
        }
    }

    public Patient(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                   ArrayList<Appointment> appointmentList, MedicalRecord medicalRecord,
                   ArrayList<MedicalRecord> medicalRecordLibrary) {
        super(name, nric, phone, email, address, tags, appointmentList);
        this.latestMedicalRecord = medicalRecord;
        this.medicalRecordLibrary = medicalRecordLibrary;
    }

    public Patient(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                   ArrayList<Appointment> appointmentList, ArrayList<MedicalRecord> medicalRecordLibrary) {
        super(name, nric, phone, email, address, tags, appointmentList);
        this.medicalRecordLibrary = medicalRecordLibrary;
        if (!medicalRecordLibrary.isEmpty()) {
            latestMedicalRecord = medicalRecordLibrary.get(0);
        } else {
            latestMedicalRecord = MedicalRecord.DEFAULT_MEDICAL_RECORD;
        }
    }

    public Patient(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                   ArrayList<MedicalRecord> medicalRecordLibrary) {
        super(name, nric, phone, email, address, tags);
        this.medicalRecordLibrary = medicalRecordLibrary;
        if (!medicalRecordLibrary.isEmpty()) {
            latestMedicalRecord = medicalRecordLibrary.get(0);
        } else {
            latestMedicalRecord = MedicalRecord.DEFAULT_MEDICAL_RECORD;
        }
    }

    /**
     * Creates a new Patient object based on given details.
     * All field must be present and non-null.
     */
    public Patient(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags,
                   Appointment appointment, MedicalRecord medicalRecord) {
        super(name, nric, phone, email, address, tags, appointment);
        this.latestMedicalRecord = medicalRecord;
        this.medicalRecordLibrary = new ArrayList<>(Arrays.asList(medicalRecord));
    }

    public MedicalRecord getMedicalRecord() {
        return this.latestMedicalRecord;
    }

    public ArrayList<MedicalRecord> getMedicalRecordLibrary() {
        return this.medicalRecordLibrary;
    }

    /**
     * Creates a keyword array for the {@code Patient}'s {@code MedicalRecord} that lists all the relevant details.
     * For use in {@code PersonContainsKeywordPredicate}.
     */

    public ArrayList<String> getMedicalRecordKeywords() {
        ArrayList<String> keywordsList = new ArrayList<>();
        for (MedicalRecord record : this.medicalRecordLibrary) {
            keywordsList.add(record.getDate());
            keywordsList.add(record.getDiagnosis());
            keywordsList.add(record.getTreatment());
            keywordsList.add(record.getComments());
        }
        return keywordsList;
    }

    /**
     * Check if the patient is the same as other patient.
     * @param otherPatient The other patient to compare to.
     * @return True if the two patient are the same.
     */
    public boolean isSamePerson(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        } else {
            return otherPatient != null && otherPatient.getNric().equals(getNric());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Patient) {
            Patient otherPatient = (Patient) obj;
            return super.equals(otherPatient);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getNric(), getPhone(), getEmail(), getAddress(), getMedicalRecord(),
                getMedicalRecordLibrary(), getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" NRIC: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Medical Records: ")
                .append(getMedicalRecord())
                .append(" Latest Appointment: ")
                .append(getAppointment())
                .append(" AppointmentList: ")
                .append(getAppointmentList())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a Xml Adapted version of this patient object.
     * Overwrites the method in the super class, {@code Person}.
     * Asserts that this person is an instance of a Patient.
     */
    public XmlAdaptedPerson toXmlVersion(Person source) {
        assert source instanceof Patient;
        return XmlAdaptedPatient.adaptToXml((Patient) source);
    }
}
