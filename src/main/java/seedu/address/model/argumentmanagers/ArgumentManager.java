package seedu.address.model.argumentmanagers;

import java.util.List;

/**
 * Class that determines how arguments are stored and keeps track of respective instructions.
 */
public abstract class ArgumentManager {
    public static final int MIN_ARGUMENT_INDEX = 0;
    protected static final String COMMAND_COMPLETE_MESSAGE = "All required inputs received, processing...";
    protected static final String UNEXPECTED_SCENARIO_MESSAGE = "ArgumentManager: "
            + "Unexpected scenario has occurred in switch-case block";

    /**
     * Handles the adding of the user's input as an argument, depending on
     * the type of command.
     *
     * @param arguments the container of arguments to be added to
     * @param argumentIndex the current argument index
     * @param userInput the user's input to be added as an argument
     * @return the newly incremented argumentIndex
     */
    public abstract int addArgumentForCommand(List<String> arguments, int argumentIndex, String userInput);

    /**
     * Retrieves corresponding instruction for a field (specified by the current argument index)
     * for the intuitive command.
     *
     * @param arguments the current container of arguments
     * @return the corresponding string instruction for the specified field
     */
    public abstract String retrieveInstruction(List<String> arguments, int argumentIndex);

    /**
     * Removes the latest stored argument of the currently executing intuitive command.
     *
     * @param arguments the current container of arguments
     * @param argumentIndex the current argument index
     * @return the newly decremented argument index
     */
    public abstract int removeArgumentForCommand(List<String> arguments, int argumentIndex);

    /**
     * Retrieves the maximum number of arguments that the intuitive command takes in.
     *
     * @return the maximum number of arguments that the command takes in
     */
    public abstract int getMaximumArguments();

    /**
     * Prepares a string represents a single line (non-intuitive) command based on all
     * the past arguments entered by the user during the execution of the intuitive command.
     *
     * @param arguments the current container of arguments
     * @return a string that is the non-intuitive version of the command, containing entered arguments of the
     * past executed intuitive command
     */
    public abstract String prepareArguments(List<String> arguments);

    /**
     * Checks if given input is a valid argument for the intuitive command.
     *
     * @param arguments the current container of arguments
     * @param userInput the given input
     * @param argumentIndex the current argument index
     * @return true if the input is a valid argument, false otherwise
     */
    public abstract boolean isArgumentValid(List<String> arguments, int argumentIndex, String userInput);

    /**
     * Retrieves message to be thrown with exception when an invalid argument is detected.
     *
     * @param arguments the current container of arguments
     * @param argumentIndex the current argument index
     * @return string message to be thrown with exception
     */
    public abstract String retrieveInvalidArgumentExceptionMessage(List<String> arguments, int argumentIndex);


    public abstract boolean isCurrentFieldSkippable(int argumentIndex);

    /**
     * Returns a String representing the command that the ArgumentManager is for.
     *
     * @return the String representing the command
     */
    public abstract String getArgumentManagerType();

}
