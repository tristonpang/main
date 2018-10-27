package seedu.address.model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.doctor.MedicalDepartment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.ui.UiManager;


/**
 * The IntuitivePromptManager of the Model. Handles and stores all data related to an intuitive command
 * execution.
 */
public class IntuitivePromptManager {
    public static final String SKIP_COMMAND = "//";
    public static final String SKIP_INSTRUCTION = "\n(Type %1$s to skip this field)";

    public static final String ADD_ROLE_INSTRUCTION = "Is this a patient or a doctor? "
            + "(Please enter patient or doctor)";
    public static final String ADD_NAME_INSTRUCTION = "Please enter person's name";
    public static final String ADD_EMAIL_INSTRUCTION = "Please enter person's email";
    public static final String ADD_PHONE_INSTRUCTION = "Please enter person's phone number";
    public static final String ADD_ADDRESS_INSTRUCTION = "Please enter person's address";
    public static final String ADD_TAGS_INSTRUCTION = "Please enter person's tags, "
            + "separated by commas (with no spaces after a comma)";
    public static final String ADD_NRIC_INSTRUCTION = "Please enter patient's NRIC";
    public static final String ADD_DEPT_INSTRUCTION = "Please enter doctor's medical department";

    public static final String DELETE_TARGET_INSTRUCTION = "Please enter the index of the person to be deleted";
    public static final String DELETE_INVALID_ARGUMENT_MESSAGE = "Index must be a non-zero positive integer";

    public static final String EDIT_CLEAR_TAGS_COMMAND = "--";

    public static final String EDIT_TARGET_INSTRUCTION = "Please enter the index of the person to be edited";
    public static final String EDIT_FIELDS_INSTRUCTION = "Please indicate which fields you want to edit, by typing"
            + "down the corresponding numbers, separated by spaces:\n"
            + "1. Name\n"
            + "2. Phone\n"
            + "3. Email\n"
            + "4. Address\n"
            + "5. Tags";
    public static final String EDIT_NAME_INSTRUCTION = "Please enter person's new name";
    public static final String EDIT_EMAIL_INSTRUCTION = "Please enter person's new email";
    public static final String EDIT_PHONE_INSTRUCTION = "Please enter person's new phone number";
    public static final String EDIT_ADDRESS_INSTRUCTION = "Please enter person's new address";
    public static final String EDIT_TAGS_INSTRUCTION = "Please enter person's new tags, "
            + "separated by commas (with no spaces after a comma) (Type %1$s to clear tags)";

    public static final String EDIT_INVALID_INDEX_MESSAGE = "Index must be a non-zero positive integer";
    public static final String EDIT_INVALID_FIELDS_MESSAGE = "Index must be a non-zero positive integer "
            + "and must be between %1$s and %2$s";

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
            + "and must be between %1$s and %2$s";

    public static final String PATIENT_ARG_IDENTIFIER = "patient";
    public static final String DOCTOR_ARG_IDENTIFIER = "doctor";

    private static int currentArgIndex;
    private static List<String> arguments;
    private static String commandWord;
    private static boolean isIntuitiveMode;

    private static final int MIN_ARGUMENT_INDEX = 0;

    private static final String COMMAND_COMPLETE_MESSAGE = "All required inputs received, processing...";

    private static final int ADD_MAX_ARGUMENTS = 8;
    private static final int ADD_ROLE_INDEX = 0;
    private static final int ADD_NAME_INDEX = 1;
    private static final int ADD_PHONE_INDEX = 2;
    private static final int ADD_EMAIL_INDEX = 3;
    private static final int ADD_ADDRESS_INDEX = 4;
    private static final int ADD_TAGS_INDEX = 5;
    private static final int ADD_NRIC_INDEX = 6;
    private static final int ADD_DEPT_INDEX = 7;

    private static final int DELETE_MAX_ARGUMENTS = 1;
    private static final int DELETE_TARGET_INDEX = 0;

