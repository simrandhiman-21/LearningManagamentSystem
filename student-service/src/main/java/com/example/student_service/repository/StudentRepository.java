package com.example.student_service.repository;


import com.example.student_service.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(String userId);
}
