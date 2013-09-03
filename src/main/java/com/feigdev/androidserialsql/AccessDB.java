package com.feigdev.androidserialsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
    private final SQLiteDatabase dbWrite, dbRead;
    ExecutorService executorService = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, writerQueue, new ThreadPoolExecutor.CallerRunsPolicy());

    public AccessDB(Context context, DefineDB myDB) {
        dbHelper = new GenericDBHelper(context, myDB);
        dbWrite = dbHelper.getWritableDatabase();
        dbRead = dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getReadableDB() {
        return dbRead;
    }

    public void addWriteTask(WriterTask task){
        executorService.execute(task);
    }

    private SQLiteDatabase getWritableDB() {
        return dbWrite;
    }

    public abstract class WriterTask implements Runnable {
        SQLiteDatabase db;

        public WriterTask() {
            this.db = getWritableDB();
        }

    }


}
