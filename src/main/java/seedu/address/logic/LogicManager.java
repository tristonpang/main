package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    public static final String MESSAGE_NON_INTUITIVE_CANCELLATION = "There is currently "
            + "no intuitive command that is executing. Command box cleared.";
    public static final String MESSAGE_INTUITIVE_CANCELLATION = "Intuitive command cancelled.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            CommandResult result = command.execute(model, history);

            //if after intuitive input, all inputs have been received, parse with full arguments
            if (!model.isIntuitiveMode() && model.areIntuitiveArgsAvailable()) {
                //tell AddressBookParser that the intuitive command has completed
                addressBookParser.exitIntuitiveMode();

                String intuitiveArguments = model.retrieveIntuitiveArguments();
                logger.fine("Retrieved Argument String: " + intuitiveArguments);
                Command intuitiveCompletedCommand = addressBookParser.parseCommand(intuitiveArguments);
                return intuitiveCompletedCommand.execute(model, history);
            }

            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public String cancelCommand() {
        if (!model.isIntuitiveMode()) {
            return MESSAGE_NON_INTUITIVE_CANCELLATION;
        }
        addressBookParser.exitIntuitiveMode();
        model.cancelIntuitiveCommand();
        return MESSAGE_INTUITIVE_CANCELLATION;
    }
}
