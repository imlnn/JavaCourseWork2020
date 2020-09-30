package main.model;

public class PatientModel {
    private String firstName;
    private String lastName;
    private String patronymicName;
    private long diagnosisId;
    private long wardId;

    public PatientModel(String firstName, String lastName, String patronymicName, long diagnosisId, long wardId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.diagnosisId = diagnosisId;
        this.wardId = wardId;
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

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + patronymicName + " in ward " + wardId + " with diagnosis " + diagnosisId;
    }
}
