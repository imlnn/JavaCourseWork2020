package main.exception;

public class DataTransformFailureException extends RuntimeException {
    public DataTransformFailureException(String s) {
        super(s);
    }
}
