/**package fitnesssystem.dao;

import fitnesssystem.dataobjects.Activity;
import fitnesssystem.dataobjects.Segment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HibernateSystemDAO implements SystemDAO {
    private final EntityManagerFactory emf;
    private final EntityManager em;
    public HibernateSystemDAO () {
        // if you get ConnectException / JDBCConnectionException then you
        // probably forgot to start the database before starting the application
        emf = Persistence.createEntityManagerFactory ("pos");
        em = emf.createEntityManager ();
    }
    // TODO implement missing methods
    public void close () {
        em.close ();
        emf.close ();
    }
    @Override
    public void beginTransaction () {
        em.getTransaction (). begin ();
    }
    @Override
    public void rollbackTransaction () {
        em.getTransaction (). rollback ();
    }
    @Override
    public void commitTransaction () {
        em.getTransaction (). commit ();
    }

    @Override
    public List<Activity> findActivities() {
        return null;
    }

    @Override
    public List<Segment> findSegments() {
        return null;
    }
}**/