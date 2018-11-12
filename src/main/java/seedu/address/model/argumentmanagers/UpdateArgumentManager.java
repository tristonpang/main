package seedu.address.model.argumentmanagers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TREATMENT;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.patient.Diagnosis;
import seedu.address.model.patient.Treatment;
import seedu.address.model.person.Date;

/**
 * ArgumentManager that handles management and recording of arguments for an intuitive 'update' command.
 */
public class UpdateArgumentManager extends ArgumentManager {
    public static final String UPDATE_TARGET_INSTRUCTION = "Please enter the index of the patient whose medical record "
            + "will be updated";
    public static final String UPDATE_DATE_INSTRUCTION = "Please enter the date of the medical record entry";
    public static final String UPDATE_DIAGNOSIS_INSTRUCTION = "Please enter the patient's diagnosis";
    public static final String UPDATE_TREATMENT_INSTRUCTION = "Please enter the patient's treatment";
    public static final String UPDATE_COMMENTS_INSTRUCTION = "Please enter any comments for this medical record entry";

    public static final String UPDATE_INVALID_INDEX_MESSAGE = "Index must be a non-zero positive integer";
    public static final String UPDATE_INVALID_DATE_MESSAGE = "Invalid date. Please enter an existing date "
            + "(not in the past) in the format DD.MM.YYYY";
    public static final String UPDATE_INVALID_DIAGNOSIS_MESSAGE = "Invalid diagnosis. Diagnosis cannot be blank";
    public static final String UPDATE_INVALID_TREATMENT_MESSAGE = "Invalid treatment. Treatment cannot be blank";

    private static final int UPDATE_MAX_ARGUMENTS = 5;
    private static final int UPDATE_TARGET_INDEX = 0;
    private static final int UPDATE_DATE_INDEX = 1;
    private static final int UPDATE_DIAGNOSIS_INDEX = 2;
    private static final int UPDATE_TREATMENT_INDEX = 3;
    private static final int UPDATE_COMMENTS_INDEX = 4;





    @Override
    public int addArgumentForCommand(List<String> arguments, int argumentIndex, String userInput) {
        arguments.add(userInput);
        return argumentIndex + 1;
    }

    @Override
    public String retrieveInstruction(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case UPDATE_TARGET_INDEX:
            return UPDATE_TARGET_INSTRUCTION;

        case UPDATE_DATE_INDEX:
            return UPDATE_DATE_INSTRUCTION;

        case UPDATE_DIAGNOSIS_INDEX:
            return UPDATE_DIAGNOSIS_INSTRUCTION;

        case UPDATE_TREATMENT_INDEX:
            return UPDATE_TREATMENT_INSTRUCTION;

        case UPDATE_COMMENTS_INDEX:
            return UPDATE_COMMENTS_INSTRUCTION;

        case UPDATE_MAX_ARGUMENTS:
            return COMMAND_COMPLETE_MESSAGE;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public int removeArgumentForCommand(List<String> arguments, int argumentIndex) {
        arguments.remove(argumentIndex - 1);
        return argumentIndex - 1;
    }

    @Override
    public int getMaximumArguments() {
        return UPDATE_MAX_ARGUMENTS;
    }

    @Override
    public String prepareArguments(List<String> arguments) {
        StringBuilder preparedString = new StringBuilder();
        preparedString.append(UpdateCommand.COMMAND_WORD + " ");

        int index = MIN_ARGUMENT_INDEX;
        for (String argument : arguments) {
            if (argument.isEmpty()) {
                index++;
                continue;
            }
            preparedString.append(prefixUpdateArgument(index, argument));
            preparedString.append(" ");
            index++;
        }

        return preparedString.toString().trim();
    }

    /**
     * Given an argument and an index that represents which field this argument belongs to in the 'schedule' command,
     * prefix and return the edited argument.
     *
     * @param index    the index that represents which field the argument belongs to in the 'schedule' command
     * @param argument the specified argument
     * @return the prefixed argument
     */
    private String prefixUpdateArgument(int index, String argument) {
        switch (index) {

        case UPDATE_TARGET_INDEX:
            return argument;

        case UPDATE_DATE_INDEX:
            return PREFIX_DATE + argument;

        case UPDATE_DIAGNOSIS_INDEX:
            return PREFIX_DIAGNOSIS + argument;

        case UPDATE_TREATMENT_INDEX:
            return PREFIX_TREATMENT + argument;

        case UPDATE_COMMENTS_INDEX:
            String resultArg = PREFIX_COMMENT + argument;
            return resultArg.replace(",", " " + PREFIX_COMMENT).trim();

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public boolean isArgumentValid(List<String> arguments, int argumentIndex, String userInput) {
        switch (argumentIndex) {

        case UPDATE_TARGET_INDEX:
            return StringUtil.isNonZeroUnsignedInteger(userInput);

        case UPDATE_DATE_INDEX:
            return (new Date(userInput)).isValid();

        case UPDATE_DIAGNOSIS_INDEX:
            return new Diagnosis(userInput).isValid();

        case UPDATE_TREATMENT_INDEX:
            return new Treatment(userInput).isValid();

        case UPDATE_COMMENTS_INDEX:
            return true;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public String retrieveInvalidArgumentExceptionMessage(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case UPDATE_TARGET_INDEX:
            return UPDATE_INVALID_INDEX_MESSAGE;

        case UPDATE_DATE_INDEX:
            return UPDATE_INVALID_DATE_MESSAGE;

        case UPDATE_DIAGNOSIS_INDEX:
            return UPDATE_INVALID_DIAGNOSIS_MESSAGE;

        case UPDATE_TREATMENT_INDEX:
            return UPDATE_INVALID_TREATMENT_MESSAGE;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }


    @Override
    public boolean isCurrentFieldSkippable(int argumentIndex) {
        return argumentIndex == UPDATE_COMMENTS_INDEX;
    }

    @Override
    public String getArgumentManagerType() {
        return UpdateCommand.COMMAND_WORD;
    }
}
