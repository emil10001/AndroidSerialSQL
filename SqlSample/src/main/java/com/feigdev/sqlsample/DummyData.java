package com.feigdev.sqlsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.feigdev.androidserialsql.AccessDB;
import com.feigdev.androidserialsql.DefineDB;
import com.feigdev.androidserialsql.WriterTask;

/**
 * Created by ejohn on 9/2/13.
 */
public class DummyData {
    private static final String TAG = "DummyData";
    private static final String DB_NAME = "DummyData";
    private static final int VERSION = 1;

    private static final String ITEMS = "items";
    private static final String ITEMS_TABLE_DEFINITION = "create table "
            + ITEMS + "( _id integer primary key autoincrement, item text);";
    private final DefineDB myDB = new DefineDB(DB_NAME, VERSION);

    // uncomment to test upgrade
//    private static final int VERSION = 2;
//    private static final String TABLE_TWO = "two";
//    private static final String TABLE_TWO_DEFINITION = "create table "
//            + TABLE_TWO + "( _id integer primary key autoincrement, item text);";

    public DummyData(Context c, Runnable callback) {
        myDB.setTableDefenition(ITEMS, ITEMS_TABLE_DEFINITION);

        // uncomment to test upgrade
//        myDB.setTableDefenition(TABLE_TWO, TABLE_TWO_DEFINITION);
//        myDB.setVersionUpgrade(VERSION, new UpgradeRunnable() {
//            @Override
//            public void run() {
//                db.execSQL(TABLE_TWO_DEFINITION);
//                Log.d(TAG,"onUpgrade");
//            }
//        });

        AccessDB.addDB(c, myDB);
        AccessDB.addWriteTask(new WriterTask(DB_NAME, null) {

            @Override
            public void run() {
                db.beginTransaction();
                try {
                    for (int i = 0; i < 5; i++) {
                        String val;
                        switch (i) {
                            case 0:
                                val = "zero";
                                break;
                            case 1:
                                val = "one";
                                break;
                            case 2:
                                val = "two";
                                break;
                            case 3:
                                val = "three";
                                break;
                            case 4:
                                val = "four";
                                break;
                            default:
                                val = "broken";
                                break;
                        }
                        ContentValues values = new ContentValues();
                        values.put("_id", i);
                        values.put("item", val);
                        Log.d(TAG, "insert " + values.toString());
                        db.insert(ITEMS, null, values);
                    }
                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    Log.e(TAG, "failed to insert", ex);
                } finally {
                    db.endTransaction();
                }
            }
        });
        AccessDB.addWriteTask(new WriterTask(DB_NAME, callback) {

            @Override
            public void run() {
                db.beginTransaction();
                try {
                    ContentValues values = new ContentValues();
                    values.put("_id", 5);
                    values.put("item", "five");
                    db.insert(ITEMS, null, values);
                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    Log.e(TAG, "failed to insert", ex);
                } finally {
                    db.endTransaction();
                }
                callback.run();
            }
        });

        // uncomment to test upgrade
//        AccessDB.addWriteTask(new WriterTask(DB_NAME, callback) {
//
//            @Override
//            public void run() {
//                db.beginTransaction();
//                try {
//                    ContentValues values = new ContentValues();
//                    values.put("_id", 0);
//                    values.put("item", "zero");
//                    db.insert(TABLE_TWO, null, values);
//                    db.setTransactionSuccessful();
//                } catch (Exception ex) {
//                    Log.e(TAG, "failed to insert", ex);
//                } finally {
//                    db.endTransaction();
//                }
//                callback.run();
//            }
//        });

    }

    SQLiteDatabase getDB() {
        return AccessDB.getReadableDB(DB_NAME);
    }

    Cursor getItems() {
        return AccessDB.getReadableDB(DB_NAME).query(ITEMS, null, null, null, null, null, null);
    }

    // uncomment to test upgrade
//    Cursor getItemsTwo() {
//        return AccessDB.getReadableDB(DB_NAME).query(TABLE_TWO, null, null, null, null, null, null);
//    }


}
