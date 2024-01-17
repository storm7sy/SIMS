package com.example.student.controller;

import com.example.student.model.Classes;
import com.example.student.model.Score;
import com.example.student.model.Student;
import com.example.student.serivce.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;


    @PostMapping("/queryStudents")
    public String queryStudents(Student student, HttpSession session) {
        session.setAttribute("studentList", studentService.getStudents(student));
        return "redirect:/success";
    }
    @GetMapping("/showScoreCurve")
    public String showScoreCurve(Long id, Model model) {
        Student student = studentService.getScore(id);
        model.addAttribute("student", student);
        return "score";
    }
}
