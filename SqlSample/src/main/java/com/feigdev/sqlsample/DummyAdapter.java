package com.feigdev.sqlsample;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 * Created by ejohn on 9/2/13.
 */
public class DummyAdapter extends SimpleCursorAdapter {

    public DummyAdapter(Context context, Cursor c) {
        super(context, R.layout.list_item, c, new String[]{"_id", "item"}, new int[]{R.id.tv1, R.id.tv2}, 0);
    }

}
