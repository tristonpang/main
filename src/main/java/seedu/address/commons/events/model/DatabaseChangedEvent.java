package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the active database has changed.
 */
public class DatabaseChangedEvent extends BaseEvent {
    private String activeRole;

    public DatabaseChangedEvent(String activeRole) {
        this.activeRole = activeRole.toUpperCase();
    }

    @Override
    public String toString() {
        return "Database has been switched: " + activeRole;
    }
}
