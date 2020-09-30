package main.entity;

import javax.persistence.*;

@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "MAX_COUNT", nullable = false)
    private Long max;

    public Ward() {
    }

    public Ward(String name, Long max) {
        this.name = name;
        this.max = max;
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

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
