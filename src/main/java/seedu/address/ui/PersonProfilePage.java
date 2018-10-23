package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.DisplayableAttribute;
import seedu.address.model.person.Person;

public class PersonProfilePage extends UiPart<Region>{
    private static final String FXML = "PersonListCard.fxml";
    private static final String EMPTY_VALUE = "";
    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);

    @FXML
    private HBox cardPane;
    @FXML
    private Label role;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label availability;
    @FXML
    private Label uniqueField;
    @FXML
    private FlowPane tags;

    public PersonProfilePage() {
        super(FXML);
        showDefaultDisplayPanel();
        registerAsAnEventHandler(this);
    }

    public void showDefaultDisplayPanel() {
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person selectedPerson = event.getNewSelection();
        name.setText(selectedPerson.getName().fullName);
        phone.setText(selectedPerson.getPhone().value);
        address.setText(selectedPerson.getAddress().value);
        email.setText(selectedPerson.getEmail().value);
        if (selectedPerson instanceof Doctor) {
            Doctor doctor = (Doctor) selectedPerson;
            uniqueField.setText(doctor.getMedicalDepartment().deptName);
            availability.setText(doctor.currentAvailStatus());
            if (doctor.currentAvailStatus().equals(doctor.IS_AVAILABLE)) {
                availability.setStyle("-fx-background-color: #33ff77");
            } else {
                availability.setStyle("-fx-background-color: #ff4d4d");
            }
        } else if (selectedPerson instanceof Patient) {
            uniqueField.setText(((Patient) selectedPerson).getMedicalRecord().value);
            availability.setText(EMPTY_VALUE);
        }
        role.setText(selectedPerson.getClass().getSimpleName());
        selectedPerson.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
