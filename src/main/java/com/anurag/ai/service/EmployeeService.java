package com.anurag.ai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anurag.ai.dto.EmployeeDto;
import com.anurag.ai.dto.LoginResponseDTO;
import com.anurag.ai.entity.Employee;
import com.anurag.ai.repository.EmployeeRepository;

@Service
public class EmployeeService {

      @Autowired
      private EmployeeRepository repo;

      @Autowired
      private PasswordEncoder encoder;

      @Autowired
      private JwtService JService;

      public List<Employee> getAllEmployees() {
            return repo.findAll();
      }

      public Employee createEmployee(EmployeeDto dto) {
            Employee employee = new Employee();
            employee.setName(dto.getName());
            employee.setRole(dto.getRole());
            employee.setEmail(dto.getEmail());
            employee.setPassword(
                        encoder.encode(dto.getPassword()));
            return repo.save(employee);
            // INSERT INTO employee() VALUES()
      }

      public Employee getEmployeeById(Long id) {
            return repo.findById(id).orElse(null);
      }

      public Employee updateEmployee(Long id, Employee employee) {

            Employee existingEmployee = repo.findById(id).orElse(null);

            if (existingEmployee != null) {

                  existingEmployee.setName(employee.getName());
                  existingEmployee.setEmail(employee.getEmail());

                  // Encrypt password before updating
                  existingEmployee.setPassword(
                              encoder.encode(employee.getPassword()));

                  existingEmployee.setRole(employee.getRole());

                  return repo.save(existingEmployee);
            }

            return null;
      }

      public String deleteEmployee(Long id) {

            if (repo.existsById(id)) {

                  repo.deleteById(id);

                  return "Employee deleted successfully";
            }

            return "Employee not found";
      }

      public ResponseEntity<?> login(Employee emp) {
            // Find employee using email
            Employee existingEmp = repo.findByEmail(emp.getEmail());

            if (existingEmp == null) {
                  return ResponseEntity
                              .status(HttpStatus.UNAUTHORIZED)
                              .body("Email is Wrong..");
            }

            // Compare entered password with encrypted password
            if (!encoder.matches(
                        emp.getPassword(),
                        existingEmp.getPassword())) {

                  return ResponseEntity
                              .status(HttpStatus.UNAUTHORIZED)
                              .body("Password is Wrong");
            }
            // Generate JWT token
            String token = JService.generateToken(
                        existingEmp.getEmail());
            // Send database employee details in response
            LoginResponseDTO dto = new LoginResponseDTO(
                        token,
                        existingEmp.getId(),
                        existingEmp.getEmail(),
                        existingEmp.getRole().name());

            return ResponseEntity.ok(dto);
      }
}