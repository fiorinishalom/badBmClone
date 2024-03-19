package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.Util;
import edu.touro.mco152.bm.ui.Gui;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static edu.touro.mco152.bm.App.dataDir;
import static org.junit.jupiter.api.Assertions.*;

class TestDiskRun {
    List<DiskRun> diskRuns;
    @BeforeEach
    void setUp(){
        DiskRun.deleteAll();
    }

    /**
     * Checking a boundary case in DirkRun to see what happens when deleteAll is used on an empty database.
     */
    @Test
    public void testDeleteAllWithEmptyDatabase() {
        DiskRun.deleteAll();
    }
    /**
     * Checking a boundary case in DirkRun to see what happens when findAll is used on an empty database.
     */
    @Test
    public void testFindAllWithEmptyDatabase(){

        diskRuns = DiskRun.findAll();
        Assertions.assertEquals(0, diskRuns.size());
    }

    /**
     * error will expect only 4 rows of database even though there are 5
     */
    @Test
    void findAll(){
        populateDatabase(5);
        diskRuns = DiskRun.findAll();
        assertEquals(4, diskRuns.size());
    }



    @Test
    void deleteAll(){
        populateDatabase(5);
        diskRuns = DiskRun.findAll();
        assertEquals(5, diskRuns.size());
        DiskRun.deleteAll();

        diskRuns = DiskRun.findAll();
        assertEquals(0, diskRuns.size());

    }


    /**
     * This is a performance test checking to see how fast findAll could get data form database.
     */
    @Test
    void findAllInRun(){
        populateDatabase(100);
        diskRuns = DiskRun.findAll();

        long startTime = System.currentTimeMillis();

        Assertions.assertEquals(100, diskRuns.size());

        long duration = System.currentTimeMillis() - startTime;

        assertTrue(duration < 50);

    }

    /**
     * helper method for findAllInRun()
     */
    void populateDatabase(int num) {
        for (int i = 0; i < num; i++) {

            DiskRun run = new DiskRun(DiskRun.IOMode.WRITE, App.blockSequence);
            run.setNumMarks(App.numOfMarks);
            run.setNumBlocks(App.numOfBlocks);
            run.setBlockSize(App.blockSizeKb);
            run.setTxSize(App.targetTxSizeKb());
            run.setDiskInfo("os: Windows 11");
        /*
              Persist info about the Write BM Run (e.g. into Derby Database) and add it to a GUI panel
             */
            EntityManager em = EM.getEntityManager();
            em.getTransaction().begin();
            em.persist(run);
            em.getTransaction().commit();
        }

    }
}