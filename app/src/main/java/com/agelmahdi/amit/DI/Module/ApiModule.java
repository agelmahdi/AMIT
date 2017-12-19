package com.agelmahdi.amit.DI.Module;

import com.agelmahdi.amit.DI.Scope.AppScope;
import com.agelmahdi.amit.ParkService;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */
@Module
public class ApiModule {
    @Provides
    @AppScope
    ParkService providerParkService(Retrofit retrofit){
        return retrofit.create(ParkService.class);
    }

}
