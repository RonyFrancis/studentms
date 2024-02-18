package com.example.studentms.repo;

import com.example.studentms.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Integer> {
}
