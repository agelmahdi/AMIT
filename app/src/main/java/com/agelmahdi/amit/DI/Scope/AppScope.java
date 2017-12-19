package com.agelmahdi.amit.DI.Scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope {
}
