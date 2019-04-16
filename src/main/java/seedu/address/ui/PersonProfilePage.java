package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Date;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * A UI component that displays full information of a {@code Person}.
 */
public class PersonProfilePage extends UiPart<Region> {
    private static AnimationTimer animationTimer;
    private static Person personOnDisplay;

    private static final String EMPTY_VALUE = "";
    private static final String FXML = "PersonProfilePage.fxml";

    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);


    @FXML
    private Text name;
    @FXML
    private Text nric;
    @FXML
    private Text labelNric;
    @FXML
    private Text phone;
    @FXML
    private Text labelPhone;
    @FXML
    private Text address;
    @FXML
    private Text labelAddress;
    @FXML
    private Text email;
    @FXML
    private Text labelEmail;
    @FXML
    private Text availCheckTime;
    @FXML
    private Label availability;
    @FXML
    private Text availabilityLabel;
    @FXML
    private Text dept;
    @FXML
    private Text labelDept;
    @FXML
    private FlowPane tags;

    public PersonProfilePage() {
        super(FXML);
        registerAsAnEventHandler(this);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                setAvailabilityOfDoctor();
            }
        };
    }

    @Subscribe
    private void handlePersonChangedEvent(PersonChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        if (event.editedPerson == null) {
            Platform.runLater(()-> {
                showDefaultProfilePage(); // if person is deleted of database is cleared, display the default scene.
            });
            return;
        } else if (!event.originalPerson.equals(personOnDisplay)) {
            return; // if the person updated is not the person that is being displayed on the UI.
        }

        Person updatedPerson = event.editedPerson;
        updateScene(updatedPerson);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person selectedPerson = event.getNewSelection();
        updateScene(selectedPerson);
    }

    /**
     * Helper method to update the contents of the profile ui base on the details of the {@code person}.
     */
    private void updateScene(Person person) {
        showLabels();
        personOnDisplay = person;
        name.setText(person.getName().fullName);
        nric.setText(person.getNric().code);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Updates availability badge of doctor every minute to reflect real time status.
        if (person instanceof Doctor) {
            setAvailabilityOfDoctor();
            animationTimer.start();
        } else {
            assert person instanceof Patient;
            animationTimer.stop();
            hideDoctorFields();
        }
    }

    /**
     * Updates the contents of the ui to be empty
     */
    private void showDefaultProfilePage() {
        personOnDisplay = null;
        name.setText(EMPTY_VALUE);
        nric.setText(EMPTY_VALUE);
        phone.setText(EMPTY_VALUE);
        address.setText(EMPTY_VALUE);
        email.setText(EMPTY_VALUE);
        tags.getChildren().clear();
        hideDoctorFields();
        hideLabels();
    }

    /**
     * Helper method to display the labels for each {@person}'s field.
     */
    private void showLabels() {
        labelAddress.setVisible(true);
        labelDept.setVisible(true);
        labelEmail.setVisible(true);
        labelPhone.setVisible(true);
        labelNric.setVisible(true);
    }

    /**
     * Helper method to hide all labels.
     */
    private void hideLabels() {
        labelAddress.setVisible(false);
        labelDept.setVisible(false);
        labelEmail.setVisible(false);
        labelPhone.setVisible(false);
        labelNric.setVisible(false);
    }

    /**
     * Helper method that sets the availability labels of the doctor.
     */
    private void setAvailabilityOfDoctor() {
        Doctor doctor = (Doctor) personOnDisplay;
        labelDept.setVisible(true);
        dept.setText(doctor.getMedicalDepartment().deptName);
        availability.setVisible(true);
        availabilityLabel.setVisible(true);
        availCheckTime.setVisible(true);
        availability.setText(doctor.currentAvailStatus());
        availCheckTime.setText("last updated:  " + Date.getCurrentDate() + "," + Time.getCurrentTime());
        if (doctor.currentAvailStatus().equals(doctor.IS_AVAILABLE)) {
            availability.setStyle("-fx-background-color: #33ff77");
        } else {
            availability.setStyle("-fx-background-color: #ff4d4d");
        }
    }

    /**
     * Helper method that sets the visibility of the labels (that are applicable to Doctors only) to false.
     */
    private void hideDoctorFields() {
        animationTimer.stop();
        dept.setText(EMPTY_VALUE);
        labelDept.setVisible(false);
        availability.setVisible(false);
        availCheckTime.setVisible(false);
        availabilityLabel.setVisible(false);
    }
}
