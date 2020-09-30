package main.model;

public class DiagnosisModel {
    private String name;

    public DiagnosisModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Diagnosis " + name;
    }
}
