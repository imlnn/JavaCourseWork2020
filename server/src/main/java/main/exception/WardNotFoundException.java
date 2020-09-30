package main.exception;

public class WardNotFoundException extends RuntimeException {
    public WardNotFoundException() {
        super("Ward not found");
    }

    public WardNotFoundException(String msg) {
        super(msg);
    }
}
