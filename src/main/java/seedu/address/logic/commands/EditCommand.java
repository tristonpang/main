package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.MedicalDepartment;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentManager;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String

            MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    private static final String MESSAGE_ROLE_CONFLICT = "Attempting to edit invalid fields for: ";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (hasRoleConflict(personToEdit, editPersonDescriptor)) {
            throw new CommandException(MESSAGE_ROLE_CONFLICT + personToEdit.getClass().getSimpleName().toUpperCase());
        }

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor, model);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor,
                                             Model model) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        // edit command does not allow editing remarks
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        if (personToEdit instanceof Patient) {
            // edit command does not allow editing medical records
            MedicalRecord updatedMedicalRecord = ((Patient) personToEdit).getMedicalRecord();

            // reflecting the change in appointments of patient
            ArrayList<Appointment> appointmentList = new ArrayList<>(personToEdit.getAppointmentList());
            ArrayList<Appointment> updatedAppointmentList = AppointmentManager
                    .changePatientNameAndNric(personToEdit.getName(),
                    personToEdit.getNric(), updatedName, updatedNric, appointmentList);
            // reflecting the change in appointments of doctors who have an appointment with the patient
            for (Appointment appt : updatedAppointmentList) {
                Person doctorToEdit = model.getPerson(appt.getDoctorNric()).get();
                ArrayList<Appointment> doctorAppointmentList =
                        new ArrayList<>(doctorToEdit.getAppointmentList());
                ArrayList<Appointment> updatedDoctorAppointmentList =
                        AppointmentManager.changePatientNameAndNric(personToEdit.getName(),
                        personToEdit.getNric(), updatedName, updatedNric, doctorAppointmentList);
                Person editedDoctor = new Doctor(doctorToEdit.getName(),
                        doctorToEdit.getNric(), doctorToEdit.getPhone(),
                        doctorToEdit.getEmail(), doctorToEdit.getAddress(), doctorToEdit.getTags(),
                        updatedDoctorAppointmentList, ((Doctor) doctorToEdit).getMedicalDepartment());
                model.updatePerson(doctorToEdit, editedDoctor);
            }
            return new Patient(updatedName, updatedNric, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                    updatedAppointmentList, updatedMedicalRecord);
        } else {
            // Person must be either Patient or Doctor.
            assert personToEdit instanceof Doctor; // Person must be either Patient or Doctor.
            MedicalDepartment updateMedicalDepartment =
                    editPersonDescriptor.getMedicalDepartment().orElse(((Doctor) personToEdit).getMedicalDepartment());

            // reflecting the change in appointments of doctor
            ArrayList<Appointment> appointmentList = new ArrayList<>(personToEdit.getAppointmentList());
            ArrayList<Appointment> updatedAppointmentList = AppointmentManager
                    .changeDoctorNameAndNric(personToEdit.getName(),
                    personToEdit.getNric(), updatedName, updatedNric, appointmentList);
            // reflecting the change in appointments of patients who have an appointment with the doctor
            for (Appointment appt : updatedAppointmentList) {
                Person patientToEdit = model.getPerson(appt.getPatientNric()).get();
                ArrayList<Appointment> patientAppointmentList =
                        new ArrayList<>(patientToEdit.getAppointmentList());
                ArrayList<Appointment> updatedDoctorAppointmentList =
                        AppointmentManager.changeDoctorNameAndNric(personToEdit.getName(),
                                personToEdit.getNric(), updatedName, updatedNric, patientAppointmentList);
                Person editedPatient = new Patient(patientToEdit.getName(),
                        patientToEdit.getNric(), patientToEdit.getPhone(),
                        patientToEdit.getEmail(), patientToEdit.getAddress(), patientToEdit.getTags(),
                        updatedDoctorAppointmentList, ((Patient) patientToEdit).getMedicalRecordLibrary());
                model.updatePerson(patientToEdit, editedPatient);
            }
            return new Doctor(updatedName, updatedNric, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                    updatedAppointmentList, updateMedicalDepartment);
        }
    }

    /**
     * Checks if the given person has valid arguments. If the person is a patient, checks if the Medical
     * Department field is present. Else if the person is a doctor, checks if the NRIC field is present.
     * @param personToEdit the person to edit.
     * @param editPersonDescriptor details to edit the person with
     */
    private static boolean hasRoleConflict(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        if (personToEdit instanceof Patient) {
            return editPersonDescriptor.getMedicalDepartment().isPresent();
        } else {
            assert personToEdit instanceof Doctor;
            return editPersonDescriptor.getNric().isPresent();
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Nric nric;
        private MedicalDepartment medicalDepartment;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setMedicalDepartment(toCopy.medicalDepartment);
            setNric(toCopy.nric);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, nric, medicalDepartment);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setMedicalDepartment(MedicalDepartment medicalDepartment) {
            this.medicalDepartment = medicalDepartment;
        }

        public Optional<MedicalDepartment> getMedicalDepartment() {
            return Optional.ofNullable(medicalDepartment);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }


        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
