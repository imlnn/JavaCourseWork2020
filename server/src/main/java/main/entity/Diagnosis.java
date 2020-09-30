package main.entity;

import javax.persistence.*;

@Entity
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DIAGNOSIS_NAME", nullable = false, unique = true)
    private String name;

    public Diagnosis() {
    }

    public Diagnosis(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
