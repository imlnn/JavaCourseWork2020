package main.service;

import main.entity.Patient;
import main.model.PatientModel;

import java.util.List;

public interface PatientService {
    public void add(PatientModel patient);

    public void delete(long id);

    public List<Patient> listPatients();
}
