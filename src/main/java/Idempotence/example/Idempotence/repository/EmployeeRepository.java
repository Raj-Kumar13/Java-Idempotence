package Idempotence.example.Idempotence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import Idempotence.example.Idempotence.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    Optional<Employee> findByIdempotenceKey(String idempotenceKey);
}
