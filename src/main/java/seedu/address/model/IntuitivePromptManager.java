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
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Nric;
import seedu.address.ui.UiManager;


/**
 * The IntuitivePromptManager of the Model. Handles and stores all data related to an intuitive command
 * execution.
 */
public class IntuitivePromptManager {
    private static int currentArgIndex; //change this to enum in future version
    private static List<String> arguments;
    private static String commandWord;
    private static boolean isIntuitiveMode;

    private static final int MIN_ARGUMENT_INDEX = 0;

    private static final String INVALID_ARGUMENT_MESSAGE = "Invalid input! Please try again!\n %1$s";

    private static final String SKIP_COMMAND = "//";
    private static final String SKIP_INSTRUCTION = "\n(Type %1$s to skip this field)";

    private static final String ADD_ROLE_INSTRUCTION = "Is this a patient or a doctor? "
            + "(Please enter patient or doctor)";
    private static final String ADD_NAME_INSTRUCTION = "Please enter person's name";
    private static final String ADD_EMAIL_INSTRUCTION = "Please enter person's email";
    private static final String ADD_PHONE_INSTRUCTION = "Please enter person's phone number";
    private static final String ADD_ADDRESS_INSTRUCTION = "Please enter person's address";
    private static final String ADD_TAGS_INSTRUCTION = "Please enter person's tags, "
            + "separated by commas (with no spaces after a comma)";
    private static final String ADD_NRIC_INSTRUCTION = "Please enter patient's NRIC";
    private static final String ADD_DEPT_INSTRUCTION = "Please enter doctor's medical department";

    private static final int ADD_MAX_ARGUMENTS = 7;
    private static final int ADD_ROLE_INDEX = 0;
    private static final int ADD_NAME_INDEX = 1;
    private static final int ADD_PHONE_INDEX = 2;
    private static final int ADD_EMAIL_INDEX = 3;
    private static final int ADD_ADDRESS_INDEX = 4;
    private static final int ADD_TAGS_INDEX = 5;
    private static final int ADD_NRIC_OR_DEPT_INDEX = 6;

    private static final int DELETE_MAX_ARGUMENTS = 1;
    private static final int DELETE_TARGET_INDEX = 0;

    private static final String DELETE_TARGET_INSTRUCTION = "Please enter the index of the person to be deleted";

    private static final String PATIENT_ARG_IDENTIFIER = "patient";
    private static final String DOCTOR_ARG_IDENTIFIER = "doctor";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);


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
            commandWord = userInput;
            startIntuitiveMode();
        } else if (isSkipCommand(userInput) && isCurrentFieldSkippable()) { //skip command
            arguments.add("");
            currentArgIndex++;
        } else if (isArgumentValid(userInput)) { //any other valid argument
            arguments.add(userInput);
            currentArgIndex++;
        } else {
            throw new CommandException(String.format(INVALID_ARGUMENT_MESSAGE, getInstruction()));
        }


        logger.fine("Intuitive Argument index: " + currentArgIndex);
        logger.fine("Current arguments: " + arguments);


        if (currentArgIndex >= getMaximumArguments(commandWord)) {
            exitIntuitiveMode();
        }
    }

    private void startIntuitiveMode() {
        isIntuitiveMode = true;
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

        default:
            return "Invalid";
        }
    }

    /**
     * Removes the latest stored argument of the currently executing intuitive command.
     */
    public void removeArgument() {
        if (currentArgIndex != MIN_ARGUMENT_INDEX) {
            arguments.remove(currentArgIndex - 1);
            currentArgIndex--;
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

        case ADD_NRIC_OR_DEPT_INDEX:
            if (isPatient()) {
                return ADD_NRIC_INSTRUCTION;
            } else if (isDoctor()) {
                return ADD_DEPT_INSTRUCTION;
            } else {
                return "Invalid";
            }

        default:
            return "Invalid";
        }
    }

    private String retrieveDeleteInstruction() {
        switch (currentArgIndex) {

        case DELETE_TARGET_INDEX:
            return DELETE_TARGET_INSTRUCTION;

        default:
            return "Invalid";

        }
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

        default:
            return "Invalid";
        }
    }

    private String prepareArgumentsForDelete() {
        String preparedString = "";
        preparedString += DeleteCommand.COMMAND_WORD + " ";

        String targetIndex = arguments.get(DELETE_TARGET_INDEX);
        preparedString += targetIndex;

        return preparedString;
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

        int index = 0;
        for (String arg : arguments) {
            preparedString += prefixAddArgument(index, arg); //TODO: optimise with StringBuilder
            preparedString += " ";
            index++;
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
            if (argument.isEmpty()) {
                return "";
            }
            String resultArg = PREFIX_TAG + argument;
            return resultArg.replace(",", " " + PREFIX_TAG).trim();

        case ADD_NRIC_OR_DEPT_INDEX:
            if (isPatient()) {
                return PREFIX_NRIC + argument;
            } else if (isDoctor()) {
                return PREFIX_MEDICAL_DEPARTMENT + argument;
            } else {
                return "";
            }

        default:
            return "";
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

        default:
            return true;

        }
    }

    /**
     * Checks if the given input is a valid argument for the add command.
     *
     * @param input the given input
     * @return true if the input is a valid argument, false otherwise
     */
    private boolean isAddArgumentValid(String input) {
        switch (currentArgIndex) {

        case ADD_ROLE_INDEX:
            return input.equals(DOCTOR_ARG_IDENTIFIER) || input.equals(PATIENT_ARG_IDENTIFIER);

        case ADD_NRIC_OR_DEPT_INDEX:
            if (isPatient()) {
                return Nric.isValidNric(input);
            }
            // Fallthrough
        default:
            return true;

        }

    }
}
