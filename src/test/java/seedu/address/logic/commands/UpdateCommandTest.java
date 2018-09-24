package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICAL_RECORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICAL_RECORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MedicalRecord;

public class UpdateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute() throws Exception {
        final String medicalRecord = "Some medical record";
        assertCommandFailure(new UpdateCommand(INDEX_FIRST_PERSON, new MedicalRecord(medicalRecord)), model, commandHistory,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), medicalRecord));
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(INDEX_FIRST_PERSON, new MedicalRecord(VALID_MEDICAL_RECORD_AMY));

        // same values -> returns true
        UpdateCommand commandWithSameValues = new UpdateCommand(INDEX_FIRST_PERSON, new MedicalRecord(VALID_MEDICAL_RECORD_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_SECOND_PERSON, new MedicalRecord(VALID_MEDICAL_RECORD_AMY))));

        // different medical records -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_FIRST_PERSON, new MedicalRecord(VALID_MEDICAL_RECORD_BOB))));
    }

}
