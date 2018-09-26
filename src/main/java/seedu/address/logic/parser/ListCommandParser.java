package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Role;

public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        if(args.length() == 0) {
            return new ListCommand();
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROLE);

        if (!isPrefixPresent(argMultimap, PREFIX_ROLE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }

        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        return new ListCommand(role);
    }

    public static boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getValue(prefix).isPresent();
    }
}
