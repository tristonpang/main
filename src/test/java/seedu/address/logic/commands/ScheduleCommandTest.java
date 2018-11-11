package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Appointment;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleCommand.
 */
public class ScheduleCommandTest {

    private static final String SCHEDULE_STUB = "23.11.2018,1300,1400,Elle Meyer,S6977714G,Alice Pauline,S3305985Z";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addScheduleUnfilteredList_success() {
        Patient firstPerson = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        firstPerson.clearAppointmentList(); // This prevents appointments collected from different tests to clash.
        Patient editedPerson = new PatientBuilder(firstPerson).withAppointment(SCHEDULE_STUB).build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment(editedPerson.getAppointment().value));

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_APPOINTMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(scheduleCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteScheduleUnfilteredList_success() {
        Patient firstPerson = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        firstPerson.clearAppointmentList(); // This prevents appointments collected from different tests to clash.
        Patient editedPerson = new PatientBuilder(firstPerson)
                .withAppointment("22.11.2018,1300,1400,Elle Meyer,S6977714G,"
                        + firstPerson.getName().toString()
                        + "," + firstPerson.getNric().toString()).build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment(editedPerson.getAppointment().toString()));

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_APPOINTMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(scheduleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Patient firstPerson = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        firstPerson.clearAppointmentList(); // This prevents appointments collected from different tests to clash.
        Patient editedPerson = new PatientBuilder((Patient) model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAppointment(SCHEDULE_STUB).build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment(editedPerson.getAppointment().value));

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_APPOINTMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(scheduleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, new Appointment(VALID_SCHEDULE_BOB));

        assertCommandFailure(scheduleCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, new Appointment(VALID_SCHEDULE_BOB));

        assertCommandFailure(scheduleCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Patient personToModify = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToModify.clearAppointmentList(); // This prevents appointments collected from different tests to clash.
        Patient modifiedPerson = new PatientBuilder(personToModify).withAppointment(SCHEDULE_STUB).build();

        String[] parts = SCHEDULE_STUB.split(",");
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, new Appointment(parts[0],
                parts[1], parts[2], parts[3], parts[4],
                personToModify.getName().toString(), personToModify.getNric().toString()));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // schedule -> first person schedule changed
        scheduleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex,
                new Appointment("22.11.2018,1300,1400,Alice,S3305985Z,Benson Meier,S8234567A"));

        // execution failed -> address book state not added into model
        assertCommandFailure(scheduleCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#Appointment} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment("22.11.2018,1300,1400,Elle Meyer,S6977714G,"
                        + "Benson Meier"
                        + "," + "S1215130F"));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient personToModify = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToModify.clearAppointmentList(); // This prevents appointments collected from different tests to clash.
        Patient modifiedPerson = new PatientBuilder(personToModify)
                .withAppointment("22.11.2018,1300,1400,Elle Meyer,S6977714G,"
                        + personToModify.getName().toString() + ","
                        + personToModify.getNric().toString())
                .build();
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // schedule -> modifies second person in unfiltered person list / first person in filtered person list

        scheduleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void equals() {
        final ScheduleCommand standardCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment(VALID_SCHEDULE_AMY));

        // same values -> returns true
        ScheduleCommand commandWithSameValues = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment(VALID_SCHEDULE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(INDEX_SECOND_PERSON,
                new Appointment(VALID_SCHEDULE_AMY))));

        // different appointment -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment(VALID_SCHEDULE_BOB))));
    }
}
