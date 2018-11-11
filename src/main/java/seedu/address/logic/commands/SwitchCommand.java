package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Switches from one database to another (i.e. patient or doctor database or both).
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String MESSAGE_SUCCESS = "Switches the database to indicated role.";
    public static final String MESSAGE_SUCCESS_FILTERED_LIST = "Load database of: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the database.\n"
            + "Example : \n" + COMMAND_WORD + " "
            + PREFIX_ROLE + " patient\n"
            + PREFIX_ROLE + " doctor\n"
            + COMMAND_WORD + " " + PREFIX_ROLE + "all\n";

    private Predicate<Person> filter;
    private String role;

    public SwitchCommand(Predicate<Person> predicate, String role) {
        this.filter = predicate;
        this.role = role.toUpperCase();
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.changeDatabase(filter, role);
        new ListCommand().execute(model, history);
        return new CommandResult(MESSAGE_SUCCESS_FILTERED_LIST + this.role + "\n");
    }
}
