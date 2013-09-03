package com.feigdev.sqlsample;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private Handler handler = new Handler();
    private ListView lv;
    private SimpleCursorAdapter la;
    private DummyData dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.list);
        Runnable callback = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Cursor c = dd.getItems();
                        la.swapCursor(c);
                        la.notifyDataSetInvalidated();
                        Log.d(TAG, "running callback, " + c.getCount());
                        while (c.moveToNext())
                            Log.d(TAG,"item: " + c.getInt(0) + " - " + c.getString(1));

                        // uncomment to test upgrade
//                        Cursor c2 = dd.getItemsTwo();
//                        Log.d(TAG, "running callback, " + c2.getCount());
//                        while (c2.moveToNext())
//                            Log.d(TAG,"item: " + c2.getInt(0) + " - " + c2.getString(1));
                    }
                });
            }
        };

        dd = new DummyData(this, callback);
        la = new DummyAdapter(this, null);
        lv.setAdapter(la);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
