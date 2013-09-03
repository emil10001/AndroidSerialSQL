package com.feigdev.androidserialsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ejohn on 9/2/13.
 */
public class AccessDB {
    private static final LinkedBlockingQueue<Runnable> writerQueue = new LinkedBlockingQueue<Runnable>();
    private final GenericDBHelper dbHelper;
    private final String dbName;

    static final ConcurrentHashMap<String, DBPair> databases = new ConcurrentHashMap<String, DBPair>();
    private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, writerQueue, new ThreadPoolExecutor.CallerRunsPolicy());

    public AccessDB(Context context, DefineDB myDB) {
        dbHelper = new GenericDBHelper(context, myDB);
        dbName = myDB.getDbName();
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        databases.put(dbName, new DBPair(dbRead, dbWrite));
    }

    public SQLiteDatabase getReadableDB() {
        return databases.get(dbName).getReader();
    }

    public static SQLiteDatabase getReadableDB(String dbName){
        return databases.get(dbName).getReader();
    }

    public void addWriteTask(WriterTask task) {
        executorService.execute(task);
    }

}
