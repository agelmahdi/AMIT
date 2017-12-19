package com.agelmahdi.amit.Base;

import com.agelmahdi.amit.View.ParkView;
import com.agelmahdi.amit.model.Datum;
import com.agelmahdi.amit.model.Park;

import java.util.List;

import rx.Observer;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */

public class ParkPresenter extends BasePresenter implements Observer<Park> {
    private ParkView mParkView;
    public ParkPresenter(ParkView parkView) {
        this.mParkView = parkView;
    }

    @Override
    public void onCompleted() {
        mParkView.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
       mParkView.onError(e.getMessage());
    }

    @Override
    public void onNext(Park park) {
        mParkView.onParks(park);

    }


    public void fetchParks() {
        unSubscribeAll();
        subscribe(mParkView.getParks(),ParkPresenter.this);
    }
}
