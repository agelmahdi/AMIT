package com.agelmahdi.amit;

import com.agelmahdi.amit.model.Datum;
import com.agelmahdi.amit.model.Park;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */

public interface ParkService {

    @GET("/parkForMe/index.php")
    Observable<Park> getPark();

}
