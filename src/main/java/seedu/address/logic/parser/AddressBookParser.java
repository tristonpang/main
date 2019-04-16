package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AvailCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearallCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.IntuitiveEntryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {
    /**
     * Keeps track of whether the system is currently executing an Intuitive Prompt Command
     */
    private static boolean isIntuitiveMode = false;

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        //detect if in intuitive mode
        if (isIntuitiveMode) {
            return new IntuitiveEntryCommand(userInput.trim());
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            //detect if user wants to activate intuitive mode/command
            if (arguments.isEmpty()) {
                return triggerIntuitiveMode(userInput);
            }
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return triggerIntuitiveMode(userInput);
            }
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return triggerIntuitiveMode(userInput);
            }
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearallCommand.COMMAND_WORD:
            return new ClearallCommand();

        case AvailCommand.COMMAND_WORD:
            return new AvailCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return triggerIntuitiveMode(userInput);
            }
            return new FindCommandParser().parse(arguments);

        case ScheduleCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return triggerIntuitiveMode(userInput);
            }
            return new ScheduleCommandParser().parse(arguments);

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case UpdateCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return triggerIntuitiveMode(userInput);
            }
            return new UpdateCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command triggerIntuitiveMode(String userInput) {
        isIntuitiveMode = true;
        return new IntuitiveEntryCommand(userInput);
    }

    public void exitIntuitiveMode() {
        isIntuitiveMode = false;
    }



}
