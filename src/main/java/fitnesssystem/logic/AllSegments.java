package fitnesssystem.logic;

import fitnesssystem.dao.SystemDAO;
import fitnesssystem.dataobjects.Segment;

import java.util.List;

public class AllSegments {
    private final SystemDAO dao;

    public AllSegments(SystemDAO dao) {
        this.dao = dao;
    }

    public List<Segment> getAllSegments() {
        return dao.findSegments();
    }
}
