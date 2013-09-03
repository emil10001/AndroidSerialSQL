package com.feigdev.androidserialsql;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ejohn on 9/2/13.
 */
public abstract class WriterTask implements Runnable {
    private SQLiteDatabase db;

    public WriterTask(String dbName) {
        this.db = AccessDB.databases.get(dbName).getWriter();
    }

    public SQLiteDatabase getDB() {
        return this.db;
    }
}
