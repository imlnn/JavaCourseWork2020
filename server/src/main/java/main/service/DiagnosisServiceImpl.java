package main.service;

import main.entity.Diagnosis;
import main.entity.Patient;
import main.exception.DiagnosisNotFoundException;
import main.repository.DiagnosisRepository;
import main.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void add(Diagnosis diagnosis) {
        diagnosisRepository.save(diagnosis);
    }

    @Override
    public void delete(long id) {
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(id);
        if (diagnosis.isEmpty()) {
            throw new DiagnosisNotFoundException();
        }
        diagnosisRepository.delete(diagnosis.get());
    }

    @Override
    public List<Diagnosis> listDiagnoses() {
        return (List<Diagnosis>) diagnosisRepository.findAll();
    }

    @Override
    public List<Patient> getPatientsByDiagnosis(long id) {
        List<Patient> patients = (List<Patient>) patientRepository.findAll();
        return patients
                .stream()
                .filter(patient -> patient.getDiagnosis().getId() == id)
                .collect(Collectors.toList());
    }
}
