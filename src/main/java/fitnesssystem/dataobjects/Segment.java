package fitnesssystem.dataobjects;

import java.time.LocalTime;

public class Segment {
    private boolean star;
    private Sport sport;
    private String name;
    private double distance;
    private LocalTime time;
    private LocalTime myBest;
    public Segment(boolean star, Sport sport, String name, double distance, LocalTime time, LocalTime myBest) {
        this.star = star;
        this.sport = sport;
        this.name = name;
        this.distance = distance;
        this.time = time;
        this.myBest = myBest;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getMyBest() {
        return myBest;
    }

    public void setMyBest(LocalTime myBest) {
        this.myBest = myBest;
    }
}
