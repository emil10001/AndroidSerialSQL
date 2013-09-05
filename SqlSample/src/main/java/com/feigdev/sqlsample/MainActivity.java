package com.feigdev.sqlsample;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 1932;
    private Handler handler = new Handler();
    private ListView lv;
    private SimpleCursorAdapter la;
    private DummyData dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.list);
        Runnable callback = new Runnable() {
            @Override
            public void run() {
                getSupportLoaderManager().getLoader(LOADER_ID).forceLoad();
            }
        };

        dd = new DummyData(this, callback);
        la = new DummyAdapter(this, null);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        lv.setAdapter(la);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new DumbLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        la.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        la.swapCursor(null);
    }
}
