package main.exception;

public class RequestFailureException extends RuntimeException {
    public RequestFailureException(String s) {
        super(s);
    }
}
