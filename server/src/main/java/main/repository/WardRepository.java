package main.repository;

import main.entity.Ward;
import org.springframework.data.repository.CrudRepository;

public interface WardRepository extends CrudRepository<Ward, Long> {
}
