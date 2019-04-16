package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GLOBAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_RECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @param args is a non-empty String.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        final ArrayList<Prefix> prefixList = new ArrayList<>(Arrays.asList(PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ROLE, PREFIX_TAG, PREFIX_MEDICAL_DEPARTMENT,
                PREFIX_MEDICAL_RECORD));

        // Checks if any of the prefixes entered are invalid.
        List<String> allPrefixes = new ArrayList<>();
        Matcher m = Pattern.compile("\\s\\w+/").matcher(args);
        while (m.find()) {
            allPrefixes.add(m.group().trim());
        }
        for (String s : allPrefixes) {
            if (prefixList.stream().noneMatch(x -> s.equals(x.toString()))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixList.toArray(new Prefix[0]));
        Map<Prefix, List<String>> personSearchKeywords = new HashMap<>();

        if (!argMultimap.getPreamble().equals("")) {
            List<String> keywordList = new ArrayList<>(Arrays.asList(argMultimap.getPreamble().split("\\s+")));
            personSearchKeywords.put(PREFIX_GLOBAL, keywordList);
        }
        for (Prefix prefix : prefixList) {
            initialiseKeywordMap(argMultimap, personSearchKeywords, prefix);
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(personSearchKeywords));
    }

    /**
     * Private method for handling {@code Prefix} in the input argument.
     * Maps each {@code Prefix} into a HashMap with their searched keywords.
     *
     * @param argMultimap Immutable HashMap of all given keywords for each {@code Prefix} mapped to {@code Prefix}.
     * @param personSearchKeywords HashMap of individual keywords in an ArrayList mapped to {@code Prefix}.
     * @param prefix {@code Prefix} corresponding to the keywords given.
     */
    private void initialiseKeywordMap(ArgumentMultimap argMultimap, Map<Prefix, List<String>> personSearchKeywords,
                                      Prefix prefix) throws ParseException {
        if (argMultimap.getValue(prefix).isPresent()) {
            List<String> keywordList = new ArrayList<>(argMultimap.getAllValues(prefix));
            if (keywordList.get(0).equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            personSearchKeywords.put(prefix, keywordList);
        }
    }

}
