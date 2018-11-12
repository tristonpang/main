package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.SwitchCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Role;

/**
 * Parses input arguments and creates a new SwitchCommand object.
 */
public class SwitchCommandParser implements Parser<SwitchCommand> {
    private static final String LIST_EVERYTHING = "ALL";

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * If there are no arguments, then return a regular ListCommand (unfiltered list).
     * Else, ensures that arguments conform to format and returns a filtered list of
     * Doctors or Patients, as per specified.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwitchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROLE);

        if (!isPrefixPresent(argMultimap, PREFIX_ROLE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SwitchCommand.MESSAGE_USAGE));
        }

        if (listAllPersons(argMultimap.getValue(PREFIX_ROLE).get())) {
            return new SwitchCommand(PREDICATE_SHOW_ALL_PERSONS, LIST_EVERYTHING);
        }

        try {
            Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
            return new SwitchCommand((person -> person.getClass().getSimpleName().equalsIgnoreCase(role.toString())),
                    role.toString());
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage() + " or 'All' (case-insensitive)");
        }

    }

    /**
     * Returns true if the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getValue(prefix).isPresent();
    }

    /**
     * Helper method to check if user wants to list all persons, regardless of role, base on user's command input.
     */
    private static boolean listAllPersons(String args) {
        String trimmedArgs = args.trim();
        return trimmedArgs.equalsIgnoreCase(LIST_EVERYTHING);
    }
}
