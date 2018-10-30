package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GLOBAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_RECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Person}'s attributes matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final Map<Prefix, List<String>> personSearchKeywords;

    public PersonContainsKeywordsPredicate(Map<Prefix, List<String>> personSearchKeywords) {
        this.personSearchKeywords = personSearchKeywords;
    }

    @Override
    public boolean test(Person person) {
        final StringBuilder builder = new StringBuilder();

        builder.append(person.getName()).append(" ")
                .append(person.getNric()).append(" ")
                .append(person.getPhone()).append(" ")
                .append(person.getEmail()).append(" ")
                .append(person.getAddress().toString().replaceAll(",", "")).append(" ")
                .append(person.getClass().getSimpleName()).append(" ");
        person.getTags().stream()
                .map(x -> x.toString().replaceAll("[\\[\\]]", ""))
                .map(x -> x + " ")
                .forEach(builder::append);

        if (person instanceof Doctor) {
            builder.append(((Doctor) person).getMedicalDepartment()).append(" ");
        } else if (person instanceof Patient) {
            builder.append(person.getNric()).append(" ");
            for (String s : ((Patient) person).getMedicalRecordKeywords()) {
                builder.append(s + " ");
            }
        }

        boolean isAnyKeywordMatch = personSearchKeywords.get(PREFIX_GLOBAL) == null
                || personSearchKeywords.get(PREFIX_GLOBAL).stream().anyMatch(keyword -> {
                    return StringUtil.containsQueryIgnoreCase(builder.toString().replaceAll(",", " "),
                            keyword);
                });

        boolean isAnyNameMatch = personSearchKeywords.get(PREFIX_NAME) == null
                || personSearchKeywords.get(PREFIX_NAME).stream()
                .anyMatch(name -> StringUtil.containsQueryIgnoreCase(person.getName().toString(), name));

        boolean isAnyNricMatch = personSearchKeywords.get(PREFIX_NRIC) == null
                || personSearchKeywords.get(PREFIX_NRIC).stream()
                .anyMatch(nric -> StringUtil.containsQueryIgnoreCase(person.getNric().toString(), nric));

        boolean isAnyPhoneMatch = personSearchKeywords.get(PREFIX_PHONE) == null
                || personSearchKeywords.get(PREFIX_PHONE).stream()
                .anyMatch(phone -> StringUtil.containsQueryIgnoreCase(person.getPhone().toString(), phone));

        boolean isAnyEmailMatch = personSearchKeywords.get(PREFIX_EMAIL) == null
                || personSearchKeywords.get(PREFIX_EMAIL).stream()
                .anyMatch(email -> StringUtil.containsQueryIgnoreCase(person.getEmail().toString(), email));

        boolean isAnyAddressMatch = personSearchKeywords.get(PREFIX_ADDRESS) == null
                || personSearchKeywords.get(PREFIX_ADDRESS).stream()
                .anyMatch(address -> StringUtil.containsQueryIgnoreCase(person.getAddress().toString(), address));

        boolean isAnyRoleMatch = personSearchKeywords.get(PREFIX_ROLE) == null
                || personSearchKeywords.get(PREFIX_ROLE).stream()
                .anyMatch(role -> StringUtil.containsQueryIgnoreCase(person.getClass().getSimpleName(), role));

        boolean isAnyTagMatch = personSearchKeywords.get(PREFIX_TAG) == null
                || personSearchKeywords.get(PREFIX_TAG).stream()
                .anyMatch(tag -> StringUtil.containsQueryIgnoreCase(person.getTags().toString(), tag));

        boolean isAnyMedicalDepartmentMatch = personSearchKeywords.get(PREFIX_MEDICAL_DEPARTMENT) == null
                || person instanceof Doctor && personSearchKeywords.get(PREFIX_MEDICAL_DEPARTMENT).stream()
                .anyMatch(medicalDepartment ->
                        StringUtil.containsQueryIgnoreCase(((Doctor) person).getMedicalDepartment().toString(),
                                medicalDepartment));

        boolean isAnyMedicalRecordMatch = personSearchKeywords.get(PREFIX_MEDICAL_RECORD) == null;
        if (person instanceof Patient && personSearchKeywords.get(PREFIX_MEDICAL_RECORD) != null) {
            for (String keywords : personSearchKeywords.get(PREFIX_MEDICAL_RECORD)) {
                for (String medicalRecord : ((Patient) person).getMedicalRecordKeywords()) {
                    isAnyMedicalRecordMatch = isAnyMedicalRecordMatch
                            || StringUtil.containsQueryIgnoreCase(medicalRecord, keywords);
                }
            }
        }

        return isAnyKeywordMatch
                && isAnyNameMatch
                && isAnyNricMatch
                && isAnyPhoneMatch
                && isAnyEmailMatch
                && isAnyAddressMatch
                && isAnyRoleMatch
                && isAnyTagMatch
                && isAnyMedicalDepartmentMatch
                && isAnyMedicalRecordMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && personSearchKeywords.equals(((PersonContainsKeywordsPredicate) other)
                .personSearchKeywords)); // state check
    }

}
