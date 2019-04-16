package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.argumentmanagers.AddArgumentManager;
import seedu.address.model.argumentmanagers.ArgumentManager;
import seedu.address.model.argumentmanagers.DeleteArgumentManager;
import seedu.address.model.argumentmanagers.EditArgumentManager;
import seedu.address.model.argumentmanagers.FindArgumentManager;
import seedu.address.model.argumentmanagers.ScheduleArgumentManager;
import seedu.address.model.argumentmanagers.UpdateArgumentManager;
import seedu.address.ui.UiManager;


/**
 * The IntuitivePromptManager of the Model. Handles and stores all data related to an intuitive command
 * execution.
 */
public class IntuitivePromptManager {
    public static final String SKIP_COMMAND = "//";
    public static final String SKIP_INSTRUCTION = "\n(Type %1$s to skip this field)";
    public static final String INTUITIVE_MODE_MESSAGE = "(You are currently in the intuitive %1$s command.)";

    private static int currentArgIndex;
    private static List<String> arguments;
    private static ArgumentManager argumentManager;
    private static boolean isIntuitiveMode;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String UNEXPECTED_SCENARIO_MESSAGE = "IntuitivePromptManager: "
            + "Unexpected scenario has occurred in switch-case block";


    public IntuitivePromptManager() {
        currentArgIndex = ArgumentManager.MIN_ARGUMENT_INDEX;
        arguments = new ArrayList<>();
        argumentManager = null;
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
        if (argumentManager == null) { //start of intuitive command, record command word
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


        if (currentArgIndex >= argumentManager.getMaximumArguments()) {
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
        isIntuitiveMode = true;
        switch (userInput) {

        case AddCommand.COMMAND_WORD:
            argumentManager = new AddArgumentManager();
            break;

        case DeleteCommand.COMMAND_WORD:
            argumentManager = new DeleteArgumentManager();
            break;

        case EditCommand.COMMAND_WORD:
            argumentManager = new EditArgumentManager();
            break;

        case FindCommand.COMMAND_WORD:
            argumentManager = new FindArgumentManager();
            break;

        case ScheduleCommand.COMMAND_WORD:
            argumentManager = new ScheduleArgumentManager();
            break;

        case UpdateCommand.COMMAND_WORD:
            argumentManager = new UpdateArgumentManager();
            break;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }

    }

    /**
     * Handles the adding of the user's input as an argument, depending on
     * the type of command.
     *
     * @param userInput the user's input to be added as an argument
     */
    private void addArgumentForCommand(String userInput) {
        currentArgIndex = argumentManager.addArgumentForCommand(arguments, currentArgIndex, userInput);
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
        return String.format(INTUITIVE_MODE_MESSAGE, getCurrentCommandType()) + "\n"
                + argumentManager.retrieveInstruction(arguments, currentArgIndex);
    }

    /**
     * Indicates that currently executing intuitive command has completed.
     * i.e. all fields have been filled up, intuitive command mode has exited.
     */
    private void exitIntuitiveMode() {
        isIntuitiveMode = false;
    }

    /**
     * Removes the latest stored argument of the currently executing intuitive command.
     */
    public void removeArgument() {
        if (currentArgIndex <= ArgumentManager.MIN_ARGUMENT_INDEX) {
            return;
        }

        currentArgIndex = argumentManager.removeArgumentForCommand(arguments, currentArgIndex);
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

        String result = argumentManager.prepareArguments(arguments);
        resetIntuitiveCache();
        return result;
    }

    /**
     * Clears all stored arguments and readies the manager for a new intuitive command.
     */
    private void resetIntuitiveCache() {
        argumentManager = null;
        arguments.clear();
        currentArgIndex = ArgumentManager.MIN_ARGUMENT_INDEX;
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
        return argumentManager.isCurrentFieldSkippable(currentArgIndex);
    }

    /**
     * Checks if given input is a valid argument.
     *
     * @param input the given input
     * @return true if the input is a valid argument, false otherwise
     */
    private boolean isArgumentValid(String input) {
        return argumentManager.isArgumentValid(arguments, currentArgIndex, input);
    }


    /**
     * Retrieves message to be thrown with exception when an invalid argument is detected.
     *
     * @return string message to be thrown with exception
     */
    private String retrieveInvalidArgumentExceptionMessage() {
        return argumentManager.retrieveInvalidArgumentExceptionMessage(arguments, currentArgIndex);
    }

    /**
     * Cancels and exits out of the currently executing intuitive command.
     */
    public void cancelIntuitiveCommand() {
        exitIntuitiveMode();
        resetIntuitiveCache();
    }

    /**
     * Gets a String representing what intuitive command is currently executing.
     *
     * @return the String representing the current intuitive command
     */
    private String getCurrentCommandType() {
        return argumentManager.getArgumentManagerType();
    }
}
