package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Doctor.
 */
public class DoctorUtil extends PersonUtil {

    public static String getAddCommand(Doctor source) {
        return AddCommand.COMMAND_WORD + " " + PREFIX_ROLE + "Doctor " + getDoctorDetails(source);
    }

    /**
     * Returns the part of command string for the given {@code doctor}'s details.
     */
    public static String getDoctorDetails(Doctor doctor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + doctor.getName().fullName + " ");
        sb.append(PREFIX_NRIC + doctor.getNric().code + " ");
        sb.append(PREFIX_PHONE + doctor.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + doctor.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + doctor.getAddress().value + " ");
        doctor.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        sb.append(PREFIX_MEDICAL_DEPARTMENT + doctor.getMedicalDepartment().deptName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string that belongs to only {@code doctor}'s details.
     */
    public static String getEditDoctorDescriptorDetails(EditCommand.EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ROLE).append("Doctor ");
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getNric().ifPresent(nric -> sb.append(PREFIX_NRIC).append(nric.code).append(" "));
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
        descriptor.getMedicalDepartment().ifPresent(medicalDepartment ->
                sb.append(PREFIX_MEDICAL_DEPARTMENT).append(medicalDepartment.deptName).append(" "));
        return sb.toString();
    }
}
