package com.agelmahdi.amit.View;

import com.agelmahdi.amit.model.Datum;
import com.agelmahdi.amit.model.Park;

import java.util.List;

import rx.Observable;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */

public interface ParkView  {
    void onCompleted();

    void onError(String message);

    void onParks(Park parkResponses);

    Observable<Park> getParks();
}
