package main.repository;

import main.entity.Diagnosis;
import org.springframework.data.repository.CrudRepository;

public interface DiagnosisRepository extends CrudRepository<Diagnosis, Long> {
}
