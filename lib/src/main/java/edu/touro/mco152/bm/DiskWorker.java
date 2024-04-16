package edu.touro.mco152.bm;

import edu.touro.mco152.bm.Command.Invoker;
import edu.touro.mco152.bm.Command.ReadBenchmark;
import edu.touro.mco152.bm.Command.WriteBenchmark;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;

import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.DiskMark.MarkType.READ;
import static edu.touro.mco152.bm.DiskMark.MarkType.WRITE;

/**
 * Run the disk benchmarking exclusively as a thread. Must cooperate with the user interface to provide and make use of interim and
 * final progress and information, which is also recorded as needed to the persistence store and log.
 * <p>
 * Depends on static values that describe the benchmark to be done having been set in App and Gui classes.
 * The DiskRun class is used to keep track of and persist info about each benchmark at a higher level (a run),
 * while the DiskMark class described each iteration's result, which is displayed by the UI as the benchmark run
 * progresses.
 * <p>
 * This class only knows how to do 'read' or 'write' disk benchmarks, all of which is done in doInBackground(). It is instantiated by the
 * startBenchmark() method.
 * <p>
 * This class declares that its final return (when doInBackground() is finished) is of type Boolean, and declares that intermediate results are communicated to
 * using an instance of the DiskMark class.
 */

public class DiskWorker {

    UIWorker uiWorker;
    Invoker invoker = new Invoker();

    public void setUiWorker(UIWorker uiWorker) {
        this.uiWorker = uiWorker;
    }


    protected boolean startDiskWorker() throws Exception {

        /*
          We 'got here' because: 1: End-user clicked 'Start' on the benchmark UI,
          which triggered the start-benchmark event associated with the App::startBenchmark()
          method.  2: startBenchmark() then instantiated a DiskWorker, and called
          its (super class's) execute() method, causing Swing to eventually
          call this doInBackground() method.
         */
        Logger.getLogger(App.class.getName()).log(Level.INFO, "*** New worker thread started ***");
        msg("Running readTest " + App.readTest + "   writeTest " + App.writeTest);
        msg("num files: " + App.numOfMarks + ", num blks: " + App.numOfBlocks
                + ", blk size (kb): " + App.blockSizeKb + ", blockSequence: " + App.blockSequence);


        Gui.updateLegend();  // init chart legend info

        if (App.autoReset) {
            App.resetTestData();
            Gui.resetTestData();
        }


        //TODO
        /*
          The GUI allows a Write, Read, or both types of BMs to be started. They are done serially.
         */
        if (App.writeTest) {
            WriteBenchmark writeBenchmark = new WriteBenchmark(DiskRun.IOMode.WRITE, blockSequence, uiWorker,
                    numOfMarks, numOfBlocks, (blockSizeKb*KILOBYTE));
            invoker.setCommand(writeBenchmark).invoke();


        }  //TODO
        // try renaming all files to clear catch
        if (App.readTest && App.writeTest && !uiWorker.checkCancel()) {
            JOptionPane.showMessageDialog(Gui.mainFrame,
                    """
                            For valid READ measurements please clear the disk cache by
                            using the included RAMMap.exe or flushmem.exe utilities.
                            Removable drives can be disconnected and reconnected.
                            For system drives use the WRITE and READ operations\s
                            independantly by doing a cold reboot after the WRITE""",
                    "Clear Disk Cache Now", JOptionPane.PLAIN_MESSAGE);
        }

        // Same as above, just for Read operations instead of Writes.
        if (App.readTest) {
            ReadBenchmark readBenchmark = new ReadBenchmark(DiskRun.IOMode.READ, blockSequence, uiWorker,
                    numOfMarks, numOfBlocks, (blockSizeKb*KILOBYTE));
            invoker.setCommand(readBenchmark).invoke();
            if(!readBenchmark.getboolean()){
                return false;
            }

        }
        App.nextMarkNumber += App.numOfMarks;
        return true;
    }

    /**
     * Process a list of 'chunks' that have been processed, ie that our thread has previously
     * published to Swing. For my info, watch Professor Cohen's video -
     * Module_6_RefactorBadBM Swing_DiskWorker_Tutorial.mp4
     *
     * @param markList a list of DiskMark objects reflecting some completed benchmarks
     */
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


}
