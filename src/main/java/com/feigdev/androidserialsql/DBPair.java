package com.feigdev.androidserialsql;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ejohn on 9/2/13.
 */
public class DBPair {
    private final SQLiteDatabase dbRead, dbWrite;

    DBPair(SQLiteDatabase read, SQLiteDatabase write){
        dbRead = read;
        dbWrite = write;
    }

    SQLiteDatabase getReader(){
        return dbRead;
    }

    SQLiteDatabase getWriter(){
        return dbWrite;
    }
}
