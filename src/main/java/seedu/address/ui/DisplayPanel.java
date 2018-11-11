package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.commons.events.ui.DisplayPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.DisplayableAttribute;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of displayable attributes.
 */
public class DisplayPanel extends UiPart<Region> {
    private static Person personOnDisplay;
    private static final String FXML = "DisplayPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);

    @FXML
    private ListView<DisplayableAttribute> displayableAppointmentsListView;
    @FXML
    private ListView<DisplayableAttribute> displayableMedicalRecordsListView;

    public DisplayPanel() {
        super(FXML);
        showDefaultDisplayPanel();
        registerAsAnEventHandler(this);
    }

    private void setAppointmentsConnections(ObservableList<DisplayableAttribute> displayableList) {
        displayableAppointmentsListView.setItems(displayableList);
        displayableAppointmentsListView.setCellFactory(listView -> new DisplayPanel.DisplayableListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setMedicalRecordsConnections(ObservableList<DisplayableAttribute> displayableList) {
        displayableMedicalRecordsListView.setItems(displayableList);
        displayableMedicalRecordsListView.setCellFactory(listView -> new DisplayPanel.DisplayableListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Subscribe
    private void handlePersonChangedEvent(PersonChangedEvent event) {
        if (event.editedPerson == null) {
            showDefaultDisplayPanel(); // if person is deleted or database has been cleared, show the default scene
            return;
        } else if (!event.originalPerson.equals(personOnDisplay)) {
            return; // if person updated in the event is not related to this person being displayed on the UI
        }
        updateScene(event.editedPerson);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        Person selectedPerson = event.getNewSelection();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateScene(selectedPerson);
    }

    /**
     * Default setting for display panel upon start up of application.
     */
    public void showDefaultDisplayPanel() {
        Platform.runLater(() -> {
            personOnDisplay = null;
            displayableAppointmentsListView.setItems(new FilteredList<>(FXCollections.observableArrayList()));
            displayableMedicalRecordsListView.setItems(new FilteredList<>(FXCollections.observableArrayList()));
        });
    }

    /**
     * Helper method to update the UI display base on the details of the {@person}.
     */
    private void updateScene(Person updatedPerson) {
        personOnDisplay = updatedPerson;
        if (updatedPerson instanceof Patient) {
            ArrayList<MedicalRecord> selectedPersonMedicalRecordLibrary = ((Patient) updatedPerson)
                    .getMedicalRecordLibrary();
            //Collections.reverse(selectedPersonMedicalRecordLibrary);
            ArrayList<Appointment> selectedPersonAppointmentList = updatedPerson.getAppointmentList();
            ArrayList<DisplayableAttribute> displayableMedicalRecordsList = new ArrayList<>();
            ArrayList<DisplayableAttribute> displayableAppointmentsList = new ArrayList<>();
            for (MedicalRecord medicalRecord : selectedPersonMedicalRecordLibrary) {
                displayableMedicalRecordsList.add((DisplayableAttribute) medicalRecord);
            }
            for (Appointment appointment : selectedPersonAppointmentList) {
                displayableAppointmentsList.add((DisplayableAttribute) appointment);
            }

            setAppointmentsConnections(
                    new FilteredList<>(FXCollections.observableArrayList(displayableAppointmentsList)));
            setMedicalRecordsConnections(
                    new FilteredList<>(FXCollections.observableArrayList(displayableMedicalRecordsList)));
        } else {
            assert updatedPerson instanceof Doctor;
            ArrayList<Appointment> selectedPersonAppointmentList = updatedPerson.getAppointmentList();
            setAppointmentsConnections(
                    new FilteredList<>(FXCollections.observableArrayList(selectedPersonAppointmentList)));
            setMedicalRecordsConnections(
                    new FilteredList<>(FXCollections.observableArrayList()));
        }
    }

    private void setEventHandlerForSelectionChangeEvent() {
        displayableAppointmentsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in displayable attribute list panel changed to : '"
                                + newValue + "'");
                        raise(new DisplayPanelSelectionChangedEvent(newValue));
                    }
                });

        displayableMedicalRecordsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in displayable attribute list panel changed to : '"
                                + newValue + "'");
                        raise(new DisplayPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DisplayableAttribute}
     * using a {@code DisplayableAttributeCard}.
     */
    class DisplayableListViewCell extends ListCell<DisplayableAttribute> {
        @Override
        protected void updateItem(DisplayableAttribute displayableAttribute, boolean empty) {
            Platform.runLater(()-> {
                super.updateItem(displayableAttribute, empty);

                if (empty || displayableAttribute == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new DisplayableAttributeCard(displayableAttribute, getIndex() + 1).getRoot());
                }
            });
        }
    }
}
