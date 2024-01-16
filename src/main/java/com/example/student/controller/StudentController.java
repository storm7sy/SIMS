package com.example.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.student.mapper.StudentMapper;
import com.example.student.model.Student;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;


    @PostMapping("/queryStudents")
    public String queryStudents(Student student, HttpSession session) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getName, student.getName());
        student = studentMapper.selectOne(queryWrapper);
        session.setAttribute("studentList", student);
        return "redirect:/success";
    }


}
