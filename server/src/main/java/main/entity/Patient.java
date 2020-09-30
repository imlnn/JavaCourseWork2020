package main.entity;

import javax.persistence.*;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "PATRONYMIC_NAME")
    private String patronymicName;

    @ManyToOne
    @JoinColumn(name = "DIAGNOSIS", nullable = false)
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "WARD", nullable = false)
    private Ward ward;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String patronymicName, Diagnosis diagnosis, Ward ward) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.diagnosis = diagnosis;
        this.ward = ward;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFatherName() {
        return patronymicName;
    }

    public void setFatherName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }
}
