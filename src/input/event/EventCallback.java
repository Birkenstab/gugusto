package input.event;

public interface EventCallback<T> {

    boolean callback(T event);

}
