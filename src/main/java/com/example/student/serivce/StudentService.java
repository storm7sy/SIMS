package com.example.student.serivce;

import com.example.student.model.Classes;
import com.example.student.model.Score;
import com.example.student.model.Student;
import com.example.student.model.User;
import com.example.student.model.common.Result;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> getStudents(Student student);

    Student getStudentById(Long id);

    Student getScore(Long studentId);

    Result handleFileUpload(MultipartFile file);
}
