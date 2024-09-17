package Idempotence.example.Idempotence.service;

import java.util.List;

import Idempotence.example.Idempotence.model.Employee;

public interface EmployeeService {
    Employee saveEmployee (Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(long id);
    Employee updateEmployee(Employee employee, long id);
    void deleteEmployee(long id);

    //Idempotence methods
    Employee saveEmployee (Employee employee, String idempotenceKey);
}
