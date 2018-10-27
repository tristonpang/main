package seedu.address.model.argumentmanagers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;

public class FindArgumentManager extends ArgumentManager {
    public static final String FIND_SEARCH_FIELDS_INSTRUCTION = "Please select what fields to search in, by typing"
            + "down the corresponding numbers, separated by spaces:\n"
            + "1. Global Search\n"
            + "2. Search by Name\n"
            + "3. Search by Phone\n"
            + "4. Search by Email\n"
            + "5. Search by Address\n"
            + "6. Search by Tags";
    public static final String FIND_GLOBAL_INSTRUCTION = "Please enter keywords to be searched everywhere, "
            + "separated only by commas";
    public static final String FIND_NAME_INSTRUCTION = "Please enter keywords to be searched for (by name), "
            + "separated only by commas";
    public static final String FIND_PHONE_INSTRUCTION = "Please enter keywords to be searched for (by phone number), "
            + "separated only by commas";
    public static final String FIND_EMAIL_INSTRUCTION = "Please enter keywords to be searched for (by email), "
            + "separated only by commas";
    public static final String FIND_ADDRESS_INSTRUCTION = "Please enter keywords to be searched for (by address), "
            + "separated only by commas";
    public static final String FIND_TAGS_INSTRUCTION = "Please enter keywords to be searched for (by tags), "
            + "separated only by commas";

    public static final String FIND_INVALID_FIELDS_MESSAGE = "Index must be a non-zero positive integer "
            + "from %1$s to %2$s";

    private static final int FIND_MAX_ARGUMENTS = 7;
    private static final int FIND_SEARCH_FIELDS_INDEX = 0;
    private static final int FIND_GLOBAL_INDEX = 1;
    private static final int FIND_NAME_INDEX = 2;
    private static final int FIND_PHONE_INDEX = 3;
    private static final int FIND_EMAIL_INDEX = 4;
    private static final int FIND_ADDRESS_INDEX = 5;
    private static final int FIND_TAGS_INDEX = 6;


    @Override
    public int addArgumentForCommand(List<String> arguments, int argumentIndex, String userInput) {
        arguments.add(userInput);

        if (arguments.get(FIND_SEARCH_FIELDS_INDEX).isEmpty()) {
            //all remaining unselected fields are empty
            for (int index = argumentIndex + 1; index < FIND_MAX_ARGUMENTS; index++) {
                arguments.add("");
            }
            return FIND_MAX_ARGUMENTS;
        }

        //first element is the next edit index, second element is the remaining indexes
        String[] firstIndexAndRemainingIndexes = arguments.get(FIND_SEARCH_FIELDS_INDEX)
                .split(" ", 2);
        int nextIndex = Integer.valueOf(firstIndexAndRemainingIndexes[0].trim());

        //the unselected indexes in between the current index and the next index are empty
        for (int index = argumentIndex + 1; index < nextIndex; index++) {
            arguments.add("");
        }

        //update remaining indexes
        arguments.remove(FIND_SEARCH_FIELDS_INDEX);
        if (firstIndexAndRemainingIndexes.length <= 1) {
            arguments.add(FIND_SEARCH_FIELDS_INDEX, "");
            return nextIndex;
        }
        String remainingIndexes = firstIndexAndRemainingIndexes[1];
        arguments.add(FIND_SEARCH_FIELDS_INDEX, remainingIndexes);
        return nextIndex;
    }

    @Override
    public String retrieveInstruction(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case FIND_SEARCH_FIELDS_INDEX:
            return FIND_SEARCH_FIELDS_INSTRUCTION;

        case FIND_GLOBAL_INDEX:
            return FIND_GLOBAL_INSTRUCTION;

        case FIND_NAME_INDEX:
            return FIND_NAME_INSTRUCTION;

        case FIND_PHONE_INDEX:
            return FIND_PHONE_INSTRUCTION;

        case FIND_EMAIL_INDEX:
            return FIND_EMAIL_INSTRUCTION;

        case FIND_ADDRESS_INDEX:
            return FIND_ADDRESS_INSTRUCTION;

        case FIND_TAGS_INDEX:
            return FIND_TAGS_INSTRUCTION;

        case FIND_MAX_ARGUMENTS:
            return COMMAND_COMPLETE_MESSAGE;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public int removeArgumentForCommand(List<String> arguments, int argumentIndex) {
        arguments.clear();
        return FIND_SEARCH_FIELDS_INDEX;
    }

    @Override
    public int getMaximumArguments() {
        return FIND_MAX_ARGUMENTS;
    }

    @Override
    public String prepareArguments(List<String> arguments) {
        String preparedString = "";
        preparedString += FindCommand.COMMAND_WORD + " ";

        for (int index = MIN_ARGUMENT_INDEX; index < FIND_MAX_ARGUMENTS; index++) {
            if (index == FIND_SEARCH_FIELDS_INDEX) {
                continue;
            }

            String argument = arguments.get(index);
            if (argument.isEmpty()) {
                continue;
            }

            preparedString += prefixFindArgument(index, argument); //TODO: optimise with StringBuilder
            preparedString += " ";
        }

        return preparedString.trim();
    }

    /**
     * Given an argument and an index that represents which field this argument belongs to in the 'find' command,
     * prefix and return the edited argument.
     *
     * @param index    the index that represents which field the argument belongs to in the 'find' command
     * @param argument the specified argument
     * @return the prefixed argument
     */
    private String prefixFindArgument(int index, String argument) {
        String resultArg;
        switch (index) {

        case FIND_GLOBAL_INDEX:
            return argument.replace(",", " ").trim();

        case FIND_NAME_INDEX:
            resultArg = PREFIX_NAME + argument;
            return resultArg.replace(",", " " + PREFIX_NAME).trim();

        case FIND_PHONE_INDEX:
            resultArg = PREFIX_PHONE + argument;
            return resultArg.replace(",", " " + PREFIX_PHONE).trim();

        case FIND_EMAIL_INDEX:
            resultArg = PREFIX_EMAIL + argument;
            return resultArg.replace(",", " " + PREFIX_EMAIL).trim();

        case FIND_ADDRESS_INDEX:
            resultArg = PREFIX_ADDRESS + argument;
            return resultArg.replace(",", " " + PREFIX_ADDRESS).trim();

        case FIND_TAGS_INDEX:
            resultArg = PREFIX_TAG + argument;
            return resultArg.replace(",", " " + PREFIX_TAG).trim();

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public boolean isArgumentValid(List<String> arguments, int argumentIndex, String userInput) {
        switch (argumentIndex) {

        case FIND_SEARCH_FIELDS_INDEX:
            for (String index : userInput.split(" ")) {
                if (!StringUtil.isNonZeroUnsignedInteger(index)
                        || Integer.valueOf(index) > FIND_TAGS_INDEX) {
                    return false;
                }
            }
            // Fallthrough

        default:
            return true;

        }
    }

    @Override
    public String retrieveInvalidArgumentExceptionMessage(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case FIND_SEARCH_FIELDS_INDEX:
            return String.format(FIND_INVALID_FIELDS_MESSAGE,
                    FIND_GLOBAL_INDEX,
                    FIND_TAGS_INDEX);

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public boolean isCurrentFieldSkippable(int argumentIndex) {
        return false;
    }
}
