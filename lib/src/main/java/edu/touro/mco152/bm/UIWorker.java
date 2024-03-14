package edu.touro.mco152.bm;

import java.beans.PropertyChangeListener;

public interface UIWorker  {
    // creates a new thread to execute on.
    void start();

    // Method to set the progress
    void updateProgress(int progress);

    // Method for publishing intermediate results
    void displayResults(DiskMark results);

    // Method to cancel the background task
    void makeCanceled(boolean bool);


    // Method to check if the background task is cancelled
    boolean checkCancel();

    void addPropertyChangeListener(PropertyChangeListener listener);




}