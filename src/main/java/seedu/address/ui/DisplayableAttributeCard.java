package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.person.DisplayableAttribute;

/**
 * A UI component that displays information of a {@code DisplayableAttribute}
 */
public class DisplayableAttributeCard extends UiPart<Region> {
    private static final String FXML = "DisplayableAttributeCard.fxml";

    public final DisplayableAttribute displayableAttribute;

    @FXML
    private Label id;

    @FXML
    private Label date;

    @FXML
    private Label diagnosis;

    @FXML
    private Label treatment;

    @FXML
    private Label comments;

    public DisplayableAttributeCard(DisplayableAttribute displayableAttribute, int displayedIndex) {
        super(FXML);
        this.displayableAttribute = displayableAttribute;
        id.setText(displayedIndex + ". ");
        if (displayableAttribute instanceof MedicalRecord) {
            date.setText(((MedicalRecord) displayableAttribute).getDate());
            diagnosis.setText("Diagnosis: " + ((MedicalRecord) displayableAttribute).getDiagnosis());
            treatment.setText("Treatment: " + ((MedicalRecord) displayableAttribute).getTreatment());
            comments.setText("Comments: " + ((MedicalRecord) displayableAttribute).getComments());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayableAttributeCard)) {
            return false;
        }

        // state check
        DisplayableAttributeCard card = (DisplayableAttributeCard) other;
        return id.getText().equals(card.id.getText())
                && displayableAttribute.equals(card.displayableAttribute);
    }
}
