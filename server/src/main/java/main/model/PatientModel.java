package main.model;

import main.entity.Patient;

public class PatientModel {
    private String firstName;
    private String lastName;
    private String patronymicName;
    private long diagnosisId;
    private long wardId;

    public PatientModel(Patient patient) {
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.patronymicName = patient.getFatherName();
        this.diagnosisId = patient.getDiagnosis().getId();
        this.wardId = patient.getWard().getId();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(long id) {
        this.diagnosisId = id;
    }

    public long getWardId() {
        return wardId;
    }

    public void setWardId(long id) {
        this.wardId = id;
    }
}
