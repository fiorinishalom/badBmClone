package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Properties;

/**
 * concrete class to run benchMarker not using swing
 */
public class MyWorker implements UIWorker {

    Boolean lastStatus = false;  // so far unknown
    int progressNum = 0;
    boolean val;
    @Override
    public void start() {
        try {
           doInBackground();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doInBackground() throws Exception {
        val = App.worker.startDiskWorker(); //Prof said this is alright, but it is a dependency
        lastStatus = true;

    }

    /**
     Throws IllegalArgumentException – is value not from 0 to 100
     * @param progress
     */
    @Override
    public void updateProgress(int progress) {
        if(0 > progress || progress > 100) {
            throw new IllegalArgumentException("Progress value must be between 0 and 100");
        }
            else{ progressNum = progress;}


    }

    public boolean getLastStatus(){

        return lastStatus;
    }
























    @Override
    public void displayResults(DiskMark results) {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }

    @Override
    public void makeCanceled(boolean bool) {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }

    @Override
    public boolean checkCancel() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }
}
