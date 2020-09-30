package main.web;

import main.entity.Diagnosis;
import main.entity.Patient;
import main.exception.DiagnosisNotFoundException;
import main.model.DiagnosisModel;
import main.model.PatientModel;
import main.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/diagnoses")
public class DiagnosisController {
    private DiagnosisService diagnosisService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void addDiagnosis(@RequestBody DiagnosisModel diag) {
        try {
            diagnosisService.add(new Diagnosis(diag.getName()));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Diagnosis already exist");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDiagnosis(@PathVariable("id") long id) {
        try {
            diagnosisService.delete(id);
        } catch (DiagnosisNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diagnosis not found");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Diagnosis>> getAllDiagnoses() {
        List<Diagnosis> patients = diagnosisService.listDiagnoses();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PatientModel>> getPatientsWithDiagnosis(@PathVariable("id") long id) {
        List<Patient> patients = diagnosisService.getPatientsByDiagnosis(id);
        List<PatientModel> models = patients.stream()
                .map(PatientModel::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @Autowired
    public void setDiagnosisService(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }
}
