package fitnesssystem.ui.controllers;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.logic.ActivityLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
            Label dateAndLocationLabel=new Label(activity.getDateAndLocationOfActivity());
            Label titleLabel=new Label(activity.getTitle());
            Label distanceLabel = new Label("Distance: " + activity.getDistance() + " km");
            Label timeLabel = new Label("Time: " + activity.getNormalizedDuration());
            Label emptyRow=new Label();

            styleActivityLabels(nameLabel,titleLabel);

            activitiesContainer.getChildren().addAll(nameLabel,dateAndLocationLabel,titleLabel,distanceLabel, timeLabel,emptyRow);
        }
    }

    private void styleActivityLabels(Label nameLabel,Label titleLabel) {
        nameLabel.setFont(Font.font(13));
        titleLabel.setFont(Font.font("", FontWeight.BOLD,16));
    }


}