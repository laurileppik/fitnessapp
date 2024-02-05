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
    private Athlete athlete;

    public Activity(Sport sport, double distance, Duration duration, int elevation, LocalDateTime startTime, String title, String description,Athlete athlete) {
        this.sport = sport;
        this.distance = distance;
        this.duration = duration;
        this.elevation = elevation;
        this.startTime = startTime;
        this.title = title;
        this.description = description;
        this.athlete=athlete;
    }
    public String getNormalizedDuration(){
        long seconds = duration.getSeconds();
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        return String.format("%02d:%02d:%02d", HH, MM, SS);
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

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
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
