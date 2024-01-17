package com.example.student.serivce;

import com.example.student.model.Student;
import com.example.student.model.User;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(Student student);

    Student getStudentById(Long id);
}
