package com.anurag.ai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anurag.ai.dto.EmployeeDto;
import com.anurag.ai.entity.Employee;
import com.anurag.ai.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/employee") // localhost:8080/employee
public class EmployeeController {// api
      @Autowired
      private EmployeeService service;// field injection

      @GetMapping("/get")
      public List<Employee> getAllEmployees() {
            return service.getAllEmployees();
      }

      @PostMapping("/login")
      public ResponseEntity<?> login(@RequestBody Employee emp) {
            return service.login(emp);
      }

      @PostMapping("/register")
      public Employee createEmployee(@Valid @RequestBody EmployeeDto employeedto) {
            return service.createEmployee(employeedto);
      }

      // localhost:8080/employee/2
      @GetMapping("/{id}")
      public Employee getEmployeeById(@PathVariable Long id) {
            return service.getEmployeeById(id);
      }

      @PutMapping("/{id}")
      public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
            return service.updateEmployee(id, employee);
      }

      @DeleteMapping("/{id}")
      public String deleteEmployee(@PathVariable Long id) {
            return service.deleteEmployee(id);
      }
}
