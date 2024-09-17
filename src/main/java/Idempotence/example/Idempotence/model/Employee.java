package Idempotence.example.Idempotence.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "Employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="FirstName", nullable=false)
    private String firstName;
    @Column(name="LastName", nullable=false)
    private String lastName;
    @Column(name="E-Mail")
    private String email;
    @Column(name = "Idempotence_key", unique = true)
    private String idempotenceKey;
}
