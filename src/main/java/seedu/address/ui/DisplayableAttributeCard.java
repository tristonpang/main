package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.person.Appointment;
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
    private Label displayableField1;

    @FXML
    private Label displayableField2;

    @FXML
    private Label displayableField3;

    public DisplayableAttributeCard(DisplayableAttribute displayableAttribute, int displayedIndex) {
        super(FXML);
        this.displayableAttribute = displayableAttribute;
        if (displayableAttribute instanceof MedicalRecord) {
            id.setText(displayedIndex + ". ");
            date.setText(((MedicalRecord) displayableAttribute).getDate());
            displayableField1.setText("Diagnosis: " + ((MedicalRecord) displayableAttribute).getDiagnosis());
            displayableField2.setText("Treatment: " + ((MedicalRecord) displayableAttribute).getTreatment());
            displayableField3.setText("Comments: " + ((MedicalRecord) displayableAttribute).getComments());
        } else if (displayableAttribute instanceof Appointment) {
            if (((Appointment) displayableAttribute).value.equals("")) {
                return;
            }

            id.setText(displayedIndex + ". ");
            Appointment appointment = (Appointment) displayableAttribute;
            date.setText(appointment.getDateString());
            displayableField1.setText("Start Time: " + appointment.getStartTimeString()
                    + "\t\tEnd Time: " + appointment.getEndTimeString());
            displayableField2.setText("Doctor Name: " + appointment.getDoctorNameString()
                    + " (" + appointment.getDoctorNricString() + ")");
            displayableField3.setText("Patient Name: " + appointment.getPatientNameString()
                    + " (" + appointment.getPatientNricString() + ")");
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
