package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Patient.
 */
public class PatientUtil extends PersonUtil {

    public static String getAddCommand(Patient source) {
        return AddCommand.COMMAND_WORD + " " + PREFIX_ROLE + "Patient " + getPatientDetails(source);
    }

    /**
     * Returns the part of command string that belongs to only {@code patient}'s details.
     */
    public static String getPatientDetails(Patient source) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + source.getName().fullName + " ");
        sb.append(PREFIX_PHONE + source.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + source.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + source.getAddress().value + " ");
        source.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        sb.append(PREFIX_NRIC + source.getNric().code + " ");
        return sb.toString();
    }

    public static String getEditPatientDescriptorDetails(EditCommand.EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        descriptor.getNric().ifPresent(nric -> sb.append(" ").append(PREFIX_NRIC).append(nric.code).append(" "));
        return sb.toString();
    }
}
