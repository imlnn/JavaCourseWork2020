package main.service;

import main.entity.Diagnosis;
import main.entity.Patient;

import java.util.List;

public interface DiagnosisService {
    void add(Diagnosis diagnosis);

    void delete(long id);

    public List<Diagnosis> listDiagnoses();

    public List<Patient> getPatientsByDiagnosis(long id);
}
