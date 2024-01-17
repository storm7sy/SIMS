package com.example.student.serivce;

import com.example.student.model.Classes;
import com.example.student.model.Score;
import com.example.student.model.Student;
import com.example.student.model.User;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> getStudents(Student student);

    Student getStudentById(Long id);

    Student getScore(Long studentId);
}
