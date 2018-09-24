package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MedicalRecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_RECORD;

public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": updates the medical record of the person identified by the index number used in the last person listing. "
            + "Existing medical record will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + PREFIX_MEDICAL_RECORD + "MEDICAL_RECORD\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MEDICAL_RECORD + "Diagnosed with flue. Tamiflu prescribed.";

    public static final String MESSAGE_UPDATE_MEDICAL_RECORD_SUCCESS = "Medical record updated";

    public static final String MESSAGE_UPDATE_MEDICAL_RECORD_FAILURE = "Medical record does nothing";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Medical record: %2$s";

    private final Index index;
    private final MedicalRecord medicalRecord;

    public UpdateCommand(Index index, MedicalRecord medicalRecord) {
        requireNonNull(index);
        requireNonNull(medicalRecord);
        this.index = index;
        this.medicalRecord = medicalRecord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), medicalRecord));
        /**
         try {
         return null;
         } catch (Exception e) {
         throw e;
         }
         **/
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }
        // state check
        UpdateCommand e = (UpdateCommand) other;
        return index.equals(e.index)
                && medicalRecord.equals(e.medicalRecord);
    }
}
