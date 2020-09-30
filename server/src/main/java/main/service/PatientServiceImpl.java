package main.service;

import main.entity.Diagnosis;
import main.entity.Patient;
import main.entity.Ward;
import main.exception.DiagnosisNotFoundException;
import main.exception.WardNotFoundException;
import main.exception.WardOverflowException;
import main.exception.PatientNotFoundException;
import main.model.PatientModel;
import main.repository.DiagnosisRepository;
import main.repository.PatientRepository;
import main.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private WardRepository wardRepository;

    @Override
    public void add(PatientModel patient) {
        Optional<Ward> ward = wardRepository.findById(patient.getWardId());
        if (ward.isEmpty()) {
            throw new WardNotFoundException();
        }

        Optional<Diagnosis> diag = diagnosisRepository.findById(patient.getDiagnosisId());
        if (diag.isEmpty()) {
            throw new DiagnosisNotFoundException();
        }

        List<Patient> patients = listPatients()
                .stream()
                .filter(patient1 -> {
                    return patient1.getWard().getId() == ward.get().getId();
                })
                .collect(Collectors.toList());

        if (patients.size() >= ward.get().getMax()) {
            throw new WardOverflowException();
        }
        patientRepository.save(new Patient(
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPatronymicName(),
                diag.get(),
                ward.get()));
    }

    @Override
    public void delete(long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException();
        }
        patientRepository.delete(patient.get());
    }

    @Override
    public List<Patient> listPatients() {
        return (List<Patient>) patientRepository.findAll();
    }
}
