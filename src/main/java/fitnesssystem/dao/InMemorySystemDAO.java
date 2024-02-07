package fitnesssystem.dao;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.dataobjects.Athlete;
import fitnesssystem.dataobjects.Segment;
import fitnesssystem.dataobjects.Sport;
import org.alternativevision.gpx.GPXParser;


import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InMemorySystemDAO implements SystemDAO {
    private final List<Activity> activityList;
    private final List<Segment> segmentList;

    public InMemorySystemDAO() {
        List<Activity> activities = new ArrayList<Activity>();
        List<Segment> segments = new ArrayList<Segment>();

        try {

            GPXParser p = new GPXParser();
            //FileInputStream in = new FileInputStream("/example.gpx");
            //GPX gpx = p.parseGPX(in);
        } catch (Exception ignored) {

        }



        activities.add(new Activity(new Sport("Run"), 10.45, Duration.ofHours(1).plusMinutes(30).plusSeconds(45), 15, LocalDateTime.now(), "Afternoon Run", "New PB",new Athlete("Peeter","Peets","Estonia","Tartu"),"Tartu"));
        activities.add(new Activity(new Sport("Bike"), 40.21, Duration.ofHours(2).plusMinutes(12).plusSeconds(30), 125, LocalDateTime.now(), "Afternoon Bike", "Awful wind today",new Athlete("Mike","John","England","London"),"London"));
        String timeString = "01:00:00";
        LocalTime timeToBeat = LocalTime.parse(timeString);
        String timeBestString = "01:05:00";
        LocalTime myBest = LocalTime.parse(timeBestString);
        segments.add(new Segment(true,new Sport("Run"), "Suur Munamagi", 4.2,timeToBeat,myBest));
        this.activityList = activities;
        this.segmentList=segments;
    }

    @Override
    public List<Activity> findActivities() {
        return activityList;
    }

    @Override
    public List<Segment> findSegments() {
        return segmentList;
    }

}
