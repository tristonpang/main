package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";

    public static final String COMMON_MESSAGE = COMMAND_WORD + ": Adds a person to the database. "
            + "Parameters: "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n";

    private static final String USAGE_EXAMPLE = "Example: "
            + COMMAND_WORD + " "
            + PREFIX_ROLE + "<ROLE> "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "diabetic ";

    private static final String USAGE_DOCTOR_EXAMPLE = USAGE_EXAMPLE.replaceFirst("<ROLE>", "Doctor")
            + PREFIX_MEDICAL_DEPARTMENT + "Oncology";

    public static final String MESSAGE_PATIENT_USAGE =
            COMMON_MESSAGE.replaceFirst("person", "patient") + USAGE_EXAMPLE.replaceFirst("<ROLE>",
                    "Patient");
    public static final String MESSAGE_DOCTOR_USAGE =
            COMMON_MESSAGE.replaceFirst("person", "doctor") + USAGE_DOCTOR_EXAMPLE;
    public static final String MESSAGE_GENERAL_USAGE = MESSAGE_PATIENT_USAGE.replaceFirst("patient",
            "patient/doctor") + "\n" + USAGE_DOCTOR_EXAMPLE;

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
