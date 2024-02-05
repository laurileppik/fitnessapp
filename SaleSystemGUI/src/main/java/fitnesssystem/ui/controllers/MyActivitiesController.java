package fitnesssystem.ui.controllers;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.dataobjects.Sport;
import fitnesssystem.logic.ActivityLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSport().getName()));
        dateColumn.setCellValueFactory(createDateCellFactory());
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        timeColumn.setCellValueFactory(createTimeCellFactory());
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        elevationColumn.setCellValueFactory(new PropertyValueFactory<>("elevation"));

        findAllActivities();
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
                Duration duration = cellData.getValue().getDuration();
                long seconds = duration.getSeconds();
                long HH = seconds / 3600;
                long MM = (seconds % 3600) / 60;
                long SS = seconds % 60;
                String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);

                return new SimpleStringProperty(timeInHHMMSS);
            } else {
                return new SimpleStringProperty("");
            }
        };
    }

    private void findAllActivities() {
        List<Activity> activityList= activityLogic.getAllActivities();
        ObservableList<Activity> activities = FXCollections.observableArrayList(activityList);
        activitiesTableView.setItems(activities);
        for (Activity activity: activities) {
            System.out.println(activity.getDuration().getSeconds());
            //activitiesTableView.set
        }
    }
}
