package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GLOBAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_RECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NRIC;
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
        builder.append(PREFIX_NAME.toString() + person.getName()).append(" ")
                .append(PREFIX_PHONE.toString() + person.getPhone()).append(" ")
                .append(PREFIX_EMAIL.toString() + person.getEmail()).append(" ")
                .append(PREFIX_ADDRESS.toString() + person.getAddress()).append(" ")
                .append(PREFIX_ROLE.toString() + person.getClass().getSimpleName()).append(" ");
        person.getTags().stream()
                .map(x -> x.toString().replaceAll("[\\[\\]]", ""))
                .map(x -> PREFIX_TAG.toString() + x + " ")
                .forEach(builder::append);

        if (person instanceof Doctor) {
            builder.append(PREFIX_MEDICAL_DEPARTMENT.toString() + ((Doctor) person).getMedicalDepartment()).append(" ");
        } else if (person instanceof Patient) {
            builder.append(PREFIX_PATIENT_NRIC.toString() + ((Patient) person).getNric()).append(" ")
                    .append(PREFIX_MEDICAL_RECORD.toString() + ((Patient) person).getMedicalRecord()).append(" ");
        }

        boolean isAnyKeywordMatch = personSearchKeywords.get(PREFIX_GLOBAL) != null
                && personSearchKeywords.get(PREFIX_GLOBAL).stream().anyMatch(keyword -> {
                    return StringUtil.containsWordIgnoreCase(builder.toString().replaceAll(",", " "),
                            keyword);
                        });

        boolean isAnyNameMatch = personSearchKeywords.get(PREFIX_NAME) != null
                && personSearchKeywords.get(PREFIX_NAME).stream()
                .anyMatch(name -> StringUtil.containsWordIgnoreCase(person.getName().toString(), name));

        boolean isAnyPhoneMatch = personSearchKeywords.get(PREFIX_PHONE) != null
                && personSearchKeywords.get(PREFIX_PHONE).stream()
                .anyMatch(phone -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), phone));

        boolean isAnyEmailMatch = personSearchKeywords.get(PREFIX_EMAIL) != null
                && personSearchKeywords.get(PREFIX_EMAIL).stream()
                .anyMatch(email -> StringUtil.containsWordIgnoreCase(person.getEmail().toString(), email));

        boolean isAnyAddressMatch = personSearchKeywords.get(PREFIX_ADDRESS) != null
                && personSearchKeywords.get(PREFIX_ADDRESS).stream()
                .anyMatch(address -> StringUtil.containsWordIgnoreCase(person.getAddress().toString(), address));

        boolean isAnyRoleMatch = personSearchKeywords.get(PREFIX_ROLE) != null
                && personSearchKeywords.get(PREFIX_ROLE).stream()
                .anyMatch(role -> StringUtil.containsWordIgnoreCase(person.getClass().getSimpleName(), role));

        boolean isAnyTagMatch = personSearchKeywords.get(PREFIX_TAG) != null
                && personSearchKeywords.get(PREFIX_TAG).stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(person.getTags().toString(), tag));

        boolean isAnyMedicalDepartmentMatch = person instanceof Doctor
                && personSearchKeywords.get(PREFIX_MEDICAL_DEPARTMENT) != null
                && personSearchKeywords.get(PREFIX_MEDICAL_DEPARTMENT).stream()
                .anyMatch(medicalDepartment ->
                        StringUtil.containsWordIgnoreCase(((Doctor) person).getMedicalDepartment().toString(),
                                medicalDepartment));

        boolean isAnyDoctorNricMatch = person instanceof Doctor
                && personSearchKeywords.get(PREFIX_DOCTOR_NRIC) != null
                && personSearchKeywords.get(PREFIX_DOCTOR_NRIC).stream()
                .anyMatch(doctorNric -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), doctorNric));

        boolean isAnyPatientNricMatch = person instanceof Patient
                && personSearchKeywords.get(PREFIX_PATIENT_NRIC) != null
                && personSearchKeywords.get(PREFIX_PATIENT_NRIC).stream()
                .anyMatch(patientNric -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), patientNric));

        boolean isAnyMedicalRecordMatch = person instanceof Patient
                && personSearchKeywords.get(PREFIX_MEDICAL_RECORD) != null
                && personSearchKeywords.get(PREFIX_MEDICAL_RECORD).stream()
                .anyMatch(medicalRecord ->
                        StringUtil.containsWordIgnoreCase(((Patient) person).getMedicalRecord().toString(),
                                medicalRecord));

        return isAnyKeywordMatch
                || isAnyNameMatch
                || isAnyPhoneMatch
                || isAnyEmailMatch
                || isAnyAddressMatch
                || isAnyRoleMatch
                || isAnyTagMatch
                || isAnyMedicalDepartmentMatch
                || isAnyDoctorNricMatch
                || isAnyPatientNricMatch
                || isAnyMedicalRecordMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && personSearchKeywords.equals(((PersonContainsKeywordsPredicate) other)
                .personSearchKeywords)); // state check
    }

}
