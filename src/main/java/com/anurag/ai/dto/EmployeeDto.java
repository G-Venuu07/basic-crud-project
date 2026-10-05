package com.anurag.ai.dto;

import com.anurag.ai.enums.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeDto {

      private Long id;

      @NotBlank(message = "Name is required")
      private String name;

      @Enumerated(EnumType.STRING)
      private Role role;

      @Email(message = "Enter valid Email")
      @NotBlank(message = "Email is required")
      private String email;

      @NotBlank(message = "Password is required")
      @Size(min = 8, message = "Password must contain at least 8 characters")
      private String password;

      public EmployeeDto() {
            // constructor
      }

      public Long getId() {
            return id;
      }

      public String getName() {
            return name;
      }

      public String getEmail() {
            return email;
      }

      public String getPassword() {
            return password;
      }

      public Role getRole() {
            return role;
      }

      public void setId(Long id) {
            this.id = id;
      }

      public void setName(String name) {
            this.name = name;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public void setRole(Role role) {
            this.role = role;
      }
}