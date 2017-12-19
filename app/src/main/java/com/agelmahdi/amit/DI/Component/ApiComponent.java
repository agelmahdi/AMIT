package com.agelmahdi.amit.DI.Component;

import com.agelmahdi.amit.DI.Module.ApiModule;
import com.agelmahdi.amit.DI.Scope.AppScope;
import com.agelmahdi.amit.MainActivity;

import dagger.Component;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */
@AppScope
@Component(modules = ApiModule.class,dependencies = NetworkComponent.class)
public interface ApiComponent {
    void inject(MainActivity mainActivity);
}
