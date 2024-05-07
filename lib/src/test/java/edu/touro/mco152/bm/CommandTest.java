package edu.touro.mco152.bm;

import edu.touro.mco152.bm.Command.Invoker;
import edu.touro.mco152.bm.Command.ReadBenchmark;
import edu.touro.mco152.bm.Command.WriteBenchmark;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Properties;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.persist.DiskRun.BlockSequence.SEQUENTIAL;

public class CommandTest {
    UIWorker uiWorker;
    Invoker invoker = new Invoker();
    @BeforeEach
    void setUp() {
        setupDefaultAsPerProperties();
        uiWorker = new MyUI();
    }

    /**
     *  Tests that progress is being updated correctly or at all when ReadBenchmark is called.
     */
    @Test
    void read(){
        ReadBenchmark readBenchmark = new ReadBenchmark(DiskRun.IOMode.READ, SEQUENTIAL, uiWorker,
                25, 128, (2048*KILOBYTE));
        invoker.setCommand(readBenchmark).invoke();

    }

    /**
     *  Tests that progress is being updated correctly or at all when WriteBenchmark is called.
     */
    @Test
    void write(){
        WriteBenchmark writeBenchmark = new WriteBenchmark(DiskRun.IOMode.READ, SEQUENTIAL, uiWorker,
                25, 128, (2048*KILOBYTE));
        invoker.setCommand(writeBenchmark).invoke();

    }





    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    public static void setupDefaultAsPerProperties() {
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();

        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference

        // may be null when tests not run in original proj dir, so use a default area
        if (App.locationDir == null) {
            App.locationDir = new File(System.getProperty("user.home"));
        }

        App.dataDir = new File(App.locationDir.getAbsolutePath() + File.separator + App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        } else {
            App.dataDir.mkdirs(); // create data dir if not already present
        }
    }
}
