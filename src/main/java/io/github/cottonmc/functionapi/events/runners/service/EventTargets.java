package io.github.cottonmc.functionapi.events.runners.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventTargets {
    EventTarget[] value();
}
