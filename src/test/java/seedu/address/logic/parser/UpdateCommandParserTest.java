package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_RECORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.patient.MedicalRecord;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String medicalRecord = "Some medical record.";
        // have medical records
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_MEDICAL_RECORD.toString() + " " + medicalRecord;
        UpdateCommand expectedCommand = new UpdateCommand(INDEX_FIRST_PERSON, new MedicalRecord(medicalRecord));
        assertParseSuccess(parser, userInput, expectedCommand);
        // no medical records
        userInput = targetIndex.getOneBased() + " " + PREFIX_MEDICAL_RECORD.toString();
        expectedCommand = new UpdateCommand(INDEX_FIRST_PERSON, new MedicalRecord(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        // nothing at all
        assertParseFailure(parser, UpdateCommand.COMMAND_WORD, expectedMessage);
    }
}
