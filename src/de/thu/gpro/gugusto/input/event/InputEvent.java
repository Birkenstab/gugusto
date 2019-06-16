package de.thu.gpro.gugusto.input.event;

public interface InputEvent {

    InputEventType getType();
    boolean isConsumed();

}
