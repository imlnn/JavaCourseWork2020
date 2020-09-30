package main.service;

import main.entity.Patient;
import main.entity.Ward;

import java.util.List;

public interface WardService {
    public void add(Ward ward);

    public void delete(long id);

    public List<Ward> listWards();

    public List<Patient> getPatientsByWardId(long id);
}
