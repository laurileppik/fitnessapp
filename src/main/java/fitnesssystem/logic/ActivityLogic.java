package fitnesssystem.logic;

import fitnesssystem.dao.SystemDAO;
import fitnesssystem.dataobjects.Activity;
import org.alternativevision.gpx.beans.GPX;
import org.alternativevision.gpx.beans.Track;
import org.alternativevision.gpx.beans.Waypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ActivityLogic {
    private static final Logger log = LogManager.getLogger(ActivityLogic.class);
    private final SystemDAO dao;

    public ActivityLogic(SystemDAO dao) {
        this.dao = dao;
    }

    public List<Activity> getAllActivities() {
        List<Activity> activities = dao.findActivities();
        return dao.findActivities();
    }

    public String numberOfActivities() {
        return dao.findActivities().size() + " Activities";
    }

    public List<String> getAllSportsNames() {
        List<String> allSports = new ArrayList<>();
        for (Activity activity : dao.findActivities()) {
            allSports.add(activity.getSport().getName());
        }
        return allSports;
    }

    public List<Activity> getActivitiesContainingWord(String containedWord) {
        List<Activity> activitiesContainingWord = new ArrayList<>();
        for (Activity activity : dao.findActivities()) {
            if (activity.getTitle().contains(containedWord))
                activitiesContainingWord.add(activity);
        }
        return activitiesContainingWord;
    }

    public List<Activity> getActivitiesWithSportsFilterUsed(String newValue) {
        List<Activity> activitiesWithSportsFilterUsed = new ArrayList<>();
        for (Activity activity : dao.findActivities()) {
            if (activity.getSport().getName().equals(newValue))
                activitiesWithSportsFilterUsed.add(activity);
        }
        return activitiesWithSportsFilterUsed;
    }

    public ArrayList<Waypoint> getActivityTrackPoints(Activity activity) {
        GPX gpx = activity.getActivityTrack().getGpx();
        HashSet<Track> tracks = gpx.getTracks();
        if (tracks == null) {
            log.info("No routes found");
        } else if (tracks.isEmpty()) {
            log.info("Routes list is empty.");
        } else {
            for (Track track : tracks) {
                return track.getTrackPoints();
            }
        }
        return null;
    }

    public String getRealStartTime(Activity activity) {
        GPX gpx = activity.getActivityTrack().getGpx();
        HashSet<Track> tracks = gpx.getTracks();
        if (tracks == null) {
            log.info("No routes found");
        } else if (tracks.isEmpty()) {
            log.info("Routes list is empty.");
        } else {
            for (Track track : tracks) {

                String dateString = String.valueOf(track.getTrackPoints().get(0).getTime());

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss z yyyy");
                LocalDateTime dateTime = LocalDateTime.parse(dateString, inputFormatter);

                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
                String formattedDate = dateTime.format(outputFormatter);
                String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                activity.setStartTime(dateTime);
                return formattedDate + " at " + formattedTime + " * ";
            }
        }
        return null;
    }

    public String getRealDuration(Activity activity) {
        GPX gpx = activity.getActivityTrack().getGpx();
        HashSet<Track> tracks = gpx.getTracks();
        if (tracks == null) {
            log.info("No routes found");
        } else if (tracks.isEmpty()) {
            log.info("Routes list is empty.");
        } else {
            for (Track track : tracks) {
                ArrayList<Waypoint> trackPoints=track.getTrackPoints();
                long totalSeconds=(track.getTrackPoints().get(trackPoints.size()-1).getTime().getTime()-track.getTrackPoints().get(0).getTime().getTime())/1000;
                long hours = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;

                String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                if (formattedTime.startsWith("0")) {
                    activity.setDuration(Duration.ofSeconds(totalSeconds));
                    //activity.setDuration(Duration.parse(formattedTime));
                    return formattedTime.substring(1);
                }
                else  {
                    activity.setDuration(Duration.ofSeconds(totalSeconds));
                    return formattedTime;
                }
            }
        }
        return null;
    }
    public String getRealTitle(Activity activity) {
        GPX gpx = activity.getActivityTrack().getGpx();
        HashSet<Track> tracks = gpx.getTracks();
        if (tracks == null) {
            log.info("No routes found");
        } else if (tracks.isEmpty()) {
            log.info("Routes list is empty.");
        } else {
            for (Track track : tracks) {
                activity.setTitle(track.getName());
                return track.getName();
            }
        }
        return null;
    }
}


