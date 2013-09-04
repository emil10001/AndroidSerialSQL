package com.feigdev.androidserialsql;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ejohn on 9/2/13.
 */
public abstract class WriterTask implements Runnable {
    public SQLiteDatabase db;
    public Runnable callback;

    public WriterTask(String dbName, Runnable callback) {
        this.db = AccessDB.databases.get(dbName).getWriter();
        this.callback = callback;
    }
}
