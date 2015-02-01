package com.Utopia.utopia.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment0 extends Fragment {
    public static final int KIND_NOTE = DataProviderMetaData.DataTableMetaData.KIND_NOTE;

    private ListView lv0;
    private ContentResolver cr;
    private ImageView button0;
    private SimpleAdapter sa;
    private InputDialog dialog;
    List<Bundle> listResource = new ArrayList<Bundle>();

    public ViewPagerFragment0() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout0, container, false);

        button0 = (ImageView) view.findViewById(R.id.page0button);
        lv0 = (ListView) view.findViewById(R.id.page0ListView0);

        registerForContextMenu(lv0);
        cr = getActivity().getContentResolver();


        dialog = new InputDialog(getActivity());
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                addEntry(dialog.getContent());
            }
        });

        FromSQLToListView();

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Utopia", "imageClicked");
                dialog.show();

            }

        });
        return view;
    }

    public void addEntry(String value) {
        if (!value.equals("")) {
            ContentValues cv = new ContentValues();
            long marginLeft = Double.valueOf(70 * Math.random() + 30).longValue();
            long marginRight = Double.valueOf(70 * Math.random() + 30).longValue();
            cv.put("value", value);
            cv.put("kind", KIND_NOTE);
            cv.put("created", marginLeft);
            cv.put("end", marginRight);
            cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);

            Bundle map = new Bundle();
            map.putString("value", value);
            map.putLong("kind", KIND_NOTE);
            map.putLong("created", marginLeft);
            map.putLong("end", marginRight);
            listResource.add(map);
            sa.notifyDataSetChanged();
        }

    }

    public void FromSQLToListView() {
        Uri uri = DataProviderMetaData.DataTableMetaData.CONTENT_URI;

        Cursor cursor = cr.query(DataProviderMetaData.DataTableMetaData.CONTENT_URI, new String[]{"created", "value", "kind", "end"},
                "kind = " + KIND_NOTE, null, "created asc");

        //Log.i("utopia", String.valueOf(cursor == null));
        listResource.clear();
        while (cursor.moveToNext()) {
            Bundle map = new Bundle();
            map.putLong("created", cursor.getLong(cursor.getColumnIndex("created")));
            map.putString("value", cursor.getString(cursor.getColumnIndex("value")));
            map.putLong("end", cursor.getLong(cursor.getColumnIndex("end")));
            listResource.add(map);
        }

        sa = new NotePadListItemAdapter(getActivity(), listResource, R.layout.notepad_listitem, null, null);
        lv0.setAdapter(sa);
        cursor.close();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.note_book_entry_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            //TODO next time...
        }
        return super.onContextItemSelected(item);
    }
}

