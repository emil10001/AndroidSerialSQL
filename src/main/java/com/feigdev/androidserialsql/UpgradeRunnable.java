package com.feigdev.androidserialsql;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ejohn on 9/2/13.
 */
public abstract class UpgradeRunnable implements Runnable {
    private SQLiteDatabase db;

    public final void setDB (SQLiteDatabase db){
        this.db = db;
    }

}
