package fitnesssystem.logic;

import fitnesssystem.dao.SystemDAO;
import fitnesssystem.dataobjects.Activity;
import org.alternativevision.gpx.beans.GPX;
import org.alternativevision.gpx.beans.Track;
import org.alternativevision.gpx.beans.Waypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        List<Activity> activities=dao.findActivities();
        return dao.findActivities();
    }
    public String numberOfActivities() {
        return dao.findActivities().size() + " Activities";
    }

    public List<String> getAllSportsNames() {
        List<String> allSports=new ArrayList<>();
        for (Activity activity: dao.findActivities()) {
            allSports.add(activity.getSport().getName());
        }
        return allSports;
    }

    public List<Activity> getActivitiesContainingWord(String containedWord) {
        List<Activity> activitiesContainingWord=new ArrayList<>();
        for (Activity activity:dao.findActivities()) {
            if (activity.getTitle().contains(containedWord))
                activitiesContainingWord.add(activity);
        }
        return activitiesContainingWord;
    }

    public List<Activity> getActivitiesWithSportsFilterUsed(String newValue) {
        List<Activity> activitiesWithSportsFilterUsed=new ArrayList<>();
        for (Activity activity:dao.findActivities()) {
            if (activity.getSport().getName().equals(newValue))
                activitiesWithSportsFilterUsed.add(activity);
        }
        return activitiesWithSportsFilterUsed;
    }

    public ArrayList<Waypoint> getActivityTrackPoints(Activity activity) {
            GPX gpx= activity.getActivityTrack().getGpx();
            HashSet<Track> tracks = gpx.getTracks();
            if (tracks == null) {
                log.info("No routes found");
            } else if (tracks.isEmpty()) {
                log.info("Routes list is empty.");
            } else {
                log.info("Routes: ");
                for (Track track : tracks) {
                    return track.getTrackPoints();
                }
        }
        return null;
    }

}
