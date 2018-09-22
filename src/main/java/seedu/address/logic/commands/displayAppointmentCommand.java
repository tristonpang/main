package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class displayAppointmentCommand {

    public static final String COMMAND_WORD = "appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the appointment(s) of a doctor \n"
            + "Parameters if doctor: INDEX (must be a positive integer) "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_MEDICAL_DEPARTMENT + "MEDICAL_DEPARTMENT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ROLE + "doctor "
            + PREFIX_NAME + "John "
            + PREFIX_MEDICAL_DEPARTMENT + "Oncology";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
    }

}