    private static final int EDIT_MAX_ARGUMENTS = 7;
    private static final int EDIT_TARGET_INDEX = 0;
    private static final int EDIT_FIELDS_INDEX = 1;
    private static final int EDIT_NAME_INDEX = 2;
    private static final int EDIT_PHONE_INDEX = 3;
    private static final int EDIT_EMAIL_INDEX = 4;
    private static final int EDIT_ADDRESS_INDEX = 5;
    private static final int EDIT_TAGS_INDEX = 6;

    private static final int EDIT_INDEX_OFFSET = 1;

    private static final int FIND_MAX_ARGUMENTS = 7;
    private static final int FIND_SEARCH_FIELDS_INDEX = 0;
    private static final int FIND_GLOBAL_INDEX = 1;
    private static final int FIND_NAME_INDEX = 2;
    private static final int FIND_PHONE_INDEX = 3;
    private static final int FIND_EMAIL_INDEX = 4;
    private static final int FIND_ADDRESS_INDEX = 5;
    private static final int FIND_TAGS_INDEX = 6;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String UNEXPECTED_SCENARIO_MESSAGE = "IntuitivePromptManager: "
            + "Unexpected scenario has occurred in switch-case block";


    public IntuitivePromptManager() {
        currentArgIndex = MIN_ARGUMENT_INDEX;
        arguments = new ArrayList<>();
        commandWord = null;
        isIntuitiveMode = false;
    }

    public boolean isIntuitiveMode() {
        return isIntuitiveMode;
    }

    /**
     * Adds and stores user's input as an argument to the currently executing intuitive command.
     *
     * @param input the user's input
     */
    public void addArgument(String input) throws CommandException {
        String userInput = input.trim();
        if (commandWord == null) { //start of intuitive command, record command word
            startIntuitiveMode(userInput);
        } else if (isSkipCommand(userInput) && isCurrentFieldSkippable()) { //skip command
            skipArgumentField();
        } else if (isArgumentValid(userInput)) { //any other valid argument
            addArgumentForCommand(userInput);
        } else {
            String exceptionMessage = retrieveInvalidArgumentExceptionMessage();
            throw new CommandException(exceptionMessage + "\n" + getInstruction());
        }


        logger.info("Intuitive Argument index: " + currentArgIndex);
        logger.info("Current arguments: " + arguments);


        if (currentArgIndex >= getMaximumArguments(commandWord)) {
            exitIntuitiveMode();
        }

    }

    /**
     * Signals the start of execution of an intuitive command
     *
     * @param userInput the user's input (i.e. the command word which specifies which
     *                  command is going to be run in intuitive mode)
     */
    private void startIntuitiveMode(String userInput) {
        commandWord = userInput;
        isIntuitiveMode = true;
    }

