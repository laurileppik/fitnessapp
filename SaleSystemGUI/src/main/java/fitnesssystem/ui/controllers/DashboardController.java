package fitnesssystem.ui.controllers;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;


import fitnesssystem.dataobjects.Activity;
import fitnesssystem.logic.ActivityLogic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.alternativevision.gpx.beans.Waypoint;

import java.net.URL;
import java.util.*;
import java.util.List;

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
            Label emptyRow=new Label();
            Label elevationOrPaceLabel = initalizeElevationOrPaceLabel(activity, elevationOrPaceLabelTitle);

            MapView mapView = initalizeMapView();

            styleActivityLabels(nameLabel,titleLabel);

            HBox metricsTitlesBox = createHBox(distanceTitleLabel, timeTitleLabel, elevationOrPaceLabelTitle);
            HBox metricsBox = createHBox(distanceLabel, timeLabel, elevationOrPaceLabel);

            VBox activityBox = new VBox(nameLabel, dateAndLocationLabel, titleLabel, metricsTitlesBox, metricsBox, emptyRow, mapView);
            activityBox.setStyle("-fx-background-color: lightgrey;");
            activityBox.setOnMouseClicked(event -> {
                System.out.println("Activity " + activity.getTitle() + " clicked");

            });

            activitiesContainer.getChildren().add(activityBox);
            //activitiesContainer.prefWidthProperty().bind(activitiesContainer.getScene().widthProperty().multiply(0.75));
        }
    }

    private static Label initalizeElevationOrPaceLabel(Activity activity, Label elevationOrPaceLabelTitle) {
        Label elevationOrPaceLabel=new Label();
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
        return elevationOrPaceLabel;
    }

    private MapView initalizeMapView() {
        MapView mapView = new MapView();
        mapView.initialize();
        mapView.setCenter(new Coordinate(58.38, 26.73));
        mapView.setZoom(10);
        mapView.setPrefSize(400, 200);
        ArrayList<Waypoint> trackCoordinates=activityLogic.getActivityTrackPoints();
        System.out.println(trackCoordinates);
        return mapView;
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