package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String EMPTY_VALUE = ""; // empty value to set for the availability of patients.

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label role;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label medicalRecord;
    @FXML
    private Label appointment;
    @FXML
    private Label availability;
    @FXML
    private Label uniqueField;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        if (person instanceof Doctor) {
            Doctor doctor = (Doctor) person;
            uniqueField.setText(doctor.getMedicalDepartment().deptName);
            availability.setText(doctor.currentAvailStatus());
            if (doctor.currentAvailStatus().equals(doctor.IS_AVAILABLE)) {
                availability.setStyle("-fx-background-color: #33ff77");
            } else {
                availability.setStyle("-fx-background-color: #ff4d4d");
            }
        } else if (person instanceof Patient) {
            uniqueField.setText(((Patient) person).getMedicalRecord().value);
            availability.setText(EMPTY_VALUE);
        }
        role.setText(person.getClass().getSimpleName());
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        appointment.setText(person.getAppointment().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
