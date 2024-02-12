package fitnesssystem.dao;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.dataobjects.Segment;

import java.util.List;

public interface SystemDAO {
    void beginTransaction();

    void rollbackTransaction();

    void commitTransaction();

    List<Activity> findActivities();
    List<Segment> findSegments();

}
