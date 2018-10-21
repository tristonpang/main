package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GLOBAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DOCTOR_NRIC, PREFIX_PATIENT_NRIC);
        
        Map<Prefix, List<String>> personSearchKeywords = new HashMap<>();

        if (!argMultimap.getPreamble().equals("")) {
            List<String> keywordList = new ArrayList<>();
            keywordList.addAll(Arrays.asList(argMultimap.getPreamble().split("\\s+")));
            personSearchKeywords.put(PREFIX_GLOBAL, keywordList);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameList = new ArrayList<>();
            nameList.addAll(argMultimap.getAllValues(PREFIX_NAME));
            personSearchKeywords.put(PREFIX_NAME, nameList);
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> phoneList = new ArrayList<>();
            phoneList.addAll(argMultimap.getAllValues(PREFIX_PHONE));
            personSearchKeywords.put(PREFIX_PHONE, phoneList);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            List<String> emailList = new ArrayList<>();
            emailList.addAll(argMultimap.getAllValues(PREFIX_EMAIL));
            personSearchKeywords.put(PREFIX_EMAIL, emailList);
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            List<String> addressList = new ArrayList<>();
            addressList.addAll(argMultimap.getAllValues(PREFIX_ADDRESS));
            personSearchKeywords.put(PREFIX_ADDRESS, addressList);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tagList = new ArrayList<>();
            tagList.addAll(argMultimap.getAllValues(PREFIX_TAG));
            personSearchKeywords.put(PREFIX_TAG, tagList);
        }

        if (argMultimap.getValue(PREFIX_PATIENT_NRIC).isPresent()) {
            List<String> patientNricList = new ArrayList<>();
            patientNricList.addAll(argMultimap.getAllValues(PREFIX_PATIENT_NRIC));
            personSearchKeywords.put(PREFIX_PATIENT_NRIC, patientNricList);
        }

        if (argMultimap.getValue(PREFIX_DOCTOR_NRIC).isPresent()) {
            List<String> doctorNricList = new ArrayList<>();
            doctorNricList.addAll(argMultimap.getAllValues(PREFIX_DOCTOR_NRIC));
            personSearchKeywords.put(PREFIX_DOCTOR_NRIC, doctorNricList);
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(personSearchKeywords));
    }

}
