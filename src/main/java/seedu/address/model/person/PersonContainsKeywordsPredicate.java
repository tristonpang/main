package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Person}'s attributes matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName()).append(" ")
                .append(person.getPhone()).append(" ")
                .append(person.getEmail()).append(" ")
                .append(person.getAddress()).append(" ")
                .append(person.getAppointment()).append(" ")
                .append(person.getClass().getSimpleName()).append(" ");
        person.getTags().stream()
                .map(x -> x.toString().replaceAll("[\\[\\]]", ""))
                .map(x -> x + " ")
                .forEach(builder::append);

        if (person instanceof Doctor) {
            builder.append(((Doctor) person).getMedicalDepartment()).append(" ");
        } else if (person instanceof Patient) {
            builder.append(((Patient) person).getNric()).append(" ")
                    .append(((Patient) person).getMedicalRecord()).append(" ");
        }

        return keywords.stream()
                .anyMatch(keyword -> {
                    return StringUtil.containsWordIgnoreCase(builder.toString()
                            .replaceAll(",", " "), keyword);
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}