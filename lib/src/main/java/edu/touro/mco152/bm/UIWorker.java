package edu.touro.mco152.bm;

import java.beans.PropertyChangeListener;

public interface UIWorker<V>  {
    // Method to set the progress
    void updateProgress(int progress);

    // Method for publishing intermediate results
    void displayResults(V results);


//   T doInBackground() throws Exception;
//
//    // Method to perform actions when the background task is done
//    void done();
//
//
//
//    // Method to cancel the background task
//    boolean cancel(boolean bool);
//
//    // Method to check if the background task is cancelled
//    boolean isCancelled();
//
//    // creates a new thread to execute on.
//    void execute();
//
//    void addPropertyChangeListener(PropertyChangeListener listener);


}