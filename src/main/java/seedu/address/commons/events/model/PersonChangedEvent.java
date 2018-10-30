package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/** Indicates the person in the model has changed */
public class PersonChangedEvent extends BaseEvent {

    public final Person editedPerson;
    public final Person originalPerson;

    public PersonChangedEvent(Person originalPerson, Person editedPerson) {
        this.originalPerson = originalPerson;
        this.editedPerson = editedPerson;
    }

    @Override
    public String toString() {
        return "Person data modified: " + editedPerson;
    }
}
