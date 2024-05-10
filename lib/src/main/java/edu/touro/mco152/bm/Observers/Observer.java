package edu.touro.mco152.bm.Observers;
import edu.touro.mco152.bm.persist.DiskRun;
/**
 * The Observer interface represents an object that observes changes in a subject and reacts accordingly.
 * Observers are typically notified when certain events occur.
 */
public interface Observer {
    /**
     * This method is called by the subject when an event of interest occurs.
     * Implementing classes should define how they react to the event.
     *
     * @param run The object representing the event that occurred.
     */
    void update(DiskRun run);
}
