package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GLOBAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // input between two valid prefixes is empty
        assertParseFailure(parser, " n/ p/91919191 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // input after last valid prefix is empty
        assertParseFailure(parser, " n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // prefix not in valid list
        assertParseFailure(parser, " role/doctor",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // mix of valid and invalid prefixes
        assertParseFailure(parser, " n/alex role/doctor",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        Map<Prefix, List<String>> testMap = Map.of(PREFIX_GLOBAL, new ArrayList<>(Arrays.asList("Alice", "Bob")));
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(testMap));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        testMap = Map.of(PREFIX_NAME, new ArrayList<>(Arrays.asList("Alice", "Bob")),
                PREFIX_TAG, new ArrayList<>(Arrays.asList("friends")));
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(testMap));

        // valid tags in mixed order
        assertParseSuccess(parser, " n/Alice t/friends n/Bob", expectedFindCommand);

        // space in between tags and prefixes
        assertParseSuccess(parser, " n/ \n Alice \n n/ \t Bob \t t/friends", expectedFindCommand);
    }

}
