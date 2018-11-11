package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.DatabaseChangedEvent;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private Predicate<Person> PREDICATE_SHOW_RELEVANT_PERSONS;
    private final IntuitivePromptManager intuitivePromptManager;
    private final String KEYWORD_ALL = "ALL";

    private String activeRole;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        // starts the application with the all patient and doctor's database by default.
        PREDICATE_SHOW_RELEVANT_PERSONS = PREDICATE_SHOW_ALL_PERSONS;
        activeRole = KEYWORD_ALL;

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons =
                new FilteredList<>(versionedAddressBook.getPersonList()).filtered(this.PREDICATE_SHOW_RELEVANT_PERSONS);

        intuitivePromptManager = new IntuitivePromptManager();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void changeDatabase(Predicate<Person> filer, String role) {
        this.PREDICATE_SHOW_RELEVANT_PERSONS = filer;
        this.activeRole = role;
        this.indicateDatabaseChanged();
        this.indicatePersonChanged();
    }

    @Override
    public void clearActiveDatabase() {
        List<Person> toDelete = versionedAddressBook.getPersonList().stream()
                .filter(this.PREDICATE_SHOW_RELEVANT_PERSONS).collect(Collectors.toList());
        toDelete.stream().forEach(person -> deletePerson(person));
        this.indicatePersonChanged();
    }

    @Override
    public String getCurrentDatabase() {
        return activeRole;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
        indicatePersonChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raise an event to indicate that the active database has been changed **/
    private void indicateDatabaseChanged() {
        raise(new DatabaseChangedEvent(activeRole));
    }

    /** Raises an event to indicate the person data has changed */
    private void indicatePersonChanged(Person original, Person editedPerson) {
        raise(new PersonChangedEvent(original, editedPerson));
    }

    /** Raise an event to indicate the person has been removed or reset to previous version*/
    private void indicatePersonChanged() {
        raise(new PersonChangedEvent(null, null));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasSuchPerson(Name name, Nric nric) {
        requireAllNonNull(name, nric);
        return versionedAddressBook.hasSuchPerson(name, nric);
    }

    @Override
    public boolean hasSuchPatient(Name name, Nric nric) {
        requireAllNonNull(name, nric);
        return versionedAddressBook.hasSuchPatient(name, nric);
    }

    @Override
    public boolean hasSuchDoctor(Name name, Nric nric) {
        requireAllNonNull(name, nric);
        return versionedAddressBook.hasSuchDoctor(name, nric);
    }

    @Override
    public Optional<Person> getPerson(Nric nric) {
        requireNonNull(nric);
        return versionedAddressBook.getPerson(nric);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
        indicatePersonChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        indicatePersonChanged(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(this.PREDICATE_SHOW_RELEVANT_PERSONS.and(predicate));
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
        indicatePersonChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
        indicatePersonChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    @Override
    public String addIntuitiveEntry(String entry) throws CommandException {
        intuitivePromptManager.addArgument(entry);
        return intuitivePromptManager.getInstruction();
    }

    @Override
    public String removeIntuitiveEntry() {
        intuitivePromptManager.removeArgument();
        return intuitivePromptManager.getInstruction();
    }

    @Override
    public boolean isIntuitiveMode() {
        return intuitivePromptManager.isIntuitiveMode();
    }

    @Override
    public String retrieveIntuitiveArguments() {
        return intuitivePromptManager.retrieveArguments();
    }

    @Override
    public boolean areIntuitiveArgsAvailable() {
        return intuitivePromptManager.areArgsAvailable();
    }

    @Override
    public void cancelIntuitiveCommand() {
        intuitivePromptManager.cancelIntuitiveCommand();
    }

}
