package edu.touro.mco152.bm.externalsys;

import edu.touro.mco152.bm.Observers.Observer;
import edu.touro.mco152.bm.persist.DiskRun;
/**
 * The SlackObserver class implements the Observer interface to monitor disk runs and send Slack messages
 * when certain criteria are met.
 */
public class SlackObserver implements Observer {

    /**
     * This method is called by the subject (benchmarking system) when a disk run is completed.
     * It checks if the maximum runtime of the disk run exceeds 3% of the average runtime and if the
     * operation is a read operation. If both conditions are met, a warning message is sent to the
     * designated Slack channel.
     *
     * @param run The completed disk run to be checked against the criteria.
     */
    @Override
    public void update(DiskRun run) {
        if ( run.getIoMode() == DiskRun.IOMode.READ && run.getRunMax() > (run.getRunAvg()) * 1.03) {
            SlackManager slackManager = new SlackManager("BadBM");
            slackManager.postMsg2OurChannel("Warning!!! Max runtime had exceeded over 3% of average runtime.");
        }
    }
}
