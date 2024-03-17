package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;

import javax.swing.*;
import java.util.List;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.dataDir;
/**
 * Manages the Swing user interface for the disk benchmarking application.
 * This class provides functionality for displaying progress and information to the user,
 * as well as interacting with the benchmarking process.
 * <p>
 * This class cooperates with the benchmarking logic to provide and update the user interface,
 * displaying interim and final progress and information to the user.
 * <p>
 * The GUI components are managed and updated by this class in coordination with the benchmarking logic.
 * It encapsulates Swing components and their behavior, ensuring proper interaction between the
 * user interface and the backend benchmarking process.
 */
public class SwingUI extends SwingWorker<Boolean, DiskMark>  implements UIWorker {
    // Record any success or failure status returned from SwingWorker (might be us or super)
    Boolean lastStatus = null;  // so far unknown



        @Override
        protected Boolean doInBackground() throws Exception {
            App.worker.startDiskWorker(); //Prof said this is alright, but it is a dependency

            return true;
        }

    @Override
    public void start() {
        execute();
    }

    @Override
    public void updateProgress(int progress) {
        setProgress(progress);
    }

    @Override
    public void displayResults(DiskMark results) {
            publish(results);
    }

    @Override
    public void makeCanceled(boolean bool) {
        cancel(bool);

    }

    @Override
    public boolean checkCancel() {
        return isCancelled();
    }

    //TODO
    protected void process(List<DiskMark> markList) {
        markList.stream().forEach((dm) -> {
            if (dm.type == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }


    /**
     * Called when doInBackGround method of SwingWorker successfully or unsuccessfully finishes or is aborted.
     * This method is called by Swing and has access to the get method within it's scope, which returns the computed
     * result of the doInBackground method.
     */
    @Override
    protected void done() {
        // Obtain final status, might from doInBackground ret value, or SwingWorker error
        try {
            lastStatus = super.get();   // record for future access  ME: this get is getting the value from swingWorker itself so we can get asccess.
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).warning("Problem obtaining final status: " + e.getMessage());
        }

        if (App.autoRemoveData) {
            Util.deleteDirectory(dataDir);
        }
        App.state = App.State.IDLE_STATE;
        Gui.mainFrame.adjustSensitivity();
    }

    public Boolean getLastStatus() {
        return lastStatus;
    }





}
