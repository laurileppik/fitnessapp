package fitnesssystem.logic;

import fitnesssystem.dao.SystemDAO;
import fitnesssystem.dataobjects.Activity;

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
}
