package main.web;

import main.entity.Patient;
import main.entity.Ward;
import main.model.PatientModel;
import main.model.WardModel;
import main.service.WardService;
import org.hibernate.persister.walking.spi.WalkingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wards")
public class WardController {
    private WardService wardService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void addWard(@RequestBody WardModel ward) {
        try {
            wardService.add(new Ward(ward.getName(), ward.getMax()));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ward already exist");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteWard(@PathVariable("id") long id) {
        try {
            wardService.delete(id);
        } catch (WalkingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ward not found");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Ward>> getAllWards() {
        List<Ward> wards = wardService.listWards();
        return new ResponseEntity<>(wards, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PatientModel>> getPatientsInWard(@PathVariable long id) {
        List<Patient> patients = wardService.getPatientsByWardId(id);
        List<PatientModel> models = patients.stream()
                .map(PatientModel::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @Autowired
    public void setWardService(WardService wardService) {
        this.wardService = wardService;
    }
}
