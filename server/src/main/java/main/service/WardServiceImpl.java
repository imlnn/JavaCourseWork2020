package main.service;

import main.entity.Patient;
import main.entity.Ward;
import main.exception.WardNotFoundException;
import main.repository.PatientRepository;
import main.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WardServiceImpl implements WardService {
    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void add(Ward ward) {
        wardRepository.save(ward);
    }

    @Override
    public void delete(long id) {
        Optional<Ward> ward = wardRepository.findById(id);
        if (ward.isEmpty()) {
            throw new WardNotFoundException();
        }

        for (Patient patient : getPatientsByWardId(id)) {
            patientRepository.delete(patient);
        }
        wardRepository.delete(ward.get());
    }

    @Override
    public List<Ward> listWards() {
        return (List<Ward>) wardRepository.findAll();
    }

    @Override
    public List<Patient> getPatientsByWardId(long id) {
        List<Patient> patients = (List<Patient>) patientRepository.findAll();
        return patients
                .stream()
                .filter(patient -> patient.getWard().getId() == id)
                .collect(Collectors.toList());
    }
}
