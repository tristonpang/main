package seedu.address.model.argumentmanagers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * ArgumentManager that handles management and recording of arguments for an intuitive 'edit' command.
 */
public class EditArgumentManager extends ArgumentManager {
    public static final String EDIT_CLEAR_TAGS_COMMAND = "--";

    public static final String EDIT_TARGET_INSTRUCTION = "Please enter the index of the person to be edited";
    public static final String EDIT_FIELDS_INSTRUCTION = "Please indicate which fields you want to edit, by typing "
            + "down the corresponding numbers, separated by spaces:\n"
            + "1. Name, "
            + "2. Phone, "
            + "3. Email, "
            + "4. Address, "
            + "5. Tags";
    public static final String EDIT_NAME_INSTRUCTION = "Please enter person's new name";
    public static final String EDIT_EMAIL_INSTRUCTION = "Please enter person's new email";
    public static final String EDIT_PHONE_INSTRUCTION = "Please enter person's new phone number";
    public static final String EDIT_ADDRESS_INSTRUCTION = "Please enter person's new address";
    public static final String EDIT_TAGS_INSTRUCTION = "Please enter person's new tags, "
            + "separated by commas (with no spaces after a comma) (Type %1$s to clear tags)";

    public static final String EDIT_INVALID_FIELDS_MESSAGE = "Indexes must be non-zero positive integers "
            + "from %1$s to %2$s, and cannot be repeated";
    public static final String EDIT_INVALID_INDEX_MESSAGE = "Index must be a non-zero positive integer";

    private static final int EDIT_MAX_ARGUMENTS = 7;
    private static final int EDIT_TARGET_INDEX = 0;
    private static final int EDIT_FIELDS_INDEX = 1;
    private static final int EDIT_NAME_INDEX = 2;
    private static final int EDIT_PHONE_INDEX = 3;
    private static final int EDIT_EMAIL_INDEX = 4;
    private static final int EDIT_ADDRESS_INDEX = 5;
    private static final int EDIT_TAGS_INDEX = 6;

    private static final int EDIT_INDEX_OFFSET = 1;


    @Override
    public int addArgumentForCommand(List<String> arguments, int argumentIndex, String userInput) {
        //sort the selected indexes
        if (argumentIndex == EDIT_FIELDS_INDEX) {
            List<String> rawIndexes = Arrays.asList(userInput.split(" "));
            rawIndexes.sort((o1, o2) -> Integer.valueOf(o1) - Integer.valueOf(o2));
            String sortedIndexes = "";
            for (String index : rawIndexes) {
                sortedIndexes += index;
                sortedIndexes += " ";
            }
            sortedIndexes = sortedIndexes.trim();
            arguments.add(sortedIndexes);
        } else {
            arguments.add(userInput);
        }

        if (argumentIndex == EDIT_TARGET_INDEX) {
            return argumentIndex + 1;
        }

        if (arguments.get(EDIT_FIELDS_INDEX).isEmpty()) { //no more edit indexes
            //all remaining unselected fields are empty
            for (int index = argumentIndex + 1; index < EDIT_MAX_ARGUMENTS; index++) {
                arguments.add("");
            }
            return EDIT_MAX_ARGUMENTS;
        }

        //first element is the next edit index, second element is the remaining indexes
        String[] firstIndexAndRemainingIndexes = arguments.get(EDIT_FIELDS_INDEX)
                .split(" ", 2);
        int nextIndex = Integer.valueOf(firstIndexAndRemainingIndexes[0].trim());
        nextIndex += EDIT_INDEX_OFFSET;
        //the unselected indexes in between the current index and the next index are empty
        for (int index = argumentIndex + 1; index < nextIndex; index++) {
            arguments.add("");
        }

        //update remaining indexes
        arguments.remove(EDIT_FIELDS_INDEX);
        if (firstIndexAndRemainingIndexes.length <= 1) {
            arguments.add(EDIT_FIELDS_INDEX, "");
            return nextIndex;
        }
        String remainingIndexes = firstIndexAndRemainingIndexes[1];
        arguments.add(EDIT_FIELDS_INDEX, remainingIndexes);
        return nextIndex;
    }

