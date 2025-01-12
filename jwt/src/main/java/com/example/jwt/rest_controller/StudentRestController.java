package com.example.jwt.rest_controller;

import com.example.jwt.entity.Student;
import com.example.jwt.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentRestController {

    @Autowired
    IStudentRepository studentRepository;

    @GetMapping("/student")
    public ResponseEntity<?> getStudent(@CookieValue("token") String token) {
        System.out.printf(token);
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }
}
