package model;

import java.text.NumberFormat;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Purpose of this app is to make a stopwatch program with start/pause and reset functionality
 *
 * @author Paul Harvey (harv0116@algonquinlive.com)
 */
public class StopwatchModel extends Observable {

    private static final NumberFormat NF;

    private int mHours;
    private boolean mIsRunning;
    private int mMinutes;
    private int mSeconds;
    private int mTenthOfASecond;
    private TimerTask mTimerTask;
    private Timer mTimer;

    //initialize all class variables
    static {
        NF = NumberFormat.getInstance();
        NF.setMaximumIntegerDigits(2);
        NF.setMinimumIntegerDigits(2);
    }

    public StopwatchModel() {
        this(0, 0, 0, 0);
    }

    public int getmHours() {
        return mHours;
    }

    public int getmMinutes() {
        return mMinutes;
    }

    public int getmSeconds() {
        return mSeconds;
    }

    public int getmTenthOfASecond() {
        return mTenthOfASecond;
    }

    public StopwatchModel(int hours, int minutes, int seconds, int tenthOfASecond) {

        super();
        this.mHours = hours;
        this.mMinutes = minutes;
        this.mSeconds = seconds;
        this.mTenthOfASecond = tenthOfASecond;


        mIsRunning = false;

        mTimer = new Timer();
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void reset() {

        mHours = mMinutes = mSeconds = mTenthOfASecond = 0;

        this.updateObservers();
    }

    public void start() {
        if (this.isRunning() == false) {
            mTimerTask = new StopwatchTask();
            mTimer.scheduleAtFixedRate(mTimerTask, 0L, 100L);
            mIsRunning = true;
        }

        this.updateObservers();
    }

    public void stop() {
        if (this.isRunning() == true) {
            mTimerTask.cancel();
            mIsRunning = false;
        }

        this.updateObservers();
    }

    @Override
    public String toString() {
        return (NF.format(mHours)
                + ":" + NF.format(mMinutes)
                + ":" + NF.format(mSeconds)
                + ":" + mTenthOfASecond);
    }

    private void updateObservers() {

        this.setChanged();
        this.notifyObservers();
    }

    private class StopwatchTask extends TimerTask {

        @Override
        public void run() {
            mTenthOfASecond++;

            if (mTenthOfASecond == 10) {
                mTenthOfASecond = 0;
                mSeconds++;
                if (mSeconds >= 60) {
                    mSeconds = 0;
                    mMinutes++;
                    if (mMinutes >= 60) {
                        mMinutes = 0;
                        mHours++;
                    }
                }
            }

            updateObservers();

        }

        ;
    }


}
