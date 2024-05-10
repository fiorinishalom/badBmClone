package edu.touro.mco152.bm.Command;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.Observers.Observer;
import edu.touro.mco152.bm.UIWorker;
import edu.touro.mco152.bm.Util;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.DiskMark.MarkType.READ;

/**
 * A benchmark class for reading operations.
 * Extends {@link AbstractBenchmark} and implements {@link Command}.
 */
public class ReadBenchmark extends AbstractBenchmark implements Command
{
    boolean bool = true;

    /**
     * Constructs a new ReadBenchmark object.
     *
     * @param observers     the list of observers
     * @param mode          the I/O mode for disk operations
     * @param blockSequence the sequence of blocks for disk operations
     * @param uiWorker      the UI worker for updating progress and displaying results
     * @param numOfMarks    the number of marks for the benchmark
     * @param numOfBlocks   the number of blocks per mark for the benchmark
     * @param blockSize     the size of each block in bytes
     */
    public ReadBenchmark(List<Observer> observers, DiskRun.IOMode mode, DiskRun.BlockSequence blockSequence, UIWorker uiWorker, int numOfMarks, int numOfBlocks, int blockSize) {
        super(observers, mode, blockSequence, uiWorker, numOfMarks, numOfBlocks, blockSize);
    }


    /**
     * Gets the boolean flag indicating the success or failure of the benchmark.
     *
     * @return {@code true} if the benchmark succeeded, {@code false} otherwise
     */
    public boolean getboolean(){
        return bool;
    }

    /**
     * Runs the read benchmark.
     * Overrides the {@link AbstractBenchmark#run()} method.
     */
    @Override
    public void run(){
        super.run();
        for (int m = startFileNum; m < startFileNum + App.numOfMarks && !uiWorker.checkCancel(); m++) {

            if (App.multiFile) {
                testFile = new File(dataDir.getAbsolutePath()
                        + File.separator + "testdata" + m + ".jdm");
            }
            rMark = new DiskMark(READ);  // starting to keep track of a new benchmark
            rMark.setMarkNum(m);
            long startTime = System.nanoTime();
            long totalBytesReadInMark = 0;

            try {
                try (RandomAccessFile rAccFile = new RandomAccessFile(testFile, "r")) {
                    for (int b = 0; b < numOfBlocks; b++) {
                        if (App.blockSequence == DiskRun.BlockSequence.RANDOM) {
                            int rLoc = Util.randInt(0, numOfBlocks - 1);
                            rAccFile.seek((long) rLoc * blockSize);
                        } else {
                            rAccFile.seek((long) b * blockSize);
                        }
                        rAccFile.readFully(blockArr, 0, blockSize);
                        totalBytesReadInMark += blockSize;
                        rUnitsComplete++;
                        unitsComplete = rUnitsComplete + wUnitsComplete;
                        percentComplete = (float) unitsComplete / (float) unitsTotal * 100f;
                        uiWorker.updateProgress((int) percentComplete);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                String emsg = "May not have done Write Benchmarks, so no data available to read." +
                        ex.getMessage();
                JOptionPane.showMessageDialog(Gui.mainFrame, emsg, "Unable to READ", JOptionPane.ERROR_MESSAGE);
                msg(emsg);
                bool = false;
            }
            long endTime = System.nanoTime();
            long elapsedTimeNs = endTime - startTime;
            double sec = (double) elapsedTimeNs / (double) 1000000000;
            double mbRead = (double) totalBytesReadInMark / (double) MEGABYTE;
            rMark.setBwMbSec(mbRead / sec);
            msg("m:" + m + " READ IO is " + rMark.getBwMbSec() + " MB/s    "
                    + "(MBread " + mbRead + " in " + sec + " sec)");
            App.updateMetrics(rMark);
            uiWorker.displayResults(rMark);

            run.setRunMax(rMark.getCumMax());
            run.setRunMin(rMark.getCumMin());
            run.setRunAvg(rMark.getCumAvg());
            run.setEndTime(new Date());
        }

            super.alert();

    }

}
