package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.Appointment;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();
    private final String nonEmptyAppointment = PREFIX_DATE + "22.11.2018 "
            + PREFIX_START_TIME + "1300 "
            + PREFIX_END_TIME + "1400 "
            + PREFIX_DOCTOR_NAME + "Alice "
            + PREFIX_DOCTOR_NRIC + "S9380791Z "
            + PREFIX_PATIENT_NAME + "Betty "
            + PREFIX_PATIENT_NRIC + "S6219609B ";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + nonEmptyAppointment;
        ScheduleCommand expectedCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment("22.11.2018", "1300", "1400",
                        "Alice", "S9380791Z", "Betty", "S6219609B"));
        assertParseSuccess(parser, userInput, expectedCommand);
        // no remark

        userInput = targetIndex.getOneBased() + " " + nonEmptyAppointment;
        expectedCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new Appointment("22.11.2018", "1300", "1400",
                "Alice", "S9380791Z", "Betty", "S6219609B"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, ScheduleCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, ScheduleCommand.COMMAND_WORD + " " + nonEmptyAppointment, expectedMessage);
    }
}
