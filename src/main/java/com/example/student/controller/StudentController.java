package com.example.student.controller;

import com.example.student.model.Classes;
import com.example.student.model.Score;
import com.example.student.model.Student;
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
            return "redirect:/error";
        }

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 假设只有一个Sheet

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    // 处理单元格内容，可以根据需要进行业务逻辑
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println(); // 换行
            }
            redirectAttributes.addFlashAttribute("message", "上传成功");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "上传失败：" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "上传失败：" + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/success";
    }
}
