package main.exception;

public class AccesDeniedException extends RuntimeException {
    public AccesDeniedException() {
        super("Wrong authorisation token");
    }

    public AccesDeniedException(String s) {
        super(s);
    }
}
