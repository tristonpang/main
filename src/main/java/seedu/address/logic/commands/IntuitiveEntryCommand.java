package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class IntuitiveEntryCommand extends Command {
    private static final String GO_BACK_COMMAND = "/bk";
    private final String INPUT_ECHO = "You entered: %1$s \n";

    private String input;

    public IntuitiveEntryCommand(String userInput) {
        this.input = userInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        String nextInstruction = null;
        if (input.equals(GO_BACK_COMMAND)) {
            nextInstruction = model.removeIntuitiveEntry();
        } else {
            //model -> save input as argument in list (IntuitivePromptManager)
            //get correct command type and argument index from IntuitivePromptManager (in model)
            nextInstruction = model.addIntuitiveEntry(input);
        }

        if (input.equals("")) {
            return new CommandResult(nextInstruction);
        }


        //return correct instruction to display in CommandResult
        return new CommandResult(String.format(INPUT_ECHO, this.input) + nextInstruction);
    }
}
