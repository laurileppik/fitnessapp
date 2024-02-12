package fitnesssystem.dao;

import fitnesssystem.dataobjects.*;
import org.alternativevision.gpx.GPXParser;
import org.alternativevision.gpx.beans.*;


import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InMemorySystemDAO implements SystemDAO {
    private final List<Activity> activityList;
    private final List<Segment> segmentList;

    public InMemorySystemDAO() {
        List<Activity> activities = new ArrayList<Activity>();
        List<Segment> segments = new ArrayList<Segment>();

        GPXParser parser = new GPXParser();
        GPX gpx=parseExampleGPX(parser,"/example.gpx");
        GPX gpx2=parseExampleGPX(parser,"/example2spain.gpx");

        ActivityTrack track=new ActivityTrack(gpx);
        ActivityTrack track2=new ActivityTrack(gpx2);

        activities.add(new Activity(new Sport("Run"), 10.45, Duration.ofHours(1).plusMinutes(30).plusSeconds(45), 15, LocalDateTime.now(), "Afternoon Run", "New PB",new Athlete("Peeter","Peets","Estonia","Tartu"),"Tartu",track));
        activities.add(new Activity(new Sport("Run"), 12.21, Duration.ofHours(2).plusMinutes(12).plusSeconds(30), 125, LocalDateTime.now(), "Afternoon Bike", "Awful wind today",new Athlete("Mike","John","England","London"),"London",track2));
        String timeString = "01:00:00";
        LocalTime timeToBeat = LocalTime.parse(timeString);
        String timeBestString = "01:05:00";
        LocalTime myBest = LocalTime.parse(timeBestString);
        segments.add(new Segment(true,new Sport("Run"), "Suur Munamagi", 4.2,timeToBeat,myBest));
        this.activityList = activities;
        this.segmentList=segments;
    }

    private GPX parseExampleGPX(GPXParser p,String url) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(url);
            return p.parseGPX(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void rollbackTransaction() {

    }

    @Override
    public void commitTransaction() {

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
