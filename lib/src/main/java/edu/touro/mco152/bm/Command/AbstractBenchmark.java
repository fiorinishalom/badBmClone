package edu.touro.mco152.bm.Command;


import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.Observers.Observer;
import edu.touro.mco152.bm.Observers.Subject;
import edu.touro.mco152.bm.UIWorker;
import edu.touro.mco152.bm.Util;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import jakarta.persistence.EntityManager;

import java.util.List;

import static edu.touro.mco152.bm.App.dataDir;
import static edu.touro.mco152.bm.App.msg;

/**
 * An abstract class representing a benchmark operation.
 * Implements {@link Runnable}.
 */
public class AbstractBenchmark implements Runnable, Subject {


    protected List<Observer> observers;
    protected int wUnitsComplete, rUnitsComplete, unitsComplete, unitsTotal;
    protected float percentComplete;
    protected DiskMark wMark, rMark;
    protected byte[] blockArr;
    //add later to constructor DiskRun.blockSequence, and DiskRun.IOMode
    protected DiskRun.BlockSequence blockSequence;
    protected DiskRun.IOMode mode;
    protected UIWorker uiWorker;
    protected int numOfMarks, numOfBlocks, blockSize;
    protected int startFileNum = App.nextMarkNumber; //TODO get rid of APP

    protected DiskRun run;

    /**
     * Constructs a new AbstractBenchmark object.
     *
     * @param mode          the I/O mode for disk operations
     * @param blockSequence the sequence of blocks for disk operations
     * @param uiWorker      the UI worker for updating progress and displaying results
     * @param numOfMarks    the number of marks for the benchmark
     * @param numOfBlocks   the number of blocks per mark for the benchmark
     * @param blockSize     the size of each block in bytes
     */
    public AbstractBenchmark(List<Observer> observers, DiskRun.IOMode mode, DiskRun.BlockSequence blockSequence, UIWorker uiWorker, int numOfMarks, int numOfBlocks, int blockSize) {
        this.observers = observers;
        this.mode = mode;
        this.uiWorker = uiWorker;
        this.numOfMarks = numOfMarks;
        this.numOfBlocks = numOfBlocks;
        this.blockSize = blockSize;
        this.blockSequence = blockSequence;
        this.run = new DiskRun(mode, blockSequence);

 /*
          init local vars that keep track of benchmarks, and a large read/write buffer
         */
        int wUnitsComplete = 0, rUnitsComplete = 0, unitsComplete;
        int wUnitsTotal = (mode == DiskRun.IOMode.WRITE) ? numOfBlocks * numOfMarks : 0;
        int rUnitsTotal = (mode == DiskRun.IOMode.READ) ? numOfBlocks * numOfMarks : 0;
        unitsTotal = wUnitsTotal + rUnitsTotal;
        float percentComplete;

        //    int blockSize = blockSizeKb * KILOBYTE; TODO put into to constructer for blockSize
        blockArr = new byte[blockSize];
        for (int b = 0; b < blockArr.length; b++) {
            if (b % 2 == 0) {
                blockArr[b] = (byte) 0xFF;

                DiskMark wMark, rMark;  // declare vars that will point to objects used to pass progress to UI
            }
        }

    }


    /**
     * Runs the benchmark operation.
     * Overrides the {@link Runnable#run()} method.
     */
    public void run() {
        run.setNumMarks(App.numOfMarks);
        run.setNumBlocks(App.numOfBlocks);
        run.setBlockSize(App.blockSizeKb);
        run.setTxSize(App.targetTxSizeKb());
        run.setDiskInfo(Util.getDiskInfo(dataDir));

        // Tell logger and GUI to display what we know so far about the Run
        msg("disk info: (" + run.getDiskInfo() + ")");

        Gui.chartPanel.getChart().getTitle().setVisible(true);
        Gui.chartPanel.getChart().getTitle().setText(run.getDiskInfo());

    }



    @Override
    public DiskRun getRun() {
        return run;
    }

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void alert() {
        for(Observer obs : observers) {
            obs.update(getRun());
        }
    }
}
