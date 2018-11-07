package seedu.address.model.argumentmanagers;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.DeleteCommand;

/**
 * ArgumentManager that handles management and recording of arguments for an intuitive 'delete' command.
 */
public class DeleteArgumentManager extends ArgumentManager {
    public static final String DELETE_TARGET_INSTRUCTION = "Please enter the index of the person to be deleted";
    public static final String DELETE_INVALID_ARGUMENT_MESSAGE = "Index must be a non-zero positive integer";

    private static final String UNEXPECTED_CALL_ERROR = "DeleteArgumentManager: "
            + "removeArgumentForCommand(..) should not be called!";
    private static final int DELETE_MAX_ARGUMENTS = 1;
    private static final int DELETE_TARGET_INDEX = 0;

    @Override
    public int addArgumentForCommand(List<String> arguments, int argumentIndex, String userInput) {
        arguments.add(userInput);
        return argumentIndex + 1;
    }

    @Override
    public String retrieveInstruction(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case DELETE_TARGET_INDEX:
            return DELETE_TARGET_INSTRUCTION;

        case DELETE_MAX_ARGUMENTS:
            return COMMAND_COMPLETE_MESSAGE;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public int removeArgumentForCommand(List<String> arguments, int argumentIndex) {
        throw new Error(UNEXPECTED_CALL_ERROR);
    }

    @Override
    public int getMaximumArguments() {
        return DELETE_MAX_ARGUMENTS;
    }

    @Override
    public String prepareArguments(List<String> arguments) {
        String preparedString = "";
        preparedString += DeleteCommand.COMMAND_WORD + " ";

        String targetIndex = arguments.get(DELETE_TARGET_INDEX);
        preparedString += targetIndex;

        return preparedString;
    }

    @Override
    public boolean isArgumentValid(List<String> arguments, int argumentIndex, String userInput) {
        return StringUtil.isNonZeroUnsignedInteger(userInput);
    }

    @Override
    public String retrieveInvalidArgumentExceptionMessage(List<String> arguments, int argumentIndex) {
        return DELETE_INVALID_ARGUMENT_MESSAGE;
    }

    @Override
    public boolean isCurrentFieldSkippable(int argumentIndex) {
        return false;
    }

    @Override
    public String getArgumentManagerType() {
        return DeleteCommand.COMMAND_WORD;
    }

}
