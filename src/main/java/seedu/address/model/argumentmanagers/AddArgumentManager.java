package seedu.address.model.argumentmanagers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.IntuitivePromptManager.SKIP_COMMAND;
import static seedu.address.model.IntuitivePromptManager.SKIP_INSTRUCTION;

import java.util.List;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.doctor.MedicalDepartment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * ArgumentManager that handles management and recording of arguments for an intuitive 'add' command.
 */
public class AddArgumentManager extends ArgumentManager {
    public static final String ROLE_INSTRUCTION = "Is this a patient or a doctor? "
            + "(Please enter patient or doctor)";
    public static final String NAME_INSTRUCTION = "Please enter person's name";
    public static final String EMAIL_INSTRUCTION = "Please enter person's email";
    public static final String PHONE_INSTRUCTION = "Please enter person's phone number";
    public static final String ADDRESS_INSTRUCTION = "Please enter person's address";
    public static final String TAGS_INSTRUCTION = "Please enter person's tags, "
            + "separated by commas (with no spaces after a comma)";
    public static final String NRIC_INSTRUCTION = "Please enter patient's NRIC";
    public static final String DEPT_INSTRUCTION = "Please enter doctor's medical department";

    public static final String PATIENT_ARG_IDENTIFIER = "patient";
    public static final String DOCTOR_ARG_IDENTIFIER = "doctor";

    private static final int ADD_MAX_ARGUMENTS = 8;
    private static final int ADD_ROLE_INDEX = 0;
    private static final int ADD_NAME_INDEX = 1;
    private static final int ADD_PHONE_INDEX = 2;
    private static final int ADD_EMAIL_INDEX = 3;
    private static final int ADD_ADDRESS_INDEX = 4;
    private static final int ADD_TAGS_INDEX = 5;
    private static final int ADD_NRIC_INDEX = 6;
    private static final int ADD_DEPT_INDEX = 7;

    private static String INVALID_NRIC_MESSAGE;

    @Override
    public int addArgumentForCommand(List<String> arguments, int argumentIndex, String userInput) {
        arguments.add(userInput);
        if (!isDoctor(arguments) && !isPatient(arguments)) {
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);
        }
        if (argumentIndex == ADD_NRIC_INDEX && isPatient(arguments)) {
            return ADD_MAX_ARGUMENTS;
        }


        return argumentIndex + 1;
    }

    private boolean isPatient(List<String> arguments) {
        return arguments.get(ADD_ROLE_INDEX).equalsIgnoreCase(PATIENT_ARG_IDENTIFIER);
    }

    private boolean isDoctor(List<String> arguments) {
        return arguments.get(ADD_ROLE_INDEX).equalsIgnoreCase(DOCTOR_ARG_IDENTIFIER);
    }

    @Override
    public String retrieveInstruction(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case ADD_ROLE_INDEX:
            return ROLE_INSTRUCTION;

        case ADD_NAME_INDEX:
            return NAME_INSTRUCTION;

        case ADD_EMAIL_INDEX:
            return EMAIL_INSTRUCTION;

        case ADD_PHONE_INDEX:
            return PHONE_INSTRUCTION;

        case ADD_ADDRESS_INDEX:
            return ADDRESS_INSTRUCTION;

        case ADD_TAGS_INDEX:
            return TAGS_INSTRUCTION + String.format(SKIP_INSTRUCTION, SKIP_COMMAND);

        case ADD_NRIC_INDEX:
            return NRIC_INSTRUCTION;

        case ADD_DEPT_INDEX:
            return DEPT_INSTRUCTION;

        case ADD_MAX_ARGUMENTS:
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
        return ADD_MAX_ARGUMENTS;
    }

    @Override
    public String prepareArguments(List<String> arguments) {
        StringBuilder preparedString = new StringBuilder();
        preparedString.append(AddCommand.COMMAND_WORD + " ");

        int index = MIN_ARGUMENT_INDEX;
        for (String argument : arguments) {
            if (argument.isEmpty()) {
                index++;
                continue;
            }
            preparedString.append(prefixAddArgument(index, argument));
            preparedString.append(" ");
            index++;
        }

        return preparedString.toString().trim();
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

    @Override
    public boolean isArgumentValid(List<String> arguments, int argumentIndex, String userInput) {
        switch (argumentIndex) {

        case ADD_ROLE_INDEX:
            return Role.isValidRole(userInput);

        case ADD_NAME_INDEX:
            return Name.isValidName(userInput);

        case ADD_PHONE_INDEX:
            return Phone.isValidPhone(userInput);

        case ADD_EMAIL_INDEX:
            return Email.isValidEmail(userInput);

        case ADD_ADDRESS_INDEX:
            return Address.isValidAddress(userInput);

        case ADD_TAGS_INDEX:
            for (String tag : userInput.split(",")) {
                if (!Tag.isValidTagName(tag)) {
                    return false;
                }
            }
            return true;

        case ADD_NRIC_INDEX:
            try {
                Nric.isValidNric(userInput);
            } catch (IllegalArgumentException e) {
                INVALID_NRIC_MESSAGE = e.getMessage();
                return false;
            }
            return true;

        case ADD_DEPT_INDEX:
            return MedicalDepartment.isValidMedDept(userInput);

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public String retrieveInvalidArgumentExceptionMessage(List<String> arguments, int argumentIndex) {
        switch (argumentIndex) {

        case ADD_ROLE_INDEX:
            return Role.MESSAGE_ROLE_CONSTRAINTS;

        case ADD_NAME_INDEX:
            return Name.MESSAGE_NAME_CONSTRAINTS;

        case ADD_PHONE_INDEX:
            return Phone.MESSAGE_PHONE_CONSTRAINTS;

        case ADD_EMAIL_INDEX:
            return Email.MESSAGE_EMAIL_CONSTRAINTS;

        case ADD_ADDRESS_INDEX:
            return Address.MESSAGE_ADDRESS_CONSTRAINTS;

        case ADD_TAGS_INDEX:
            return Tag.MESSAGE_TAG_CONSTRAINTS;

        case ADD_NRIC_INDEX:
            return INVALID_NRIC_MESSAGE;

        case ADD_DEPT_INDEX:
            return MedicalDepartment.MESSAGE_DEPTNAME_CONSTRAINTS;

        default:
            throw new Error(UNEXPECTED_SCENARIO_MESSAGE);

        }
    }

    @Override
    public boolean isCurrentFieldSkippable(int argumentIndex) {
        return argumentIndex == ADD_TAGS_INDEX;
    }

    @Override
    public String getArgumentManagerType() {
        return AddCommand.COMMAND_WORD;
    }

}
