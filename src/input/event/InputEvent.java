package input.event;

public interface InputEvent {

    InputEventType getType();
    boolean isConsumed();

}
