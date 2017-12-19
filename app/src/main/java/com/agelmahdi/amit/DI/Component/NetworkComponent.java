package com.agelmahdi.amit.DI.Component;

import com.agelmahdi.amit.DI.Module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */
@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {
    Retrofit retrofit();

}
