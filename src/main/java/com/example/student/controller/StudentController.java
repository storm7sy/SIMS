package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.serivce.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

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
        model.addAttribute("student", studentService.getStudentById(id));
        return "score";
    }
}
