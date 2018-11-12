package seedu.address.model.argumentmanagers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.Date;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Time;

/**
 * ArgumentManager that handles management and recording of arguments for an intuitive 'schedule' command.
 */
public class ScheduleArgumentManager extends ArgumentManager {
    public static final String SCHEDULE_TARGET_INSTRUCTION = "Please the enter the index of the person to "
            + "schedule an appointment for (can be patient or doctor)";
    public static final String SCHEDULE_DATE_INSTRUCTION = "Please enter the date of the appointment to be scheduled";
    public static final String SCHEDULE_START_TIME_INSTRUCTION = "Please enter the start time of the appointment";
    public static final String SCHEDULE_END_TIME_INSTRUCTION = "Please enter the end time of the appointment";
    public static final String SCHEDULE_DOCTOR_NAME_INSTRUCTION = "Please enter the doctor's name";
    public static final String SCHEDULE_DOCTOR_NRIC_INSTRUCTION = "Please enter the doctor's NRIC";
    public static final String SCHEDULE_PATIENT_NAME_INSTRUCTION = "Please enter the patient's name";
    public static final String SCHEDULE_PATIENT_NRIC_INSTRUCTION = "Please enter the patient's NRIC";

    public static final String SCHEDULE_INVALID_INDEX_MESSAGE = "Index must be a non-zero positive integer";
    public static final String SCHEDULE_INVALID_DATE_MESSAGE = "Invalid date. Please enter an existing date "
            + "in the format DD.MM.YYYY";
    public static final String SCHEDULE_INVALID_START_TIME_MESSAGE = "Invalid time. Please enter a valid "
            + "24-hour clock time (e.g. 1500)";
    public static final String SCHEDULE_INVALID_END_TIME_MESSAGE = "Invalid time. Please enter a valid "
            + "24-hour clock time and ensure that it is after %1$s";

    private static final int SCHEDULE_MAX_ARGUMENTS = 8;
    private static final int SCHEDULE_TARGET_INDEX = 0;
    private static final int SCHEDULE_DATE_INDEX = 1;
    private static final int SCHEDULE_START_TIME_INDEX = 2;
    private static final int SCHEDULE_END_TIME_INDEX = 3;
    private static final int SCHEDULE_DOCTOR_NAME_INDEX = 4;
    private static final int SCHEDULE_DOCTOR_NRIC_INDEX = 5;
    private static final int SCHEDULE_PATIENT_NAME_INDEX = 6;
    private static final int SCHEDULE_PATIENT_NRIC_INDEX = 7;

    @Override
    public int addArgumentForCommand(List<String> arguments, int argumentIndex, String userInput) {
        arguments.add(userInput);
        return argumentIndex + 1;
    }

    @Override
    public String retrieveInstruction(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case SCHEDULE_TARGET_INDEX:
            return SCHEDULE_TARGET_INSTRUCTION;

        case SCHEDULE_DATE_INDEX:
            return SCHEDULE_DATE_INSTRUCTION;

        case SCHEDULE_START_TIME_INDEX:
            return SCHEDULE_START_TIME_INSTRUCTION;

        case SCHEDULE_END_TIME_INDEX:
            return SCHEDULE_END_TIME_INSTRUCTION;

        case SCHEDULE_DOCTOR_NAME_INDEX:
            return SCHEDULE_DOCTOR_NAME_INSTRUCTION;

        case SCHEDULE_DOCTOR_NRIC_INDEX:
            return SCHEDULE_DOCTOR_NRIC_INSTRUCTION;

        case SCHEDULE_PATIENT_NAME_INDEX:
            return SCHEDULE_PATIENT_NAME_INSTRUCTION;

        case SCHEDULE_PATIENT_NRIC_INDEX:
            return SCHEDULE_PATIENT_NRIC_INSTRUCTION;

        case SCHEDULE_MAX_ARGUMENTS:
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
        return SCHEDULE_MAX_ARGUMENTS;
    }

    @Override
    public String prepareArguments(List<String> arguments) {
        StringBuilder preparedString = new StringBuilder();
        preparedString.append(ScheduleCommand.COMMAND_WORD + " ");

        int index = MIN_ARGUMENT_INDEX;
        for (String argument : arguments) {
            if (argument.isEmpty()) {
                index++;
                continue;
            }
            preparedString.append(prefixScheduleArgument(index, argument));
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
    private String prefixScheduleArgument(int index, String argument) {
        switch (index) {

        case SCHEDULE_TARGET_INDEX:
            return argument;

        case SCHEDULE_DATE_INDEX:
            return PREFIX_DATE + argument;

        case SCHEDULE_START_TIME_INDEX:
            return PREFIX_START_TIME + argument;

        case SCHEDULE_END_TIME_INDEX:
            return PREFIX_END_TIME + argument;

        case SCHEDULE_DOCTOR_NAME_INDEX:
            return PREFIX_DOCTOR_NAME + argument;

        case SCHEDULE_DOCTOR_NRIC_INDEX:
            return PREFIX_DOCTOR_NRIC + argument;

        case SCHEDULE_PATIENT_NAME_INDEX:
            return PREFIX_PATIENT_NAME + argument;

        case SCHEDULE_PATIENT_NRIC_INDEX:
            return PREFIX_PATIENT_NRIC + argument;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public boolean isArgumentValid(List<String> arguments, int argumentIndex, String userInput) {
        switch (argumentIndex) {

        case SCHEDULE_TARGET_INDEX:
            return StringUtil.isNonZeroUnsignedInteger(userInput);

        case SCHEDULE_DATE_INDEX:
            return (new Date(userInput)).isValid();

        case SCHEDULE_START_TIME_INDEX:
            return new Time(userInput).isValidTime();

        case SCHEDULE_END_TIME_INDEX:
            Time startTime = new Time(arguments.get(SCHEDULE_START_TIME_INDEX));
            Time endTime = new Time(userInput);
            return (new Time(userInput)).isValidTime() && startTime.comesStrictlyBefore(endTime);

        case SCHEDULE_DOCTOR_NAME_INDEX: case SCHEDULE_PATIENT_NAME_INDEX:
            return Name.isValidName(userInput);

        case SCHEDULE_DOCTOR_NRIC_INDEX: case SCHEDULE_PATIENT_NRIC_INDEX:
            return Nric.isValidNric(userInput);

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public String retrieveInvalidArgumentExceptionMessage(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case SCHEDULE_TARGET_INDEX:
            return SCHEDULE_INVALID_INDEX_MESSAGE;

        case SCHEDULE_DATE_INDEX:
            return SCHEDULE_INVALID_DATE_MESSAGE;

        case SCHEDULE_START_TIME_INDEX:
            return SCHEDULE_INVALID_START_TIME_MESSAGE;

        case SCHEDULE_END_TIME_INDEX:
            return String.format(SCHEDULE_INVALID_END_TIME_MESSAGE, arguments.get(SCHEDULE_START_TIME_INDEX));

        case SCHEDULE_DOCTOR_NAME_INDEX:
            // Fallthrough

        case SCHEDULE_PATIENT_NAME_INDEX:
            return Name.MESSAGE_NAME_CONSTRAINTS;

        case SCHEDULE_DOCTOR_NRIC_INDEX:
            // Fallthrough

        case SCHEDULE_PATIENT_NRIC_INDEX:
            return Nric.MESSAGE_NRIC_CONSTRAINTS;

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
        return ScheduleCommand.COMMAND_WORD;
    }
}
