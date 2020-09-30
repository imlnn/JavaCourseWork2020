package main.exception;

public class DiagnosisNotFoundException extends RuntimeException {
    public DiagnosisNotFoundException() {
        super("Diagnosis not found");
    }

    public DiagnosisNotFoundException(String msg) {
        super(msg);
    }
}
