package edu.touro.mco152.bm.Observers;

import edu.touro.mco152.bm.persist.DiskRun;
/**
 * The Subject interface represents an object that is observed by one or more observers.
 * Subjects notify their observers when certain events occur.
 */
public interface Subject {
    /**
     * Retrieves the current state of the subject, typically represented by a DiskRun object.
     *
     * @return The current state of the subject.
     */
    DiskRun getRun();
    /**
     * Adds an observer to the list of observers interested in changes to the subject.
     *
     * @param obs The observer to be added.
     */
    void addObserver(Observer obs);
    /**
     * Removes an observer from the list of observers.
     *
     * @param obs The observer to be removed.
     */
    void removeObserver(Observer obs);

    /**
     * Notifies all observers that an event of interest has occurred.
     */

    void alert();
}
