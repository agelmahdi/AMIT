package com.agelmahdi.amit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.agelmahdi.amit.Application.ParkApp;
import com.agelmahdi.amit.Base.ParkPresenter;
import com.agelmahdi.amit.View.ParkView;
import com.agelmahdi.amit.model.Datum;
import com.agelmahdi.amit.model.Park;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements ParkView {
    @Inject
    ParkService parkService ;
    private ParkPresenter mParkPresenter;

    private ParkAdapter mParkAdapter;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolveDependancy();
        ButterKnife.bind(MainActivity.this);
        configViews();
        mParkPresenter = new ParkPresenter(MainActivity.this);
        mParkPresenter.onCreate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mParkPresenter.onResume();
        mParkPresenter.fetchParks();
    }

    private void configViews() {
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mParkAdapter = new ParkAdapter(new ArrayList<Datum>());
        mRecyclerView.setAdapter(mParkAdapter);
    }

    private void resolveDependancy() {
        ((ParkApp)getApplication() )
                .getApiComponent()
                .inject(MainActivity.this);
    }

    @Override
    public void onCompleted() {
        Toast.makeText(getApplicationContext(),"Parks Done!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getApplicationContext(),"error: "+message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onParks(Park parkResponses) {
        List<Datum> data=parkResponses.getData();
        mParkAdapter.addPark(data);

    }


    @Override
    public Observable<Park> getParks() {
        return parkService.getPark();
    }

}
