package com.amhzing.activities.ui.annotation;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Inherited
@Profile("offline")
public @interface Offline {

}
