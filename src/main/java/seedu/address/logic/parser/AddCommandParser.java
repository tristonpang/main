package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_DOCTOR_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.MedicalDepartment;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_NRIC, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_MEDICAL_DEPARTMENT);

        // Ensures information for common prefixes are entered.
        if (!arePrefixesPresent(argMultimap, PREFIX_ROLE, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_GENERAL_USAGE));
        }

        if (!isRoleOf(Role.DOCTOR, argMultimap) && !isRoleOf(Role.PATIENT, argMultimap)) {
            throw new ParseException(String.format(Role.MESSAGE_ROLE_CONSTRAINTS, AddCommand.MESSAGE_GENERAL_USAGE));
        }

        // Ensures Medical Department field is not empty when adding a Doctor.
        if (isRoleOf(Role.DOCTOR, argMultimap) && !arePrefixesPresent(argMultimap, PREFIX_MEDICAL_DEPARTMENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_DOCTOR_FORMAT,
                    AddCommand.MESSAGE_DOCTOR_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, nric, phone, email, address, tagList);
        if (isRoleOf(Role.PATIENT, argMultimap)) {
            person = new Patient(name, nric, phone, email, address, tagList);
        } else if (isRoleOf(Role.DOCTOR, argMultimap)) {
            MedicalDepartment medicalDepartment =
                    ParserUtil.parseMedicalDepartment(argMultimap.getValue((PREFIX_MEDICAL_DEPARTMENT)).get());
            person = new Doctor(name, nric, phone, email, address, tagList, medicalDepartment);
        }

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the role in args matches with specified the Enum role.
     */
    private static boolean isRoleOf(Enum role, ArgumentMultimap argMultiMap) {
        String person = argMultiMap.getValue(PREFIX_ROLE).get();
        return person != null && person.equalsIgnoreCase(role.toString());
    }
}
