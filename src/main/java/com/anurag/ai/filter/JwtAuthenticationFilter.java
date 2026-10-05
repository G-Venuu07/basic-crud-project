package com.anurag.ai.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.anurag.ai.entity.Employee;
import com.anurag.ai.repository.EmployeeRepository;
import com.anurag.ai.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
      @Autowired
      private JwtService jservice;

      @Autowired
      private EmployeeRepository repo;

      @Override
      protected void doFilterInternal(HttpServletRequest request,
                  HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                  String token = authHeader.substring(7);
                  if (jservice.validateToken(token)) {
                        String email = jservice.extractUsername(token);
                        Employee emp = repo.findByEmail(email);
                        if (emp != null) {
                              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                          emp,
                                          null,
                                          List.of(new SimpleGrantedAuthority("ROLE_" + emp.getRole())));

                              SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                  }
            }
            filterChain.doFilter(request, response);
      }
}
