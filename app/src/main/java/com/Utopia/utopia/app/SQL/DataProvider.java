package com.Utopia.utopia.app.SQL;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.Utopia.utopia.app.TimeUtil;

import java.util.HashMap;

/**
 * CreateCreated by Administrator on 2014/7/27 0027.
 */
@SuppressWarnings("ALL")
public class DataProvider extends ContentProvider {
    private static final String TAG = "dataProvider";
    private static HashMap<String, String> sDataProjectionMap;

    static {
        sDataProjectionMap = new HashMap<String, String>();
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData._ID,
                DataProviderMetaData.DataTableMetaData._ID);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_CREATE,
                DataProviderMetaData.DataTableMetaData.DATA_CREATE);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_MODIFIED,
                DataProviderMetaData.DataTableMetaData.DATA_MODIFIED);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_TITLE,
                DataProviderMetaData.DataTableMetaData.DATA_TITLE);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_VALUE,
                DataProviderMetaData.DataTableMetaData.DATA_VALUE);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_BEGIN,
                DataProviderMetaData.DataTableMetaData.DATA_BEGIN);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_END,
                DataProviderMetaData.DataTableMetaData.DATA_END);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_FINISH,
                DataProviderMetaData.DataTableMetaData.DATA_FINISH);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_KIND,
                DataProviderMetaData.DataTableMetaData.DATA_KIND);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_HINT,
                DataProviderMetaData.DataTableMetaData.DATA_HINT);
        sDataProjectionMap.put(DataProviderMetaData.DataTableMetaData.DATA_BITMAP,
                DataProviderMetaData.DataTableMetaData.DATA_BITMAP);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DataProviderMetaData.DATABASE_NAME, null, DataProviderMetaData.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "inner oncreate called");
            db.execSQL("CREATE TABLE " + DataProviderMetaData.DataTableMetaData.TABLE_NAME + " ("
                    + DataProviderMetaData.DataTableMetaData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DataProviderMetaData.DataTableMetaData.DATA_CREATE + " INTEGER, "
                    + DataProviderMetaData.DataTableMetaData.DATA_MODIFIED + " INTEGER, "
                    + DataProviderMetaData.DataTableMetaData.DATA_TITLE + " TEXT, "
                    + DataProviderMetaData.DataTableMetaData.DATA_VALUE + " TEXT, "
                    + DataProviderMetaData.DataTableMetaData.DATA_BEGIN + " INTEGER, "
                    + DataProviderMetaData.DataTableMetaData.DATA_END + " INTEGER, "
                    + DataProviderMetaData.DataTableMetaData.DATA_FINISH + " INTEGER, "
                    + DataProviderMetaData.DataTableMetaData.DATA_KIND + " INTEGER, "
                    + DataProviderMetaData.DataTableMetaData.DATA_HINT + " INTEGER, "
                    + DataProviderMetaData.DataTableMetaData.DATA_BITMAP + " BLOB" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "inner onupgrade called");
            Log.w(TAG, "Upgrading database form version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DataProviderMetaData.DataTableMetaData.TABLE_NAME);
            onCreate(db);
        }
    }

    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "main onCreate called");
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DataProviderMetaData.DataTableMetaData.TABLE_NAME);
        qb.setProjectionMap(sDataProjectionMap);
        String orderBy;
        if (TextUtils.isEmpty(sortOrder))
            orderBy = DataProviderMetaData.DataTableMetaData.DEFAULT_SORT_ORDER;
        else
            orderBy = sortOrder;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        return DataProviderMetaData.DataTableMetaData.CONTENT_TYPE;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        ContentValues values;
        if (initialValues != null)
            values = new ContentValues(initialValues);
        else
            values = new ContentValues();
        Long now = TimeUtil.getCurrentTime();
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_CREATE) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_CREATE, now);
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_MODIFIED) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_MODIFIED, now);
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_TITLE) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_TITLE, "未命名");
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_VALUE) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_VALUE, "无");
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_BEGIN) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_BEGIN, now);
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_END) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_END, TimeUtil.ENDOfWORLD);
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_FINISH) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_FINISH, 0);
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_KIND) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_KIND, DataProviderMetaData.DataTableMetaData.KIND_NONE);
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_HINT) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_HINT, "");
        if (values.containsKey(DataProviderMetaData.DataTableMetaData.DATA_BITMAP) == false)
            values.put(DataProviderMetaData.DataTableMetaData.DATA_BITMAP, new byte[]{});


        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(DataProviderMetaData.DataTableMetaData.TABLE_NAME,
                DataProviderMetaData.DataTableMetaData.DATA_CREATE, values);
        Uri insertedDataUri = ContentUris.withAppendedId(DataProviderMetaData.DataTableMetaData.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(insertedDataUri, null);
        return insertedDataUri;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        count = db.delete(DataProviderMetaData.DataTableMetaData.TABLE_NAME, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        count = db.update(DataProviderMetaData.DataTableMetaData.TABLE_NAME, values, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
