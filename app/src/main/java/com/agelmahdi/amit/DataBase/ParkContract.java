package com.agelmahdi.amit.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ahmed El-Mahdi on 12/19/2017.
 */

public class ParkContract {

    public static final String AUTHORITY = "com.agelmahdi.amit";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_PARK = "park";

    public static final class ParkEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARK).build();

        public static final String TABLE_NAME = "park";

        public static final String COLUMN_USER_ID = "user_id";

        public static final String COLUMN_USER_NAME = "user_name";

        public static final String COLUMN_ADDRESS = "address";

        public static final String COLUMN_LAT = "lat";

        public static final String COLUMN_LNG = "lng";

    }




}
