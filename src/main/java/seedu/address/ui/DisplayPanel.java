package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.patient.MedicalRecord;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.DisplayableAttribute;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of displayable attributes.
 */
public class DisplayPanel extends UiPart<Region> {
    private static final String FXML = "DisplayPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);

    @FXML
    private ListView<DisplayableAttribute> displayableAttributeListView;

    public DisplayPanel() {
        super(FXML);
        showDefaultDisplayPanel();
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<DisplayableAttribute> displayableList) {
        displayableAttributeListView.setItems(displayableList);
        displayableAttributeListView.setCellFactory(listView -> new DisplayPanel.DisplayableListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    public void showDefaultDisplayPanel() {
        displayableAttributeListView.setItems(new FilteredList<>(FXCollections.observableArrayList()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person selectedPerson = event.getNewSelection();
        if (selectedPerson instanceof Patient) {
            ArrayList<MedicalRecord> selectedPersonMedicalRecordLibrary = ((Patient) selectedPerson)
                    .getMedicalRecordLibrary();
            //Collections.reverse(selectedPersonMedicalRecordLibrary);
            ArrayList<Appointment> selectedPersonAppointmentList = selectedPerson.getAppointmentList();
            ArrayList<DisplayableAttribute> displayableAttributesList = new ArrayList<>();
            for (MedicalRecord medicalRecord : selectedPersonMedicalRecordLibrary) {
                displayableAttributesList.add((DisplayableAttribute) medicalRecord);
            }
            for (Appointment appointment : selectedPersonAppointmentList) {
                displayableAttributesList.add((DisplayableAttribute) appointment);
            }

            setConnections(new FilteredList<>(FXCollections.observableArrayList(displayableAttributesList)));
        } else {
            ArrayList<Appointment> selectedPersonAppointmentList = selectedPerson.getAppointmentList();
            setConnections(new FilteredList<>((FXCollections.observableArrayList(selectedPersonAppointmentList))));
        }
    }

    private void setEventHandlerForSelectionChangeEvent() {
        displayableAttributeListView.getSelectionModel().selectedItemProperty()
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
            super.updateItem(displayableAttribute, empty);

            if (empty || displayableAttribute == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayableAttributeCard(displayableAttribute, getIndex() + 1).getRoot());
            }
        }
    }


}
