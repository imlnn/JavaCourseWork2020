package main.exception;

public class WardOverflowException extends RuntimeException {
    public WardOverflowException() {
        super("Ward is full");
    }

    public WardOverflowException(String msg) {
        super(msg);
    }
}
