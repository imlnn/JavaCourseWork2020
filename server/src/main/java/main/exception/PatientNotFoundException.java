package main.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException() {
        super("Patient not found");
    }

    public PatientNotFoundException(String msg) {
        super(msg);
    }
}
