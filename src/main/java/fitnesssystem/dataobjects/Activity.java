package fitnesssystem.dataobjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class Activity {
    private Sport sport;
    private double distance;
    private Duration duration;
    private int elevation;
    private LocalDateTime startTime;
    private String title;
    private String description;

    public Activity(Sport sport, double distance, Duration duration, int elevation, LocalDateTime startTime, String title, String description) {
        this.sport = sport;
        this.distance = distance;
        this.duration = duration;
        this.elevation = elevation;
        this.startTime = startTime;
        this.title = title;
        this.description = description;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "sport=" + sport +
                ", distance=" + distance +
                ", duration=" + duration +
                ", elevation=" + elevation +
                ", startTime=" + startTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
