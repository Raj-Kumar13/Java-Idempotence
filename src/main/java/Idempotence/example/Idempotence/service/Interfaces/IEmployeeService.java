package Idempotence.example.Idempotence.service.Interfaces;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import Idempotence.example.Idempotence.exception.ResourceNotFoundException;
import Idempotence.example.Idempotence.model.Employee;
import Idempotence.example.Idempotence.repository.EmployeeRepository;
import Idempotence.example.Idempotence.service.EmployeeService;
@Service
public class IEmployeeService implements EmployeeService{

    private EmployeeRepository  employeeRepository;
    public IEmployeeService(EmployeeRepository employeeRepository){
        super();
        this.employeeRepository = employeeRepository;

    }


    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Override
    public Employee saveEmployee(Employee employee, String idempotenceKey) {
    
        Optional<Employee> existingEmployee = employeeRepository.findByIdempotenceKey(idempotenceKey);
        if (existingEmployee.isPresent()) {
            return existingEmployee.get();
        }
        employee.setIdempotenceKey(idempotenceKey);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    @Override
    public Employee getEmployeeById(long id) {
      /*  Optional<Employee> employee = employeeRepository.findById(id);
       if(employee.isPresent()){
        return employee.get();
       }
       else{
        throw new ResourceNotFoundException("Employee ", "ID: ", id);
       }*/
       return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee ", "ID", id));
    }
    @Override
    public Employee updateEmployee(Employee employee, long id) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee ","ID ",id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        employeeRepository.save(existingEmployee);
        return existingEmployee;
    }
    @Override
    public void deleteEmployee(long id) {
       employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee ", "ID ", id));
       employeeRepository.deleteById(id);
    }

    


}