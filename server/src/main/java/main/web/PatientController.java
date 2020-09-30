package main.web;

import main.entity.Patient;
import main.exception.DiagnosisNotFoundException;
import main.exception.PatientNotFoundException;
import main.exception.WardNotFoundException;
import main.exception.WardOverflowException;
import main.model.PatientModel;
import main.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private PatientService patientService;

    @PostMapping(value = "/add", consumes = "application/json")
    public void addPatient(@RequestBody PatientModel patient) {
        try {
            patientService.add(patient);
        } catch (WardOverflowException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        } catch (WardNotFoundException | DiagnosisNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable("id") long id) {
        try {
            patientService.delete(id);
        } catch (PatientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.listPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }
}
