package fitnesssystem.ui.controllers;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.logic.ActivityLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private final ActivityLogic activityLogic;
    @FXML
    private VBox activitiesContainer;
    @FXML
    private VBox nameField;
    @FXML
    private VBox activityField;

    public DashboardController(ActivityLogic activityLogic) {
        this.activityLogic = activityLogic;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayActivities(activityLogic.getAllActivities());
    }

    private void displayActivities(List<Activity> activities) {
        for (Activity activity : activities) {
            Label nameLabel=new Label(activity.getAthlete().getFirstName() + " " + activity.getAthlete().getLastName());
            Label distanceLabel = new Label("Distance: " + activity.getDistance() + " km");
            Label timeLabel = new Label("Time: " + activity.getNormalizedDuration());
            Label emptyRow=new Label();

            activitiesContainer.getChildren().addAll(nameLabel,distanceLabel, timeLabel,emptyRow);
            //nameField.getChildren().addAll(nameLabel);
            //activityField.getChildren().addAll(distanceLabel, timeLabel);
        }
    }
}
