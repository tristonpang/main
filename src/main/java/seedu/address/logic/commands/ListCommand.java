package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {
    
    private Role role;
    private Predicate<Person> predicate;

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists people in the addressbook. "
            + "(Optional) Parameters: "
            + PREFIX_ROLE + "ROLE \n"
            + "Example : " + COMMAND_WORD + "\n"
            + "Example : " + COMMAND_WORD + " "
            + PREFIX_ROLE + "patient \n";
    
    public ListCommand(){
        predicate = PREDICATE_SHOW_ALL_PERSONS;
    }
    
    public ListCommand(Role role){
        this.role = role;
        this.predicate = p -> p.getClass().getSimpleName().toUpperCase().equals(role.toString());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(this.predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
