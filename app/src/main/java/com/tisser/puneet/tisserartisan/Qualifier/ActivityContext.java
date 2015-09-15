package com.tisser.puneet.tisserartisan.Qualifier;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Arun on 13-Sep-15.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ActivityContext {
}
