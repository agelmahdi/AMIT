package com.agelmahdi.amit.DI.Module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */
@Module
public class NetworkModule {
    private String baseUrl;

    public NetworkModule(String baseUrl){
        this.baseUrl=baseUrl;
    }
    @Provides
    @Singleton
    GsonConverterFactory gsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJavaCallAdapterFactory rxJavaCallAdapterFactory(){
        return RxJavaCallAdapterFactory.create();
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory, RxJavaCallAdapterFactory rxJavaCallAdapterFactory){
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

}
