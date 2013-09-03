package com.feigdev.androidserialsql;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ejohn on 9/2/13.
 */
public abstract class WriterTask implements Runnable {
    public Runnable callback;
    private SQLiteDatabase db;

    public WriterTask(String dbName, Runnable callback) {
        this.db = AccessDB.databases.get(dbName).getWriter();
        this.callback = callback;
    }

    public SQLiteDatabase getDB() {
        return this.db;
    }
}
