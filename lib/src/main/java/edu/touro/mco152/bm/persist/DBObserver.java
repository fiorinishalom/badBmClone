package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.Observers.Observer;
import jakarta.persistence.EntityManager;
/**
 * The DBObserver class implements the Observer interface to observe disk runs and persist them to a database.
 */
public class DBObserver implements Observer {

    /**
     * This method is called by the subject (benchmarking system) when a disk run is completed.
     * It persists the completed disk run to a database using an entity manager.
     *
     * @param run The completed disk run to be persisted to the database.
     */
    @Override
    public void update(DiskRun run) {
        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();

    }
}
