package com.agelmahdi.amit.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import static com.agelmahdi.amit.DataBase.ParkContract.ParkEntry.TABLE_NAME;

/**
 * Created by Ahmed El-Mahdi on 12/19/2017.
 */

public class ParkProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SqliteHelper mOpenHelper;

    public static final int PARK = 100;
    public static final int PARK_WITH_ID = 101;


    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);


        matcher.addURI(ParkContract.AUTHORITY, ParkContract.PATH_PARK, PARK);

        matcher.addURI(ParkContract.AUTHORITY, ParkContract.PATH_PARK + "/#", PARK_WITH_ID);

        return matcher;

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new SqliteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor retCursor;

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {
            case PARK:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PARK:

                long id = db.insert(TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(ParkContract.ParkEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int tasksDeleted;


        switch (match) {

            case PARK_WITH_ID:

                String id = uri.getPathSegments().get(1);

                tasksDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int count = 0;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match){
            case PARK:
                //
                break;
            case PARK_WITH_ID:
                String rowID = uri.getPathSegments().get(1);
                s = ParkContract.ParkEntry._ID + "=" + rowID
                        + (!TextUtils.isEmpty(s) ?
                        " AND (" + s + ')' : "");
                count = db.update(TABLE_NAME,contentValues, s, strings);

                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
