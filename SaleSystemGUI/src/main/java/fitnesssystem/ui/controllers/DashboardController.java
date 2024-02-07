package fitnesssystem.ui.controllers;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.logic.ActivityLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
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
            Label distanceTitleLabel=new Label("Distance ");
            Label timeTitleLabel=new Label("Time ");
            Label elevationOrPaceLabelTitle=new Label();
            Label distanceLabel = new Label(activity.getDistance() + " km");
            Label timeLabel = new Label(activity.getNormalizedDuration());
            Label elevationOrPaceLabel=new Label();
            Label emptyRow=new Label();
            if (Objects.equals(activity.getSport().getName(), "Bike")) {
                elevationOrPaceLabelTitle.setText("Elevation");
                elevationOrPaceLabel.setText(String.valueOf(activity.getElevation()) + " m");
            } else {
                elevationOrPaceLabelTitle.setText("Pace");
                double paceInSecondsPerKm = activity.getDuration().getSeconds() / activity.getDistance();
                int paceMinutes = (int) paceInSecondsPerKm / 60;
                int paceSeconds = (int) paceInSecondsPerKm % 60;
                String paceFormatted = String.format("%d:%02d /km", paceMinutes, paceSeconds);
                elevationOrPaceLabel.setText(paceFormatted);
            }

            styleActivityLabels(nameLabel,titleLabel);

            HBox metricsTitlesBox = createHBox(distanceTitleLabel, timeTitleLabel, elevationOrPaceLabelTitle);
            HBox metricsBox = createHBox(distanceLabel, timeLabel, elevationOrPaceLabel);

            activitiesContainer.getChildren().addAll(nameLabel,dateAndLocationLabel,titleLabel,metricsTitlesBox,metricsBox,emptyRow);
        }
    }

    private HBox createHBox(Label... labels) {
        HBox hbox = new HBox();
        for (Label label : labels) {
            HBox wrapper = new HBox(label);
            HBox.setHgrow(wrapper, Priority.ALWAYS);
            hbox.getChildren().add(wrapper);
        }
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private void styleActivityLabels(Label nameLabel,Label titleLabel) {
        nameLabel.setFont(Font.font(13));
        titleLabel.setFont(Font.font("", FontWeight.BOLD,16));
    }


}