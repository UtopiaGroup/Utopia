package com.Utopia.utopia.app.SQL;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2014/7/27 0027.
 */
public class    DataProviderMetaData {
    public static final String AUTHORITY = "com.Utopia.utopia.app.SQL.DataProvider";
    public static final String DATABASE_NAME = "data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATA_TABLE_NAME = "data";

    private DataProviderMetaData() {
    }

    public static final class DataTableMetaData implements BaseColumns {
        private DataTableMetaData() {
        }

        public static final String TABLE_NAME = "data";

        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/data");
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.Utopia.data";

        public static final String DEFAULT_SORT_ORDER = "created ASC";

        //time format : 2008/02/21 16:32:56 is 20080221163256

        public static final String _ID = "_id";

        public static final String DATA_CREATE = "created";
        public static final String DATA_MODIFIED = "modified";
        public static final String DATA_TITLE = "title";
        public static final String DATA_VALUE = "value";
        public static final String DATA_BEGIN = "begin";
        public static final String DATA_END = "end";
        public static final String DATA_FINISH = "finish";
        public static final String DATA_KIND = "kind";
        public static final String DATA_HINT = "myhint";
        public static final String DATA_BITMAP = "edpv";

        public static final int KIND_NONE = 0;
        public static final int KIND_NOTE = 1;
        public static final int KIND_SCHEDULE = 2;
        public static final int KIND_TIP = 3;
        public static final int KIND_ADVERTISE = 4;
        public static final int KIND_EVENT = 5;
    }

}
