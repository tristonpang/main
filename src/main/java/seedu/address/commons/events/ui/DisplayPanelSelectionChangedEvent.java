package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.DisplayableAttribute;

/**
 * Represents a selection change in the Display Panel
 */
public class DisplayPanelSelectionChangedEvent extends BaseEvent {


    private final DisplayableAttribute newSelection;

    public DisplayPanelSelectionChangedEvent(DisplayableAttribute newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public DisplayableAttribute getNewSelection() {
        return newSelection;
    }
}

