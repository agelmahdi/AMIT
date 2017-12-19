package com.agelmahdi.amit;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agelmahdi.amit.Application.ParkApp;
import com.agelmahdi.amit.Base.ParkPresenter;
import com.agelmahdi.amit.DataBase.ParkContract;
import com.agelmahdi.amit.View.ParkView;
import com.agelmahdi.amit.model.Datum;
import com.agelmahdi.amit.model.Park;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements ParkView,
        LoaderManager.LoaderCallbacks<Cursor>, ParkAdapter.ParkAdapterOnClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String input;
    private static final String[] PARKS_PROJECTION = {
            ParkContract.ParkEntry.COLUMN_USER_ID,
            ParkContract.ParkEntry.COLUMN_USER_NAME,
            ParkContract.ParkEntry.COLUMN_ADDRESS,
            ParkContract.ParkEntry.COLUMN_LNG,
            ParkContract.ParkEntry.COLUMN_LAT
            ,
    };
    public static final int COL_NUM_ID = 0;
    public static final int COL_NUM_USERS_NAME = 1;
    public static final int COL_NUM_ADDRESS = 2;
    public static final int COL_NUM_LNG = 3;
    public static final int COL_NUM_LAT = 4;

    private LoaderManager mLoaderManager;

    private static final int TASK_LOADER_ID = 0;

    @Inject
    ParkService parkService;

    private ParkPresenter mParkPresenter;
    private ParkAdapter mParkAdapter;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoaderManager = getSupportLoaderManager();

        resolveDependancy();
        ButterKnife.bind(MainActivity.this);
        configViews();
        mParkPresenter = new ParkPresenter(MainActivity.this);
        mParkPresenter.onCreate();

        if (getNetworkAvailability()) {
            mParkPresenter.fetchParks();

        } else {
            Toast.makeText(getApplicationContext(), "Fetch From Db", Toast.LENGTH_SHORT).show();
        }
        mLoaderManager.initLoader(TASK_LOADER_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mParkPresenter.onResume();

        mLoaderManager.restartLoader(TASK_LOADER_ID, null, this);

    }

    private void configViews() {
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mParkAdapter = new ParkAdapter(new ArrayList<Datum>(), this);
        mRecyclerView.setAdapter(mParkAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                String id = viewHolder.itemView.getTag().toString();

                Uri uri = ParkContract.ParkEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(id).build();

                getContentResolver().delete(uri, null, null);

                Toast.makeText(getApplicationContext(), "Row : " + id + "Deleted", Toast.LENGTH_SHORT).show();

                getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, MainActivity.this);

            }
        }).attachToRecyclerView(mRecyclerView);


    }

    private void resolveDependancy() {
        ((ParkApp) getApplication())
                .getApiComponent()
                .inject(MainActivity.this);
    }

    @Override
    public void onCompleted() {
        Toast.makeText(getApplicationContext(), "Parks Done!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getApplicationContext(), "error: " + message, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onParks(Park parkResponses) {
        List<Datum> data = parkResponses.getData();
        for (int i = 0; i < parkResponses.getData().size(); i++) {
            Datum datum = data.get(i);
            SaveIntoDatabase task = new SaveIntoDatabase();
            task.execute(datum);
        }
    }


    @Override
    public Observable<Park> getParks() {
        return parkService.getPark();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case TASK_LOADER_ID:
                String selection = ParkContract.ParkEntry.COLUMN_USER_ID;
                return new CursorLoader(this, ParkContract.ParkEntry.CONTENT_URI,
                        PARKS_PROJECTION, null, null, ParkContract.ParkEntry.COLUMN_USER_ID);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mParkAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClickPark(final int id) {
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.dialog_items, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setView(dialogView);
        final EditText userInput = (EditText) dialogView
                .findViewById(R.id.edit_text);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input = userInput.getText().toString();
                        if (!input.isEmpty()) {
                            Uri uriIdClicked = ParkContract.buildUriWithID(id);
                            ContentValues updateValues = new ContentValues();
                            updateValues.put(ParkContract.ParkEntry.COLUMN_USER_NAME, input);
                            getContentResolver().update(uriIdClicked, updateValues, ParkContract.ParkEntry._ID + "=" + id, null);
                            getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, MainActivity.this);

                            Toast.makeText(getApplicationContext(), "Parks id: " + id + " Updated!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Error Update!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class SaveIntoDatabase extends AsyncTask<Datum, Void, Void> {
        @Override
        protected Void doInBackground(Datum... data) {
            Datum datum = data[0];
            try {
                ContentValues datumm = new ContentValues();
                datumm.put(ParkContract.ParkEntry.COLUMN_USER_ID, datum.getId());
                datumm.put(ParkContract.ParkEntry.COLUMN_USER_NAME, datum.getUserNumber());
                datumm.put(ParkContract.ParkEntry.COLUMN_ADDRESS, datum.getAddress());
                datumm.put(ParkContract.ParkEntry.COLUMN_LNG, datum.getLangtitude());
                datumm.put(ParkContract.ParkEntry.COLUMN_LAT, datum.getLatitude());

                getContentResolver().insert(ParkContract.ParkEntry.CONTENT_URI, datumm);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }
    }

    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

}
