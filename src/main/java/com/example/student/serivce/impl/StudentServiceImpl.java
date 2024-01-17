package com.example.student.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.student.mapper.StudentMapper;
import com.example.student.mapper.UserMapper;
import com.example.student.model.Student;
import com.example.student.model.User;
import com.example.student.serivce.StudentService;
import com.example.student.serivce.UserService;
import com.example.student.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

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
}
