package com.agelmahdi.amit.Application;

import android.app.Application;

import com.agelmahdi.amit.DI.Component.ApiComponent;
import com.agelmahdi.amit.DI.Component.DaggerApiComponent;
import com.agelmahdi.amit.DI.Component.DaggerNetworkComponent;
import com.agelmahdi.amit.DI.Component.NetworkComponent;
import com.agelmahdi.amit.DI.Module.NetworkModule;
import com.agelmahdi.amit.model.Constant;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */

public class ParkApp extends Application {
    ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        resolveDependancy();
        super.onCreate();
    }

        private void resolveDependancy() {
            mApiComponent = DaggerApiComponent.builder()
                    .networkComponent(getNetworkComponent())
                    .build();

    }
    public NetworkComponent getNetworkComponent() {
        return DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(Constant.BASE_URL))
                .build();
    }
    public ApiComponent getApiComponent() {
        return mApiComponent;
    }

}
