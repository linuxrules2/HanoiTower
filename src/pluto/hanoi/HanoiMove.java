/*
 * Copyright (c) 2018.  Pluto Lab Inc.
 */

package pluto.hanoi;

import java.sql.Timestamp;

public class HanoiMove {

    private int mStep;
    private String mFromPole;
    private String mToPole;
    private Timestamp mTime;

    public HanoiMove(int step, String fromPole, String toPole) {

        this.mStep = step;
        this.mFromPole = fromPole;
        this.mToPole = toPole;
        this.mTime = new Timestamp(System.currentTimeMillis());
    }

    public int getStep() {
        return mStep;
    }

    public String getFromPole() {
        return mFromPole;
    }

    public String getToPole() {
        return mToPole;
    }

    public Timestamp getTime() {
        return mTime;
    }
}
