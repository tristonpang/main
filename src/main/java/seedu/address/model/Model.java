package seedu.address.model;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Switches the database base on the predicate */
    void changeDatabase(Predicate<Person> filer, String role);

    /** Deletes all person in the current active database only **/
    void clearActiveDatabase();

    /** Returns a message that indicates the type of the current active database **/
    String getCurrentDatabase();

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the database.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if there exist a person with matching {@code nric} and {@code name} in the database
     */
    boolean hasSuchPerson(Name name, Nric nric);

    /**
     * Returns true if there exist a patient with matching {@code nric} and {@code name} in the database
     */
    boolean hasSuchPatient(Name name, Nric nric);


    /**
     * Returns true if there exist a doctor with matching {@code nric} and {@code name} in the database
     */
    boolean hasSuchDoctor(Name name, Nric nric);


    /**
     * Returns an {@code Optional<Person>} that matches the given {@code nric}.
     */
    Optional<Person> getPerson(Nric nric);

    /**
     * Deletes the given person.
     * The person must exist in the database.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the database.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the database.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the database.
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous database states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone database states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's database to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's database to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current database state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Records the user's input when an intuitive command is executing
     * @param entry User's input for the current intuitive prompted field
     * @return The next instruction prompt (for the next field to be recorded)
     */
    String addIntuitiveEntry(String entry) throws CommandException;

    /**
     * Removes the user's most recent input during an intuitive command.
     * Invoked when user wants to undo their most recent entry/input.
     * @return The previous field's instruction prompt
     */
    String removeIntuitiveEntry();

    boolean isIntuitiveMode();

    boolean areIntuitiveArgsAvailable();

    /**
     * Retrieves all stored arguments input by the user throughout the intuitive command
     * and returns the arguments as a concatenated string, ready for normal command parsing.
     * Invoked when the intuitive command ends.
     * @return Concatenated string of all arguments input by the user during intuitive command
     */
    String retrieveIntuitiveArguments();

    /**
     * Cancels the currently executing intuitive command, if any.
     */
    void cancelIntuitiveCommand();
}
