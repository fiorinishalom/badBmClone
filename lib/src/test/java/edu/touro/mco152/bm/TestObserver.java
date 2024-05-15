package edu.touro.mco152.bm;

import edu.touro.mco152.bm.Observers.Observer;
import edu.touro.mco152.bm.persist.DiskRun;

public class TestObserver implements Observer {
    boolean bool = false;
    @Override
    public void update(DiskRun run) {
        bool = true;
    }

    public boolean getBool(){
        return bool;
    }

}
