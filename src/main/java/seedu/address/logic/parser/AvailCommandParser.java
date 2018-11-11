package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.commands.AvailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Date;
import seedu.address.model.person.Time;

/**
 * Parses input arguments and creates a new SwitchCommand object.
 */
public class AvailCommandParser implements Parser<AvailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * If there are no arguments, then return a regular ListCommand (unfiltered list).
     * Else, ensures that arguments conform to format and returns a filtered list of
     * Doctors or Patients, as per specified.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AvailCommand parse(String args) throws ParseException {
        Date date;
        Time startTime;
        Time endTime;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DATE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AvailCommand.MESSAGE_USAGE));
        }


        if (!isValidTimeInput(argMultimap, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(AvailCommand.MESSAGE_INVALID_TIME_INPUT,
                    AvailCommand.MESSAGE_USAGE));
        }

        if (hasNoDateInput(argMultimap, PREFIX_DATE)) {
            date = Date.getCurrentDate();
        } else {
            try {
                date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage());
            }
        }

        if (hasNoTimeInput(argMultimap, PREFIX_START_TIME, PREFIX_END_TIME)) {
            startTime = Time.getCurrentTime();
            endTime = null;
        } else {
            try {
                startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
                endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage());
            }
        }

        return new AvailCommand(date, startTime, endTime);
    }

    /**
     * Returns true if the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getValue(prefix).isPresent();
    }

    /**
     * Helper method to check that either both start time and end time prefix are present or absent.
     */
    private static boolean isValidTimeInput(ArgumentMultimap argumentMultimap, Prefix startTime, Prefix endTime) {
        if (isPrefixPresent(argumentMultimap, startTime) && isPrefixPresent(argumentMultimap, endTime)) {
            return true;
        } else if (!isPrefixPresent(argumentMultimap, startTime) && !isPrefixPresent(argumentMultimap, endTime)) {
            return true;
        }
        return false;
    }

    /**
     * Helper method to check for the presence of Date prefix.
     */
    private static boolean hasNoDateInput(ArgumentMultimap argumentMultimap, Prefix date) {
        return !isPrefixPresent(argumentMultimap, date);
    }

    /**
     * Helper method to check for the presence of Time prefix.
     */
    private static boolean hasNoTimeInput(ArgumentMultimap argumentMultimap, Prefix startTime, Prefix endTime) {
        return !isPrefixPresent(argumentMultimap, startTime) && !isPrefixPresent(argumentMultimap, endTime);
    }
}
