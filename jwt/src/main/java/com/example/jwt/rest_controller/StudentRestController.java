package com.example.jwt.rest_controller;

import com.example.jwt.entity.Student;
import com.example.jwt.repository.IStudentRepository;
import com.example.jwt.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentRestController {

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/student")
    public ResponseEntity<?> getStudent(@CookieValue("token") String token) {
        System.out.println(token);
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/student-token")
    public ResponseEntity<?> getStudents(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("token:"+token);
        token = token.substring("Bearer ".length());
        if (jwtUtils.isJwt(token)){
            List<Student> students = studentRepository.findAll();
            return ResponseEntity.ok(students);
        }
        return ResponseEntity.ok("fail");
//        List<Student> students = studentRepository.findAll();
//        return ResponseEntity.ok(students);
    }
}