    @Override
    public String retrieveInstruction(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case EDIT_TARGET_INDEX:
            return EDIT_TARGET_INSTRUCTION;

        case EDIT_FIELDS_INDEX:
            return EDIT_FIELDS_INSTRUCTION;

        case EDIT_NAME_INDEX:
            return EDIT_NAME_INSTRUCTION;

        case EDIT_PHONE_INDEX:
            return EDIT_PHONE_INSTRUCTION;

        case EDIT_EMAIL_INDEX:
            return EDIT_EMAIL_INSTRUCTION;

        case EDIT_ADDRESS_INDEX:
            return EDIT_ADDRESS_INSTRUCTION;

        case EDIT_TAGS_INDEX:
            return String.format(EDIT_TAGS_INSTRUCTION, EDIT_CLEAR_TAGS_COMMAND);

        case EDIT_MAX_ARGUMENTS:
            return COMMAND_COMPLETE_MESSAGE;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);
        }
    }

    @Override
    public int removeArgumentForCommand(List<String> arguments, int argumentIndex) {
        if (argumentIndex == EDIT_FIELDS_INDEX) {
            arguments.remove(argumentIndex - 1);
            return argumentIndex - 1;
        }

        String targetIndex = arguments.get(EDIT_TARGET_INDEX);
        arguments.clear();
        arguments.add(targetIndex);
        return EDIT_FIELDS_INDEX;
    }

    @Override
    public int getMaximumArguments() {
        return EDIT_MAX_ARGUMENTS;
    }

    @Override
    public String prepareArguments(List<String> arguments) {
        StringBuilder preparedString = new StringBuilder();
        preparedString.append(EditCommand.COMMAND_WORD + " ");

        for (int index = MIN_ARGUMENT_INDEX; index < EDIT_MAX_ARGUMENTS; index++) {
            if (index == EDIT_FIELDS_INDEX) {
                continue;
            }

            String argument = arguments.get(index);
            if (argument.isEmpty()) {
                continue;
            }

            preparedString.append(prefixEditArgument(index, argument));
            preparedString.append(" ");
        }

        return preparedString.toString().trim();
    }

    /**
     * Given an argument and an index that represents which field this argument belongs to in the 'edit' command,
     * prefix and return the edited argument.
     *
     * @param index    the index that represents which field the argument belongs to in the 'edit' command
     * @param argument the specified argument
     * @return the prefixed argument
     */
    private String prefixEditArgument(int index, String argument) {
        switch (index) {

        case EDIT_TARGET_INDEX:
            return argument;

        case EDIT_NAME_INDEX:
            return PREFIX_NAME + argument;

        case EDIT_EMAIL_INDEX:
            return PREFIX_EMAIL + argument;

        case EDIT_PHONE_INDEX:
            return PREFIX_PHONE + argument;

        case EDIT_ADDRESS_INDEX:
            return PREFIX_ADDRESS + argument;

        case EDIT_TAGS_INDEX:
            String resultArg = PREFIX_TAG + argument;
            if (argument.equals(EDIT_CLEAR_TAGS_COMMAND)) {
                resultArg = PREFIX_TAG + "";
            }

            return resultArg.replace(",", " " + PREFIX_TAG).trim();

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);
        }
    }

    @Override
    public boolean isArgumentValid(List<String> arguments, int argumentIndex, String userInput) {
        switch (argumentIndex) {

        case EDIT_TARGET_INDEX:
            return StringUtil.isNonZeroUnsignedInteger(userInput);

        case EDIT_FIELDS_INDEX:
            String appearedValues = "";
            for (String index : userInput.split(" ")) {
                if (!StringUtil.isNonZeroUnsignedInteger(index)
                        || Integer.valueOf(index) >= EDIT_MAX_ARGUMENTS) {
                    return false;
                }
                if (appearedValues.contains(index)) {
                    return false;
                }
                appearedValues += index;
            }
            return true;

        case EDIT_NAME_INDEX:
            return Name.isValidName(userInput);

        case EDIT_PHONE_INDEX:
            return Phone.isValidPhone(userInput);

        case EDIT_EMAIL_INDEX:
            return Email.isValidEmail(userInput);

        case EDIT_ADDRESS_INDEX:
            return Address.isValidAddress(userInput);

        case EDIT_TAGS_INDEX:
            if (userInput.equals(EDIT_CLEAR_TAGS_COMMAND)) {
                return true;
            }

            for (String tag : userInput.split(",")) {
                if (!Tag.isValidTagName(tag)) {
                    return false;
                }
            }
            return true;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public String retrieveInvalidArgumentExceptionMessage(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case EDIT_TARGET_INDEX:
            return EDIT_INVALID_INDEX_MESSAGE;

        case EDIT_FIELDS_INDEX:
            return String.format(EDIT_INVALID_FIELDS_MESSAGE,
                    EDIT_NAME_INDEX - EDIT_INDEX_OFFSET,
                    EDIT_TAGS_INDEX - EDIT_INDEX_OFFSET);

        case EDIT_NAME_INDEX:
            return Name.MESSAGE_NAME_CONSTRAINTS;

        case EDIT_PHONE_INDEX:
            return Phone.MESSAGE_PHONE_CONSTRAINTS;

        case EDIT_EMAIL_INDEX:
            return Email.MESSAGE_EMAIL_CONSTRAINTS;

        case EDIT_ADDRESS_INDEX:
            return Address.MESSAGE_ADDRESS_CONSTRAINTS;

        case EDIT_TAGS_INDEX:
            return Tag.MESSAGE_TAG_CONSTRAINTS;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public boolean isCurrentFieldSkippable(int argumentIndex) {
        return false;
    }

    @Override
    public String getArgumentManagerType() {
        return EditCommand.COMMAND_WORD;
    }
}
