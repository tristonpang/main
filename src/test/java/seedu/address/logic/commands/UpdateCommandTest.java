package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICAL_RECORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICAL_RECORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
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
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

public class UpdateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_updateMedicalRecord_success() throws Exception {
        Patient editedPatient =
                new PatientBuilder((Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                        .withMedicalRecord("13.12.2018, Diagnosis: flu, Treatment: tamiflu, Comments: -")
                        .build();

        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, editedPatient.getMedicalRecord());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_MEDICAL_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        Patient personInFilteredList = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPerson = new PatientBuilder(personInFilteredList)
                .withMedicalRecord("12.12.2018, Diagnosis: flu, Treatment: tamiflu, Comments: -")
                .build();

        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, editedPerson.getMedicalRecord());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_MEDICAL_RECORD_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, new MedicalRecord(VALID_MEDICAL_RECORD_BOB));
        assertCommandFailure(updateCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, new MedicalRecord(VALID_MEDICAL_RECORD_BOB));

        assertCommandFailure(updateCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where the user tries to edit medical records of a Doctor,
     * which is not allowed.
     */
    @Test
    public void execute_invalidDoctorFilterList_failure() throws Exception {
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIFTH_PERSON,
                new MedicalRecord(VALID_MEDICAL_RECORD_BOB));
        assertCommandFailure(updateCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_CHOSEN);
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new MedicalRecord(VALID_MEDICAL_RECORD_AMY));

        // same values -> returns true
        UpdateCommand commandWithSameValues = new UpdateCommand(INDEX_FIRST_PERSON,
                new MedicalRecord(VALID_MEDICAL_RECORD_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_SECOND_PERSON,
                new MedicalRecord(VALID_MEDICAL_RECORD_AMY))));

        // different medical records -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_FIRST_PERSON,
                new MedicalRecord(VALID_MEDICAL_RECORD_BOB))));
    }
}
