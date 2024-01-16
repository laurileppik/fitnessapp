package fitnesssystem.ui.controllers;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.dataobjects.Sport;
import fitnesssystem.logic.ActivityLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
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
    private TableColumn<Activity, LocalDate> dateColumn;
    @FXML
    private TableColumn<Activity, String> titleColumn;
    @FXML
    private TableColumn<Activity, Duration> timeColumn;
    @FXML
    private TableColumn<Activity, Double> distanceColumn;
    @FXML
    private TableColumn<Activity, Integer> elevationColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sportColumn.setCellValueFactory(new PropertyValueFactory<>("sport"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        elevationColumn.setCellValueFactory(new PropertyValueFactory<>("elevation"));

        findAllActivities();
    }

    private void findAllActivities() {
        List<Activity> activityList= activityLogic.getAllActivities();
        ObservableList<Activity> activities = FXCollections.observableArrayList(activityList);
        activitiesTableView.setItems(activities);
    }
}
