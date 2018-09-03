/*
 * Copyright (c) 2018.  Pluto Lab Inc.
 */

package pluto.hanoi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class HanoiDBUpdater {

    private long mRunID = System.currentTimeMillis();
    private int mStep;
    private List<HanoiMove> mMoveQueue;

    private int mBatchSize = 100000;

    private static HanoiDBUpdater mInstance = null;

    private HanoiDBUpdater() {

        mStep = 0;
        mMoveQueue = new ArrayList<HanoiMove>();
    }


    public static HanoiDBUpdater getInstance() {

        if(mInstance == null) {
            mInstance = new HanoiDBUpdater();
        }

        return mInstance;
    }

    public void addMove(String fromPole, String toPole) {

        mMoveQueue.add(new HanoiMove(++mStep, fromPole, toPole));

        if(mMoveQueue.size() >= mBatchSize) {
            writeToDB();
        }

    }

    public void writeToDB() {

        String dbURL = "jdbc:oracle:thin:@//192.168.111.81:1521/cdb1pdb";
        String insertSQL = "INSERT INTO PLUTO_HANOI_TOWER (PLUTO_HANOI_TOWER_ID, RUN, STEP, FROM_POLE, TO_POLE, STEP_TIME) VALUES (PLUTO_RUN_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(dbURL, "PLUTO", "welcome1");
            PreparedStatement stmt = conn.prepareStatement(insertSQL);

            for (int i = 0; i < mMoveQueue.size(); i++) {

                HanoiMove move = mMoveQueue.get(i);

                stmt.setLong(1, mRunID);
                stmt.setString(2, Integer.toString(mStep));
                stmt.setString(3, move.getFromPole());
                stmt.setString(4, move.getToPole());

                stmt.addBatch();
            }

            stmt.executeBatch();

        }
        catch(Exception e) {

            System.out.println("Failed write to db");
            e.printStackTrace();

        }
        finally {
            if(conn != null) {
                try {
                    conn.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            mMoveQueue.clear();
        }
    }

    public void close() {
        writeToDB();
    }

}
