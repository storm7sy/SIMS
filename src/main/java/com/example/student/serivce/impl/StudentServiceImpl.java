package com.example.student.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.student.mapper.ClassesMapper;
import com.example.student.mapper.StudentMapper;
import com.example.student.mapper.UserMapper;
import com.example.student.model.Classes;
import com.example.student.model.Score;
import com.example.student.model.Student;
import com.example.student.model.User;
import com.example.student.serivce.StudentService;
import com.example.student.serivce.UserService;
import com.example.student.utils.EmptyUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassesMapper classesMapper;
    private static final Gson gson = new Gson();
    @Override
    public List<Student> getStudents(Student student) {
        List<Student> list = new ArrayList<>();
        if (EmptyUtils.isNullOrEmpty(student.getName())) {
            return studentMapper.selectList(new LambdaQueryWrapper<Student>());
        } else {
            LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Student::getName, student.getName());
            list.add(studentMapper.selectOne(queryWrapper));
        }
        return list;
    }

    @Override
    public Student getStudentById(Long id) {
        if (!EmptyUtils.isNullOrEmpty(id)) {
            Student student = studentMapper.selectById(id);
            return student;
        }
        return null;
    }

    @Override
    public Student getScore(Long studentId) {
        List<Classes> classes = classesMapper.selectClassesWithScoresByStudentNumber(studentId);
        Student student = studentMapper.selectById(studentId);
        List<Score> scores = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!EmptyUtils.isNull(classes)) {
            for (Classes aClass : classes) {
                scores.addAll(aClass.getScores());
            }
            student.setScores( gson.toJson(scores));
            return student;
        }
        return null;
    }
}
