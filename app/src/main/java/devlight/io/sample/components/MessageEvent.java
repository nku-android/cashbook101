package devlight.io.sample.components;

public class MessageEvent {
    public static final String UPDATE_TODO = "update_todo";

    public final String message;

    private MessageEvent(String message) {
        this.message = message;
    }

    public static MessageEvent UpdateTodo() {
        return new MessageEvent(UPDATE_TODO);
    }
}
