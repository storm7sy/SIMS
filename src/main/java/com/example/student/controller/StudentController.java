package com.example.student.controller;

import com.example.student.model.Classes;
import com.example.student.model.Score;
import com.example.student.model.Student;
import com.example.student.model.common.Result;
import com.example.student.serivce.StudentService;
import com.sun.media.sound.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
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
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择文件上传");
            return "redirect:/success";
        }
        Result result = studentService.handleFileUpload(file);
        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("message", "上传成功");
            return "redirect:/success";
        } else {
            redirectAttributes.addFlashAttribute("message", "上传失败");
            return "redirect:/success";
        }
    }
}
