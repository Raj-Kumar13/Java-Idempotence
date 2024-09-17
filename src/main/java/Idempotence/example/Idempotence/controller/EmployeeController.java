package Idempotence.example.Idempotence.controller;

import org.springframework.web.bind.annotation.RestController;

import Idempotence.example.Idempotence.model.Employee;
import Idempotence.example.Idempotence.service.EmployeeService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/api")
public class EmployeeController {
    private EmployeeService  employeeService;
    public EmployeeController(EmployeeService employeeService){
        super();
        this.employeeService = employeeService;
    }
    //Create
    // @PostMapping("/save")
    // public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
    //     return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    // }
    /*
     * Content-Type: application/json
       Idempotence-Key: <some-unique-id>

        {
            "firstName": "Raj",
            "lastName": "Kumar",
            "email": "Raj.Kumar@example.com"
        }
    */
    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee, @RequestHeader(value="Idempotence-Key", required = false) String idempotenceKey) {
       
        if (idempotenceKey != null) {
            return new ResponseEntity<Employee>(employeeService.saveEmployee(employee, idempotenceKey), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
        }
        
    }

    //Retrieve all 
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    //Retrieve By ID
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable ("id") long employeeId) {
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(employeeId),HttpStatus.OK);
    }

    //Update
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee){
        return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id),HttpStatus.OK);
    }

    //Delete
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Employee:: "+id+" Deleted Successfully..!",HttpStatus.OK);
    }
    
    
    
}
