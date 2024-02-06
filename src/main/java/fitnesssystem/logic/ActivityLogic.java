package fitnesssystem.logic;

import fitnesssystem.dao.SystemDAO;
import fitnesssystem.dataobjects.Activity;
import fitnesssystem.dataobjects.Sport;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogic {
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


}
