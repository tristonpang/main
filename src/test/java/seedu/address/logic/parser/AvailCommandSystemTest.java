package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SECOND_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SECOND_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SECOND_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AvailCommand;
import seedu.address.model.person.Date;
import seedu.address.model.person.Time;

public class AvailCommandSystemTest {
    private AvailCommandParser parser = new AvailCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC + START_TIME_DESC + END_TIME_DESC,
                new AvailCommand(new Date(VALID_DATE), new Time(VALID_START_TIME), new Time(VALID_END_TIME)));

        // multiple dates - last date accepted
        assertParseSuccess(parser, DATE_DESC + SECOND_DATE_DESC + START_TIME_DESC + END_TIME_DESC,
                new AvailCommand(new Date(VALID_DATE_SECOND), new Time(VALID_START_TIME), new Time(VALID_END_TIME)));

        // multiple start time - last start time accepted
        assertParseSuccess(parser, DATE_DESC + SECOND_START_TIME_DESC + START_TIME_DESC
                + END_TIME_DESC, new AvailCommand(new Date(VALID_DATE), new Time(VALID_START_TIME),
                new Time(VALID_END_TIME)));

        // multiple end time - last end time accepted
        assertParseSuccess(parser, DATE_DESC + START_TIME_DESC + SECOND_END_TIME_DESC
                + END_TIME_DESC, new AvailCommand(new Date(VALID_DATE), new Time(VALID_START_TIME),
                new Time(VALID_END_TIME)));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no date
        assertParseSuccess(parser, START_TIME_DESC + END_TIME_DESC, new AvailCommand(Date.getCurrentDate(),
                new Time(VALID_START_TIME), new Time(VALID_END_TIME)));

        // no start and end time
        assertParseSuccess(parser, DATE_DESC, new AvailCommand(new Date(VALID_DATE), Time.getCurrentTime(),
                Time.getCurrentTime()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(AvailCommand.MESSAGE_INVALID_TIME_INPUT);

        // missing end time prefix but start time prefix present
        assertParseFailure(parser, DATE_DESC + START_TIME_DESC, expectedMessage);

        // missing start time prefix but end time prefix present
        assertParseFailure(parser, DATE_DESC + END_TIME_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid start time
        assertParseFailure(parser, DATE_DESC + INVALID_START_TIME_DESC + END_TIME_DESC, (new Time("111111"))
                .getFailureReason());

        // invalid end time
        assertParseFailure(parser, DATE_DESC + START_TIME_DESC + INVALID_END_TIME_DESC, (new Time("111111"))
                .getFailureReason());

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC, (new Date("23.13")).getInvalidReason());

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, DATE_DESC + INVALID_START_TIME_DESC
                + INVALID_END_TIME_DESC, (new Time("111111")).getFailureReason());

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + DATE_DESC + START_TIME_DESC + END_TIME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AvailCommand.MESSAGE_USAGE));
    }
}
