package fitnesssystem.ui.controllers;

import com.sothawo.mapjfx.*;


import fitnesssystem.dataobjects.Activity;
import fitnesssystem.logic.ActivityLogic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.alternativevision.gpx.beans.Waypoint;
import uk.recurse.geocoding.reverse.ReverseGeocoder;

import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DashboardController implements Initializable {

    private static final Coordinate coordinateTartu = new Coordinate(58.378025, 26.728493);
    private int currentActivityCounter;
    private final ActivityLogic activityLogic;
    private final List<Marker> markerList;
    private final List<MapView> mapViewList;
    private final List<CoordinateLine> trackDrawingList;
    @FXML
    private VBox activitiesContainer;
    @FXML
    private VBox nameField;
    @FXML
    private VBox activityField;

    public DashboardController(ActivityLogic activityLogic) {
        this.activityLogic = activityLogic;
        currentActivityCounter = 0;

        markerList = new ArrayList<>();

        trackDrawingList = new ArrayList<>();
        ArrayList<Coordinate> coordinatesForCoordinateLine = new ArrayList<>();
        mapViewList = new ArrayList<>();
        for (Activity activity : activityLogic.getAllActivities()) {
            mapViewList.add(new MapView());
            ArrayList<Waypoint> trackCoordinates = activityLogic.getActivityTrackPoints(activity);
            System.out.println(trackCoordinates);
            for (Waypoint waypoint : trackCoordinates) {
                double latitude = waypoint.getLatitude();
                double longitude = waypoint.getLongitude();

                coordinatesForCoordinateLine.add(new Coordinate(latitude, longitude));

                Marker marker = Marker.createProvided(Marker.Provided.RED);
                marker.setPosition(new Coordinate(latitude, longitude));
                marker.setVisible(true);
                markerList.add(marker);
            }

            CoordinateLine coordinateLine = new CoordinateLine(coordinatesForCoordinateLine).setColor(Color.MAGENTA).setVisible(true);
            trackDrawingList.add(coordinateLine);
            coordinatesForCoordinateLine.clear();
        }
        System.out.println(trackDrawingList.size());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayActivities(activityLogic.getAllActivities());
    }

    private void displayActivities(List<Activity> activities) {
        int mapViewListCounter = 0;
        for (Activity activity : activities) {
            Label nameLabel = new Label(activity.getAthlete().getFirstName() + " " + activity.getAthlete().getLastName());
            Label dateAndLocationLabel = new Label(activityLogic.getRealStartTime(activity) + getActivityLocation(activity));
            Label titleLabel = new Label(activityLogic.getRealTitle(activity));
            Label distanceTitleLabel = new Label("Distance ");
            Label timeTitleLabel = new Label("Time ");
            Label elevationOrPaceLabelTitle = new Label();
            Label distanceLabel = new Label(activity.getDistance() + " km");
            Label timeLabel = new Label(activityLogic.getRealDuration(activity));
            Label emptyRow = new Label();
            Label elevationOrPaceLabel = initalizeElevationOrPaceLabel(activity, elevationOrPaceLabelTitle);

            initalizeMapView(mapViewList.get(mapViewListCounter));

            styleActivityLabels(nameLabel, titleLabel);

            HBox metricsTitlesBox = createHBox(distanceTitleLabel, timeTitleLabel, elevationOrPaceLabelTitle);
            HBox metricsBox = createHBox(distanceLabel, timeLabel, elevationOrPaceLabel);

            VBox activityBox = new VBox(nameLabel, dateAndLocationLabel, titleLabel, metricsTitlesBox, metricsBox, emptyRow, mapViewList.get(mapViewListCounter));
            activityBox.setStyle("-fx-background-color: lightgrey;");
            activityBox.setOnMouseClicked(event -> {
                System.out.println("Activity " + activity.getTitle() + " clicked");

            });

            activitiesContainer.getChildren().add(activityBox);
            //activitiesContainer.prefWidthProperty().bind(activitiesContainer.getScene().widthProperty().multiply(0.75));
            mapViewListCounter++;
        }
    }

    private String getActivityLocation(Activity activity) {
        ArrayList<Waypoint> waypoints = activityLogic.getActivityTrackPoints(activity);
        ReverseGeocoder geocoder=new ReverseGeocoder();
        AtomicReference<String> countryName= new AtomicReference<>("");
        geocoder.getCountry(waypoints.get(0).getLatitude(), waypoints.get(0).getLongitude()).ifPresent(country -> {
            countryName.set(country.name());
        });
        return countryName.toString();
    }

    private static Label initalizeElevationOrPaceLabel(Activity activity, Label elevationOrPaceLabelTitle) {
        Label elevationOrPaceLabel = new Label();
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

    private void initalizeMapView(MapView mapView) {
        mapView.initialize();
        mapView.setCenter(coordinateTartu);
        mapView.setZoom(10);
        mapView.setPrefSize(400, 200);

        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized(mapView);
            }
        });

    }

    private void afterMapIsInitialized(MapView mapView) {
        for (Marker marker : markerList) {
            //mapView.addMarker(marker);
        }

        List<Coordinate> coordinateList = trackDrawingList.get(currentActivityCounter).getCoordinateStream().toList();
        //Get first, middle and last coordinates
        Extent extent = Extent.forCoordinates(coordinateList.get(0), coordinateList.get(coordinateList.size() - 1),coordinateList.get(coordinateList.size()/2));
        mapView.setExtent(extent);

        mapView.addCoordinateLine(trackDrawingList.get(currentActivityCounter));
        currentActivityCounter++;
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

    private void styleActivityLabels(Label nameLabel, Label titleLabel) {
        nameLabel.setFont(Font.font(13));
        titleLabel.setFont(Font.font("", FontWeight.BOLD, 16));
    }


}