package edu.touro.mco152.bm;

import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * concrete class to run benchMarker not using swing
 */
public class MyUI implements UIWorker {

    Boolean lastStatus = true;  // so far unknown
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

        lastStatus = true;

    }

    /**
     Throws IllegalArgumentException – is value not from 0 to 100
     * @param progress
     */
    @Override
    public void updateProgress(int progress) {
        assertTrue(progress > 0 || progress < 100);
//        if(0 < progress || progress > 100) {
//            throw new IllegalArgumentException("Progress value must be between 0 and 100");
       // }
           // else{
                progressNum = progress;
           // }


    }

    public boolean getLastStatus(){

        return lastStatus;
    }
























    @Override
    public void displayResults(DiskMark results) {
        System.out.println("hello world!!!!!!!!!!!!!");
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
