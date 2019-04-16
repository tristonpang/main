package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose attributes contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "The following prefixes are available for specification: Name: n/, NRIC: ic/, Phone: p/, "
            + "Address: a/, Role: r/, Tag: t/, Medical Department: md/, Medical Record: mr/.\n"
            + "Ensure that the prefixes entered adhere to the format above."
            + "If no prefixes are given, all available attributes will searched for the specified keywords.\n"
            + "Parameters: [PREFIX]KEYWORD [NEXT_PREFIX][NEXT_KEYWORD]\n"
            + "Example: " + COMMAND_WORD + " n/alex r/doctor";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
