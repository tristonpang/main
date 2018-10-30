package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private static final String DEFAULT_IMAGE_URL = "blank_profile";
    private static final String EMPTY_VALUE = "";
    private static final String FXML = "PersonProfilePage.fxml";

    private static final String LABEL_NAME = "Name:  ";
    private static final String LABEL_NRIC = "Nric:  ";
    private static final String LABEL_PHONE = "Contact No.:  ";
    private static final String LABEL_EMAIL = "Email:  ";
    private static final String LABEL_ADDRESS = "Address:  ";
    private static final String LABEL_DOCTOR_SPECIALISATION = "Specialisation:  ";

    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);


    @FXML
    private Text name;
    @FXML
    private Text nric;
    @FXML
    private Text phone;
    @FXML
    private Text address;
    @FXML
    private Text email;
    @FXML
    private Text availCheckTime;
    @FXML
    private Label availability;
    @FXML
    private Text availabilityLabel;
    @FXML
    private Text uniqueField;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView profileImageDisplay;

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
            showDefaultProfilePage(); // if person is deleted of database is cleared, display the default scene.
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
        personOnDisplay = person;
        name.setText(LABEL_NAME + person.getName().fullName);
        nric.setText(LABEL_NRIC + person.getNric().code);
        phone.setText(LABEL_PHONE + person.getPhone().value);
        address.setText(LABEL_ADDRESS + person.getAddress().value);
        email.setText(LABEL_EMAIL + person.getEmail().value);
        if (person instanceof Doctor) {
            setAvailabilityOfDoctor();
        } else {
            assert person instanceof Patient;
            hideDoctorFields();
        }

        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        setProfileImage(person.getNric().code);
        // Updates availability badge of doctor every minute to reflect real time status.
        if (person instanceof Doctor) {
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
        hideDoctorFields();
        tags.getChildren().clear();
        profileImageDisplay.setVisible(false);
    }

    /**
     * Helper method to set the profile image of the person. Uses the default profile image if no other images
     * could be found in the database.
     */
    private void setProfileImage(String imageCode) {
        String imagePath = "/images/" + imageCode + ".png";
        BufferedImage profileImageUrl;

        try {
            profileImageUrl = ImageIO.read(PersonProfilePage.class.getResource(imagePath));
            Image profileImage = SwingFXUtils.toFXImage(profileImageUrl, null);
            profileImageDisplay.setImage(profileImage);
        } catch (IOException | IllegalArgumentException e) {
            logger.info("INVALID PROFILE IMAGE PATH: " + e.getLocalizedMessage());
            setProfileImage(DEFAULT_IMAGE_URL);
        }

        profileImageDisplay.setPreserveRatio(false);
        profileImageDisplay.setFitWidth(200);
        profileImageDisplay.setFitHeight(200);
        profileImageDisplay.setVisible(true);
    }

    /**
     * Helper method that sets the availability labels of the doctor.
     */
    private void setAvailabilityOfDoctor() {
        Doctor doctor = (Doctor) personOnDisplay;
        uniqueField.setText(LABEL_DOCTOR_SPECIALISATION + doctor.getMedicalDepartment().deptName);
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
        uniqueField.setText(EMPTY_VALUE);
        availability.setVisible(false);
        availCheckTime.setVisible(false);
        availabilityLabel.setVisible(false);
    }
}
