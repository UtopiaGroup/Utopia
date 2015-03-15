package com.Utopia.utopia.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;

import java.util.ArrayList;
import java.util.List;

public class HealthTipListActivity extends Activity {

    public static final int KIND_TIP = DataProviderMetaData.DataTableMetaData.KIND_TIP;

    private ListView lv0;
    private ContentResolver cr;
    private SimpleAdapter sa;
    List<Bundle> listResource = new ArrayList<Bundle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tip_list);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        lv0 = (ListView) findViewById(R.id.health_tip_list);
        cr = getContentResolver();

        FromSQLToListView();
    }

    public void FromSQLToListView() {
        Uri uri = DataProviderMetaData.DataTableMetaData.CONTENT_URI;

        Cursor cursor = cr.query(DataProviderMetaData.DataTableMetaData.CONTENT_URI, new String[]{"begin", "title", "kind", "value"},
                "kind = " + KIND_TIP, null, "begin desc");

        listResource.clear();
        while (cursor.moveToNext()) {
            Bundle map = new Bundle();
            map.putLong("begin", cursor.getLong(cursor.getColumnIndex("begin")));
            map.putString("title", cursor.getString(cursor.getColumnIndex("title")));
            cursor.moveToNext();
            map.putString("value", cursor.getString(cursor.getColumnIndex("title")));
            listResource.add(map);
        }

        sa = new HealthTipListItemAdapter(this, listResource, R.layout.health_tip_list_item, null, null);
        lv0.setAdapter(sa);
        cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.health_tip_list, menu);
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