    /**
     * Handles the adding of the user's input as an argument, depending on
     * the type of command.
     *
     * @param userInput the user's input to be added as an argument
     */
    private void addArgumentForCommand(String userInput) {
        switch (commandWord) {

        case EditCommand.COMMAND_WORD:
            if (currentArgIndex >= EDIT_FIELDS_INDEX) {
                arguments.add(userInput);

                if (arguments.get(EDIT_FIELDS_INDEX).isEmpty()) { //no more edit indexes
                    //all remaining unselected fields are empty
                    for (int index = currentArgIndex + 1; index < EDIT_MAX_ARGUMENTS; index++) {
                        arguments.add("");
                    }
                    currentArgIndex = EDIT_MAX_ARGUMENTS;
                    break;
                }

                //first element is the next edit index, second element is the remaining indexes
                String[] firstIndexAndRemainingIndexes = arguments.get(EDIT_FIELDS_INDEX)
                        .split(" ", 2);
                int nextIndex = Integer.valueOf(firstIndexAndRemainingIndexes[0].trim());
                nextIndex += EDIT_INDEX_OFFSET;
                //the unselected indexes in between the current index and the next index are empty
                for (int index = currentArgIndex + 1; index < nextIndex; index++) {
                    arguments.add("");
                }

                //assign the argument index to the next in line from the selected edit indexes
                currentArgIndex = nextIndex;

                //update remaining indexes
                arguments.remove(EDIT_FIELDS_INDEX);
                if (firstIndexAndRemainingIndexes.length <= 1) {
                    arguments.add(EDIT_FIELDS_INDEX, "");
                    break;
                }
                String remainingIndexes = firstIndexAndRemainingIndexes[1];
                arguments.add(EDIT_FIELDS_INDEX, remainingIndexes);
                break;
            }
            // Fallthrough

        case DeleteCommand.COMMAND_WORD:
            arguments.add(userInput);
            currentArgIndex++;
            break;

        case FindCommand.COMMAND_WORD:
            arguments.add(userInput);

            if (arguments.get(FIND_SEARCH_FIELDS_INDEX).isEmpty()) {
                //all remaining unselected fields are empty
                for (int index = currentArgIndex + 1; index < FIND_MAX_ARGUMENTS; index++) {
                    arguments.add("");
                }
                currentArgIndex = FIND_MAX_ARGUMENTS;
                break;

            }

            //first element is the next edit index, second element is the remaining indexes
            String[] firstIndexAndRemainingIndexes = arguments.get(FIND_SEARCH_FIELDS_INDEX)
                    .split(" ", 2);
            int nextIndex = Integer.valueOf(firstIndexAndRemainingIndexes[0].trim());

            //the unselected indexes in between the current index and the next index are empty
            for (int index = currentArgIndex + 1; index < nextIndex; index++) {
                arguments.add("");
            }

            //assign the argument index to the next in line from the selected edit indexes
            currentArgIndex = nextIndex;

            //update remaining indexes
            arguments.remove(FIND_SEARCH_FIELDS_INDEX);
            if (firstIndexAndRemainingIndexes.length <= 1) {
                arguments.add(FIND_SEARCH_FIELDS_INDEX, "");
                break;
            }
            String remainingIndexes = firstIndexAndRemainingIndexes[1];
            arguments.add(FIND_SEARCH_FIELDS_INDEX, remainingIndexes);
            break;

        case AddCommand.COMMAND_WORD:
            arguments.add(userInput);
            if (currentArgIndex != ADD_NRIC_INDEX || isDoctor()) {
                currentArgIndex++;
                break;
            }

            if (isPatient()) {
                currentArgIndex = ADD_MAX_ARGUMENTS;
                break;
            }
            // Fallthrough

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);
        }
    }

    /**
     * Skips the current argument field.
     */
    private void skipArgumentField() {
        arguments.add("");
        currentArgIndex++;
    }

    /**
     * Gives the corresponding instruction or prompt for the current field of the executing
     * intuitive command.
     *
     * @return the string instruction for the current field of the executing intuitive command
     */
    public String getInstruction() {
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return retrieveAddInstruction();

        case DeleteCommand.COMMAND_WORD:
            return retrieveDeleteInstruction();

        case EditCommand.COMMAND_WORD:
            return retrieveEditInstruction();

        case FindCommand.COMMAND_WORD:
            return retrieveFindInstruction();

        default:
            return "Invalid";
        }
    }

    /**
     * Indicates that currently executing intuitive command has completed.
     * i.e. all fields have been filled up, intuitive command mode has exited.
     */
    private void exitIntuitiveMode() {
        isIntuitiveMode = false;
    }

    /**
     * Retrieves corresponding instruction for a field (specified by the current argument index) for the intuitive
     * 'add' command.
     *
     * @return the corresponding string instruction for the specified field
     */
    private String retrieveAddInstruction() {
        switch (currentArgIndex) {

        case ADD_ROLE_INDEX:
            return ADD_ROLE_INSTRUCTION;

        case ADD_NAME_INDEX:
            return ADD_NAME_INSTRUCTION;

        case ADD_EMAIL_INDEX:
            return ADD_EMAIL_INSTRUCTION;

        case ADD_PHONE_INDEX:
            return ADD_PHONE_INSTRUCTION;

        case ADD_ADDRESS_INDEX:
            return ADD_ADDRESS_INSTRUCTION;

        case ADD_TAGS_INDEX:
            return ADD_TAGS_INSTRUCTION + String.format(SKIP_INSTRUCTION, SKIP_COMMAND);

        case ADD_NRIC_INDEX:
            return ADD_NRIC_INSTRUCTION;

        case ADD_DEPT_INDEX:
            return ADD_DEPT_INSTRUCTION;

        case ADD_MAX_ARGUMENTS:
            return COMMAND_COMPLETE_MESSAGE;

        default:
            return "Invalid";
        }
    }

    /**
     * Retrieves corresponding instruction for a field (specified by the current argument index) for the intuitive
     * 'delete' command.
     *
     * @return the corresponding string instruction for the specified field
     */
    private String retrieveDeleteInstruction() {
        switch (currentArgIndex) {

        case DELETE_TARGET_INDEX:
            return DELETE_TARGET_INSTRUCTION;

        case DELETE_MAX_ARGUMENTS:
            return COMMAND_COMPLETE_MESSAGE;

        default:
            return "Invalid";

        }
    }

    /**
     * Retrieves corresponding instruction for a field (specified by the current argument index) for the intuitive
     * 'edit' command.
     *
     * @return the corresponding string instruction for the specified field
     */
    private String retrieveEditInstruction() {
        switch (currentArgIndex) {

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

    /**
     * Retrieves corresponding instruction for a field (specified by the current argument index) for the intuitive
     * 'find' command.
     *
     * @return the corresponding string instruction for the specified field
     */
    private String retrieveFindInstruction() {
        switch (currentArgIndex) {

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

    /**
     * Removes the latest stored argument of the currently executing intuitive command.
     */
    public void removeArgument() {
        if (currentArgIndex <= MIN_ARGUMENT_INDEX) {
            return;
        }

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            removeArgumentForAdd();
            return;

        case EditCommand.COMMAND_WORD:
            removeArgumentForEdit();
            return;

        case FindCommand.COMMAND_WORD:
            removeArgumentForFind();
            return;

        default:
            return;

        }
    }

    /**
     * Removes the latest stored argument of the currently executing intuitive "add" command.
     */
    private void removeArgumentForAdd() {
        arguments.remove(currentArgIndex - 1);
        currentArgIndex--;
    }

    /**
     * Removes the latest stored argument(s) of the currently executing intuitive "edit" command,
     * depending on the current field.
     */
    private void removeArgumentForEdit() {
        if (currentArgIndex == EDIT_FIELDS_INDEX) {
            arguments.remove(currentArgIndex - 1);
            currentArgIndex--;
            return;
        }

        String targetIndex = arguments.get(EDIT_TARGET_INDEX);
        arguments.clear();
        arguments.add(targetIndex);
        currentArgIndex = EDIT_FIELDS_INDEX;
    }

    /**
     * Removes the latest stored arguments of the currently executing intuitive "find" command.
     */
    private void removeArgumentForFind() {
        arguments.clear();
        currentArgIndex = FIND_SEARCH_FIELDS_INDEX;
    }

    /**
     * Given a command, retrieves the maximum number of arguments that the specified command takes in.
     *
     * @param commandWord the specified command
     * @return the maximum number of arguments that the command takes in
     */
    private int getMaximumArguments(String commandWord) {
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return ADD_MAX_ARGUMENTS;

        case DeleteCommand.COMMAND_WORD:
            return DELETE_MAX_ARGUMENTS;

        case EditCommand.COMMAND_WORD:
            return EDIT_MAX_ARGUMENTS;

        case FindCommand.COMMAND_WORD:
            return FIND_MAX_ARGUMENTS;

        default:
            return 0;
        }
    }

    /**
     * Checks if there are currently any arguments stored (as part of an executing intuitive command) in the manager.
     *
     * @return true if there are any arguments stored, false otherwise
     */
    public boolean areArgsAvailable() {
        return !arguments.isEmpty();
    }

    /**
     * Prepares a string that is a single line command (i.e. non-intuitive command input) based on all
     * the past arguments entered by the user during the execution of an intuitive command.
     * This string is basically how the user would have entered the command and its
     * arguments if the intuitive command prompt was not used.
     *
     * @return a string that is the non-intuitive command input, containing entered arguments of the
     * past executed intuitive command
     */
    public String retrieveArguments() {

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareArgumentsForAdd();

        case DeleteCommand.COMMAND_WORD:
            return prepareArgumentsForDelete();

        case EditCommand.COMMAND_WORD:
            return prepareArgumentsForEdit();

        case FindCommand.COMMAND_WORD:
            return prepareArgumentsForFind();

        default:
            return "Invalid";
        }
    }

    /**
     * Clears all stored arguments and readies the manager for a new intuitive command.
     */
    private void resetIntuitiveCache() {
        commandWord = null;
        arguments.clear();
        currentArgIndex = MIN_ARGUMENT_INDEX;
    }

    /**
     * Prepares a string that is a single line 'add' command (i.e. non-intuitive 'add') based on all
     * the past arguments entered by the user during the execution of an intuitive 'add' command.
     *
     * @return a string that is the non-intuitive 'add' command input, containing entered arguments of the
     * past executed intuitive 'add'
     */
    private String prepareArgumentsForAdd() {
        String preparedString = "";
        preparedString += AddCommand.COMMAND_WORD + " ";

        int index = MIN_ARGUMENT_INDEX;
        for (String argument : arguments) {
            if (argument.isEmpty()) {
                index++;
                continue;
            }
            preparedString += prefixAddArgument(index, argument); //TODO: optimise with StringBuilder
            preparedString += " ";
            index++;
        }

        resetIntuitiveCache();
        return preparedString.trim();
    }

    /**
     * Prepares a string that is a single line 'delete' command (i.e. non-intuitive 'delete') based on all
     * the past arguments entered by the user during the execution of an intuitive 'delete' command.
     *
     * @return a string that is the non-intuitive 'delete' command input, containing entered arguments of the
     * past executed intuitive 'delete'
     */
    private String prepareArgumentsForDelete() {
        String preparedString = "";
        preparedString += DeleteCommand.COMMAND_WORD + " ";

        String targetIndex = arguments.get(DELETE_TARGET_INDEX);
        preparedString += targetIndex;

        resetIntuitiveCache();
        return preparedString;
    }

    /**
     * Prepares a string that is a single line 'edit' command (i.e. non-intuitive 'edit') based on all
     * the past arguments entered by the user during the execution of an intuitive 'edit' command.
     *
     * @return a string that is the non-intuitive 'edit' command input, containing entered arguments of the
     * past executed intuitive 'edit'
     */
    private String prepareArgumentsForEdit() {
        String preparedString = "";
        preparedString += EditCommand.COMMAND_WORD + " ";

        for (int index = MIN_ARGUMENT_INDEX; index < EDIT_MAX_ARGUMENTS; index++) {
            if (index == EDIT_FIELDS_INDEX) {
                continue;
            }

            String argument = arguments.get(index);
            if (argument.isEmpty()) {
                continue;
            }

            preparedString += prefixEditArgument(index, argument); //TODO: optimise with StringBuilder
            preparedString += " ";
        }

        resetIntuitiveCache();
        return preparedString.trim();
    }

    /**
     * Prepares a string that is a single line 'find' command (i.e. non-intuitive 'find') based on all
     * the past arguments entered by the user during the execution of an intuitive 'find' command.
     *
     * @return a string that is the non-intuitive 'find' command input, containing entered arguments of the
     * past executed intuitive 'find'
     */
    private String prepareArgumentsForFind() {
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

        resetIntuitiveCache();
        return preparedString.trim();
    }

    /**
     * Given an argument and an index that represents which field this argument belongs to in the 'add' command,
     * prefix and return the edited argument.
     *
     * @param index    the index that represents which field the argument belongs to in the 'add' command
     * @param argument the specified argument
     * @return the prefixed argument
     */
    private String prefixAddArgument(int index, String argument) {
        switch (index) {

        case ADD_ROLE_INDEX:
            return PREFIX_ROLE + argument;

        case ADD_NAME_INDEX:
            return PREFIX_NAME + argument;

        case ADD_EMAIL_INDEX:
            return PREFIX_EMAIL + argument;

        case ADD_PHONE_INDEX:
            return PREFIX_PHONE + argument;

        case ADD_ADDRESS_INDEX:
            return PREFIX_ADDRESS + argument;

        case ADD_TAGS_INDEX:
            String resultArg = PREFIX_TAG + argument;
            return resultArg.replace(",", " " + PREFIX_TAG).trim();

        case ADD_NRIC_INDEX:
            return PREFIX_NRIC + argument;

        case ADD_DEPT_INDEX:
            if (argument.isEmpty()) {
                return "";
            }
            return PREFIX_MEDICAL_DEPARTMENT + argument;

        default:
            return "";
        }
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

    private boolean isPatient() {
        return arguments.get(ADD_ROLE_INDEX).equals(PATIENT_ARG_IDENTIFIER);
    }

    private boolean isDoctor() {
        return arguments.get(ADD_ROLE_INDEX).equals(DOCTOR_ARG_IDENTIFIER);
    }

    private boolean isSkipCommand(String input) {
        return input.equals(SKIP_COMMAND);
    }

    /**
     * Checks if the current field of the executing intuitive command is skippable/can be left blank.
     *
     * @return true if the field is skippable, false otherwise
     */
    private boolean isCurrentFieldSkippable() {
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return currentArgIndex == ADD_TAGS_INDEX;

        default:
            return false;
        }
    }

    /**
     * Checks if given input is a valid argument.
     *
     * @param input the given input
     * @return true if the input is a valid argument, false otherwise
     */
    private boolean isArgumentValid(String input) {
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return isAddArgumentValid(input);

        case DeleteCommand.COMMAND_WORD:
            return isDeleteArgumentValid(input);

        case EditCommand.COMMAND_WORD:
            return isEditArgumentValid(input);

        case FindCommand.COMMAND_WORD:
            return isFindArgumentValid(input);

        default:
            return true;

        }
    }

    /**
     * Checks if given input is a valid argument for the "add" command.
     *
     * @param input the given input
     * @return true if the input is a valid argument, false otherwise
     */
    private boolean isAddArgumentValid(String input) {
        switch (currentArgIndex) {

        case ADD_ROLE_INDEX:
            return Role.isValidRole(input);

        case ADD_NAME_INDEX:
            return Name.isValidName(input);

        case ADD_PHONE_INDEX:
            return Phone.isValidPhone(input);

        case ADD_EMAIL_INDEX:
            return Email.isValidEmail(input);

        case ADD_ADDRESS_INDEX:
            return Address.isValidAddress(input);

        case ADD_TAGS_INDEX:
            for (String tag : input.split(",")) {
                if (!Tag.isValidTagName(tag)) {
                    return false;
                }
            }
            return true;

        case ADD_NRIC_INDEX:
            return Nric.isValidNric(input);

        case ADD_DEPT_INDEX:
            return MedicalDepartment.isValidMedDept(input);

        default:
            return true;

        }

    }

    /**
     * Checks if given input is a valid argument for the "delete" command.
     *
     * @param input the given input
     * @return true if the input is a valid argument, false otherwise
     */
    private boolean isDeleteArgumentValid(String input) {
        return StringUtil.isNonZeroUnsignedInteger(input);
    }

    /**
     * Checks if given input is a valid argument for the "edit" command.
     *
     * @param input the given input
     * @return true if the input is a valid argument, false otherwise
     */
    private boolean isEditArgumentValid(String input) {
        switch (currentArgIndex) {

        case EDIT_TARGET_INDEX:
            return StringUtil.isNonZeroUnsignedInteger(input);

        case EDIT_FIELDS_INDEX:
            for (String index : input.split(" ")) {
                if (!StringUtil.isNonZeroUnsignedInteger(index)
                        || Integer.valueOf(index) >= EDIT_TAGS_INDEX) {
                    return false;
                }
            }
            return true;

        case EDIT_NAME_INDEX:
            return Name.isValidName(input);

        case EDIT_PHONE_INDEX:
            return Phone.isValidPhone(input);

        case EDIT_EMAIL_INDEX:
            return Email.isValidEmail(input);

        case EDIT_ADDRESS_INDEX:
            return Address.isValidAddress(input);

        case EDIT_TAGS_INDEX:
            if (input.equals(EDIT_CLEAR_TAGS_COMMAND)) {
                return true;
            }

            for (String tag : input.split(",")) {
                if (!Tag.isValidTagName(tag)) {
                    return false;
                }
            }
            return true;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    /**
     * Checks if given input is a valid argument for the "find" command.
     *
     * @param input the given input
     * @return true if the input is a valid argument, false otherwise
     */
    private boolean isFindArgumentValid(String input) {
        switch (currentArgIndex) {

        case FIND_SEARCH_FIELDS_INDEX:
            for (String index : input.split(" ")) {
                if (!StringUtil.isNonZeroUnsignedInteger(index)
                        || Integer.valueOf(index) >= FIND_TAGS_INDEX) {
                    return false;
                }
            }
            return true;

        default:
            return true;

        }
    }


    /**
     * Retrieves message to be thrown with exception when an invalid argument is detected.
     *
     * @return string message to be thrown with exception
     */
    private String retrieveInvalidArgumentExceptionMessage() {
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return retrieveInvalidAddArgumentExceptionMessage();

        case DeleteCommand.COMMAND_WORD:
            return retrieveInvalidDeleteArgumentExceptionMessage();

        case EditCommand.COMMAND_WORD:
            return retrieveInvalidEditArgumentExceptionMessage();

        case FindCommand.COMMAND_WORD:
            return retrieveInvalidFindArgumentExceptionMessage();

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    /**
     * Retrieves message to be thrown with exception when an invalid argument is detected
     * for the specific case of the "add" command.
     *
     * @return string message to be thrown with exception
     */
    private String retrieveInvalidAddArgumentExceptionMessage() {
        switch (currentArgIndex) {

        case (ADD_ROLE_INDEX):
            return Role.MESSAGE_ROLE_CONSTRAINTS;

        case (ADD_NAME_INDEX):
            return Name.MESSAGE_NAME_CONSTRAINTS;

        case (ADD_PHONE_INDEX):
            return Phone.MESSAGE_PHONE_CONSTRAINTS;

        case (ADD_EMAIL_INDEX):
            return Email.MESSAGE_EMAIL_CONSTRAINTS;

        case (ADD_ADDRESS_INDEX):
            return Address.MESSAGE_ADDRESS_CONSTRAINTS;

        case (ADD_TAGS_INDEX):
            return Tag.MESSAGE_TAG_CONSTRAINTS;

        case (ADD_NRIC_INDEX):
            return Nric.MESSAGE_NRIC_CONSTRAINTS;

        case (ADD_DEPT_INDEX):
            return MedicalDepartment.MESSAGE_DEPTNAME_CONSTRAINTS;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    /**
     * Retrieves message to be thrown with exception when an invalid argument is detected
     * for the specific case of the "delete" command.
     *
     * @return string message to be thrown with exception
     */
    private String retrieveInvalidDeleteArgumentExceptionMessage() {
        return DELETE_INVALID_ARGUMENT_MESSAGE;
    }

    /**
     * Retrieves message to be thrown with exception when an invalid argument is detected
     * for the specific case of the "edit" command.
     *
     * @return string message to be thrown with exception
     */
    private String retrieveInvalidEditArgumentExceptionMessage() {
        switch (currentArgIndex) {

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

    /**
     * Retrieves message to be thrown with exception when an invalid argument is detected
     * for the specific case of the "find" command.
     *
     * @return string message to be thrown with exception
     */
    private String retrieveInvalidFindArgumentExceptionMessage() {
        switch (currentArgIndex) {

        case FIND_SEARCH_FIELDS_INDEX:
            return String.format(FIND_INVALID_FIELDS_MESSAGE,
                    FIND_NAME_INDEX,
                    FIND_TAGS_INDEX);

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    /**
     * Cancels and exits out of the currently executing intuitive command.
     */
    public void cancelIntuitiveCommand() {
        exitIntuitiveMode();
        resetIntuitiveCache();
    }
}
