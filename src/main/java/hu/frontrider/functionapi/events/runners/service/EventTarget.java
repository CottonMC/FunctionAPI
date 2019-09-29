package hu.frontrider.functionapi.events.runners.service;


import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**any event service annotated with this will bind to the selected event.*/
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EventTargets.class)
public @interface EventTarget {
    String value();
}
