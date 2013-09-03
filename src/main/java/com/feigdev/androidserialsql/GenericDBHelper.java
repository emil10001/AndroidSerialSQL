package com.feigdev.androidserialsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Set;

/**
 * Created by ejohn on 9/2/13.
 */
public class GenericDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "GenericDBHelper";
    private final DefineDB myDB;

    public GenericDBHelper(Context context, DefineDB myDB) {
        super(context, myDB.getDbName(), null, myDB.getVersion());
        this.myDB = myDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Set<String> tables = myDB.getTables();

        db.beginTransaction();
        for (String table : tables) {
            Log.d(TAG, "exec " + table + ": " + myDB.getTableDefenition(table));
            db.execSQL(myDB.getTableDefenition(table));
        }
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        while (oldVersion < newVersion) {
            oldVersion++;
            UpgradeRunnable r = myDB.getVersionUpgrade(oldVersion);

            if (null == r)
                continue;

            r.setDB(db);
            r.run();
        }
    }
}
