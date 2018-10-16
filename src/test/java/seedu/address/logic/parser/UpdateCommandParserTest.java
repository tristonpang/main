package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TREATMENT;
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
        // medical record without comment
        final MedicalRecord medicalRecordWithoutComment = new MedicalRecord("22.11.2018", "flu",
                "tamiflu", "");
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputWithoutComment = targetIndex.getOneBased() + " "
                + PREFIX_DATE + "22.11.2018 "
                + PREFIX_DIAGNOSIS + "flu "
                + PREFIX_TREATMENT + "tamiflu";
        UpdateCommand expectedCommandWithoutComment = new UpdateCommand(INDEX_FIRST_PERSON,
                medicalRecordWithoutComment);
        assertParseSuccess(parser, userInputWithoutComment, expectedCommandWithoutComment);
        // medical record with comment
        final MedicalRecord medicalRecordWithComment = new MedicalRecord("22.11.2018", "flu",
                "tamiflu", "Some comment!");
        String userInputWithComment = targetIndex.getOneBased() + " "
                + PREFIX_DATE + "22.11.2018 "
                + PREFIX_DIAGNOSIS + "flu "
                + PREFIX_TREATMENT + "tamiflu "
                + PREFIX_COMMENT + "Some comment!";
        UpdateCommand expectedCommandWithComment = new UpdateCommand(INDEX_FIRST_PERSON, medicalRecordWithComment);
        assertParseSuccess(parser, userInputWithComment, expectedCommandWithComment);
    }
    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        // nothing at all
        assertParseFailure(parser, UpdateCommand.COMMAND_WORD, expectedMessage);
    }
}
