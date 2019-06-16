package de.thu.gpro.gugusto.input.event;

public interface EventCallback<T> {

    boolean callback(T event);

}
