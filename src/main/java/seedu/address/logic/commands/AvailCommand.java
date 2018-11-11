package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.AppointmentManager;
import seedu.address.model.person.Date;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Adds a person to the address book.
 */
public class AvailCommand extends Command {

    public static final String COMMAND_WORD = "avail";
    public static final String MESSAGE_SUCCESS = "Listed all doctors available at: %1$s";
    public static final String MESSAGE_INVALID_TIME_INPUT = "Invalid time period. You need to specify both start and"
            + " end time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all doctors available at indicated time / "
            + "current datetime (as per default). "
            + "Parameters: "
            + "[" + PREFIX_DATE + "DATE]"
            + "[" + PREFIX_START_TIME + "START TIME]"
            + "[" + PREFIX_END_TIME + "END TIME]\n"
            + "Example: "
            + COMMAND_WORD + "\n"
            + COMMAND_WORD + " "
            + PREFIX_DATE + "24.11.2019\n"
            + COMMAND_WORD + " "
            + PREFIX_DATE + "24.11.2019 "
            + PREFIX_START_TIME + "1200 "
            + PREFIX_END_TIME + "1300";

    private static final String MESSAGE_INVALID_DATABASE = "You are currently in the patients' database. Switch over "
            + "to view the doctor's database before using this command";

    private static final String PATIENT_KEYWORD = "Patient";

    private Date date;
    private Time startTime;
    private Time endTime;

    /**
     * Creates an AvailCommand base on given datetime parameters.
     * If no {@code endTime} is given, it will check the doctor's availability base on that instant of {@code starttime}
     */
    public AvailCommand(Date date, Time startTime, Time endTime) {
        requireAllNonNull(date, startTime);
        this.date = date;
        this.startTime = startTime;

        if (endTime == null) {
            this.endTime = startTime;
        } else {
            this.endTime = endTime;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getCurrentDatabase().equalsIgnoreCase(PATIENT_KEYWORD)) {
            throw new CommandException(MESSAGE_INVALID_DATABASE);
        }

        Predicate<Person> filterDoctorsOnly = person -> person instanceof Doctor;
        Predicate<Person> filterAvailDoctor;

        if (endTime != null) {
            filterAvailDoctor = doctor -> !AppointmentManager.isAnyAppointmentOngoing(doctor.getAppointmentList(),
                    date, startTime, endTime);
        } else {
            filterAvailDoctor = doctor -> !AppointmentManager.isAnyAppointmentOngoing(doctor.getAppointmentList(),
                    date, startTime, endTime);
        }
        model.updateFilteredPersonList(filterDoctorsOnly.and(filterAvailDoctor));
        return new CommandResult(String.format(MESSAGE_SUCCESS, date + " " + startTime + " to " + endTime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AvailCommand // instanceof handles nulls
                && date.equals(((AvailCommand) other).date))
                && startTime.equals(((AvailCommand) other).startTime)
                && endTime.equals(((AvailCommand) other).endTime);
    }
}
