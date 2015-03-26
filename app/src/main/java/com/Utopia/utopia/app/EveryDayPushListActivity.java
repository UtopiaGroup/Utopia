package com.Utopia.utopia.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class EveryDayPushListActivity extends Activity {

    public static final int KIND_SCHEDULE = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;
    public static final int KIND_TIP = DataProviderMetaData.DataTableMetaData.KIND_TIP;
    public static final int KIND_ADVERTISE = DataProviderMetaData.DataTableMetaData.KIND_ADVERTISE;

    private PullToRefreshListView lv0;
    private ContentResolver cr;
    Cursor cursor;
    private SimpleAdapter sa;
    List<Bundle> listResource = new ArrayList<Bundle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day_push_list);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //
        init();
    }

    void init() {
        lv0 = (PullToRefreshListView) findViewById(R.id.every_day_push_list);
        lv0.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv0.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.v("DEBUG list size", "" + listResource.size());

                new LoadingTask().execute();
            }
        });

        cr = getContentResolver();
        cursor = cr.query(DataProviderMetaData.DataTableMetaData.CONTENT_URI, new String[]{"created", "modified", "title", "value", "begin",
                        "end", "finish", "kind", "call"}, "kind = " + KIND_ADVERTISE,
                null, "begin DESC");
        for(int i = 0;i < 6*5;i++)
            if(cursor.moveToNext()) {
                Bundle map = new Bundle();
                long created, modified, begin, end, finish, kind, call;
                String title, value;

                created = cursor.getLong(cursor.getColumnIndex("created"));
                modified = cursor.getLong(cursor.getColumnIndex("modified"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                value = cursor.getString(cursor.getColumnIndex("value"));
                begin = cursor.getLong(cursor.getColumnIndex("begin"));
                end = cursor.getLong(cursor.getColumnIndex("end"));
                finish = cursor.getLong(cursor.getColumnIndex("finish"));
                kind = cursor.getLong(cursor.getColumnIndex("kind"));
                call = cursor.getLong(cursor.getColumnIndex("call"));

                map.putLong("created", created);
                map.putLong("modified", modified);
                map.putString("title", title);
                map.putString("value", value);

                map.putLong("begin", begin);
                map.putLong("end", end);
                map.putLong("finish", finish);
                map.putLong("kind", kind);
                map.putLong("call", call);
                listResource.add(map);

                Log.v("Debug", "in update" + listResource.size());

            }
        sa = new EveryDayPushListItemAdapter(this, listResource, R.layout.every_day_push_view_pager, null, null);
        lv0.setAdapter(sa);
    }


    private class LoadingTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            int n = 6*5;
            while(n > 0 && cursor.moveToNext()) {
                n = n-1;
                Bundle map = new Bundle();
                long created, modified, begin, end, finish, kind, call;
                String title, value;

                created = cursor.getLong(cursor.getColumnIndex("created"));
                modified = cursor.getLong(cursor.getColumnIndex("modified"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                value = cursor.getString(cursor.getColumnIndex("value"));
                begin = cursor.getLong(cursor.getColumnIndex("begin"));
                end = cursor.getLong(cursor.getColumnIndex("end"));
                finish = cursor.getLong(cursor.getColumnIndex("finish"));
                kind = cursor.getLong(cursor.getColumnIndex("kind"));
                call = cursor.getLong(cursor.getColumnIndex("call"));

                map.putLong("created", created);
                map.putLong("modified", modified);
                map.putString("title", title);
                map.putString("value", value);

                map.putLong("begin", begin);
                map.putLong("end", end);
                map.putLong("finish", finish);
                map.putLong("kind", kind);
                map.putLong("call", call);
                listResource.add(map);
                Log.v("Debug", ""+listResource.size());
                Log.v("Debug", title);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sa = new EveryDayPushListItemAdapter(getApplicationContext(), listResource, R.layout.every_day_push_view_pager, null, null);
            lv0.setAdapter(sa);
            lv0.onRefreshComplete();
            ListView listView = lv0.getRefreshableView();
            listView.setSelection(listView.getBottom());


            super.onPostExecute(aVoid);
        }
    }

    private void update() {
        int n = 10;
        while(n > 0 && cursor.moveToNext()) {
            n = n-1;
            Bundle map = new Bundle();
            long created, modified, begin, end, finish, kind, call;
            String title, value;

            created = cursor.getLong(cursor.getColumnIndex("created"));
            modified = cursor.getLong(cursor.getColumnIndex("modified"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            value = cursor.getString(cursor.getColumnIndex("value"));
            begin = cursor.getLong(cursor.getColumnIndex("begin"));
            end = cursor.getLong(cursor.getColumnIndex("end"));
            finish = cursor.getLong(cursor.getColumnIndex("finish"));
            kind = cursor.getLong(cursor.getColumnIndex("kind"));
            call = cursor.getLong(cursor.getColumnIndex("call"));

            map.putLong("created", created);
            map.putLong("modified", modified);
            map.putString("title", title);
            map.putString("value", value);

            map.putLong("begin", begin);
            map.putLong("end", end);
            map.putLong("finish", finish);
            map.putLong("kind", kind);
            map.putLong("call", call);
            listResource.add(map);
            Log.v("Debug", "in update"+n);
        }
        sa.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.every_day_push_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
