package fitnesssystem.ui.controllers;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.logic.ActivityLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class MyActivitiesController implements Initializable {
    private static final Logger log = LogManager.getLogger(MyActivitiesController.class);
    private final ActivityLogic activityLogic;

    public MyActivitiesController(ActivityLogic activityLogic) {
        this.activityLogic = activityLogic;
    }

    @FXML
    private TableView<Activity> activitiesTableView;
    @FXML
    private TableColumn<Activity, String> sportColumn;
    @FXML
    private TableColumn<Activity, String> dateColumn;
    @FXML
    private TableColumn<Activity, String> titleColumn;
    @FXML
    private TableColumn<Activity, String> timeColumn;
    @FXML
    private TableColumn<Activity, Double> distanceColumn;
    @FXML
    private TableColumn<Activity, Integer> elevationColumn;
    @FXML
    private Label noOfActivities;
    @FXML
    private ComboBox<String> listOfAllSports;
    @FXML
    private Button searchByTitle;
    @FXML
    private TextField workoutKeywords;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSport().getName()));
        dateColumn.setCellValueFactory(createDateCellFactory());
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        timeColumn.setCellValueFactory(createTimeCellFactory());
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        elevationColumn.setCellValueFactory(new PropertyValueFactory<>("elevation"));
        findAllActivities();
        noOfActivities.setText(activityLogic.numberOfActivities());
        populateListOfAllSports();

        listOfAllSports.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            sportsFilterUsed(newValue);
        });
    }

    private void populateListOfAllSports() {
        List<String> sportsNames = activityLogic.getAllSportsNames();
        ObservableList<String> observableSports = FXCollections.observableArrayList(sportsNames);
        listOfAllSports.setItems(observableSports);
    }

    private Callback<TableColumn.CellDataFeatures<Activity, String>, ObservableValue<String>> createDateCellFactory() {
        return cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (cellData.getValue().getStartTime() != null) {
                String formattedDate = cellData.getValue().getStartTime().format(formatter);
                return new SimpleStringProperty(formattedDate);
            } else {
                return new SimpleStringProperty("");
            }
        };
    }

    private Callback<TableColumn.CellDataFeatures<Activity, String>, ObservableValue<String>> createTimeCellFactory() {
        return cellData -> {
            if (cellData.getValue().getDuration() != null) {
                return new SimpleStringProperty(cellData.getValue().getNormalizedDuration());
            } else {
                return new SimpleStringProperty("");
            }
        };
    }
    private void activityListToObservableList(List<Activity> activityList){
        ObservableList<Activity> activities = FXCollections.observableArrayList(activityList);
        activitiesTableView.setItems(activities);
    }

    private void findAllActivities() {
        List<Activity> activityList= activityLogic.getAllActivities();
        activityListToObservableList(activityList);
    }

    @FXML
    private void searchButtonPressed() {
        List<Activity> activityList= activityLogic.getActivitiesContainingWord(workoutKeywords.getText());
        activityListToObservableList(activityList);
    }

    private void sportsFilterUsed(String newValue) {
        List<Activity> activityList= activityLogic.getActivitiesWithSportsFilterUsed(newValue);
        activityListToObservableList(activityList);
    }
}
