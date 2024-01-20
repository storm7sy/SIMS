package com.example.student.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.student.mapper.ClassesMapper;
import com.example.student.mapper.ScoreMapper;
import com.example.student.mapper.StudentMapper;
import com.example.student.mapper.UserMapper;
import com.example.student.model.Classes;
import com.example.student.model.Score;
import com.example.student.model.Student;
import com.example.student.model.User;
import com.example.student.model.common.Result;
import com.example.student.serivce.StudentService;
import com.example.student.serivce.UserService;
import com.example.student.utils.EmptyUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private ScoreMapper scoreMapper;
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
            student.setScores(gson.toJson(scores));
            return student;
        }
        return null;
    }

    @Override
    public Result handleFileUpload(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 假设只有一个Sheet
            DataFormatter dataFormatter = new DataFormatter();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int loopCount = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (loopCount == 0) { // 第一行是标题，不处理
                    loopCount++;
                    continue;
                }

                Student student = new Student();
                Classes stuClass = new Classes();
                Score score = new Score();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    // 1.学号 2.姓名 3.性别 4.班级 5.课程 6.分数 7.考试日期
                    Row headerRow = sheet.getRow(0); // 假设标题在第一行
                    int cellIndex = cell.getColumnIndex();
                    String headerValue = headerRow.getCell(cellIndex).toString();
                    switch (headerValue) {
                        case "学号":
                            student.setStudentNumber(cell.toString());
                            break;
                        case "姓名":
                            student.setName(cell.toString());
                            break;
                        case "性别":
                            student.setGender(cell.toString());
                            break;
                        case "班级":
                            student.setGrade(cell.toString());
                            break;
                        case "课程":
                            stuClass.setStudentNumber(student.getStudentNumber());
                            stuClass.setGrade(student.getGrade());
                            stuClass.setClassName(cell.toString());
                            switch (cell.toString()) {
                                case "地理":
                                    stuClass.setClassCode("1");
                                    break;
                                case "历史":
                                    stuClass.setClassCode("2");
                                    break;
                                case "政治":
                                    stuClass.setClassCode("3");
                                    break;
                                case "生物":
                                    stuClass.setClassCode("4");
                                    break;
                                case "化学":
                                    stuClass.setClassCode("5");
                                    break;
                                case "物理":
                                    stuClass.setClassCode("6");
                                    break;
                                case "语文":
                                    stuClass.setClassCode("7");
                                    break;
                                case "数学":
                                    stuClass.setClassCode("8");
                                    break;
                                case "英语":
                                    stuClass.setClassCode("9");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "分数":
                            score.setClassCode(stuClass.getClassCode());
                            score.setScore(cell.toString());
                            break;
                        case "考试日期":
                            String formattedDate = dataFormatter.formatCellValue(cell);
                            Date parse = sdf.parse(formattedDate);
                            score.setExamTime(parse);
                            break;
                        default:
                            break;
                    }

                    log.info(cell.toString() + "\t");
                }

                // 保存学生信息
                LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper();
                queryWrapper.eq(Student::getStudentNumber, student.getStudentNumber());
                Student oldStudent = studentMapper.selectOne(queryWrapper);
                if (!EmptyUtils.isNull(oldStudent)) {
                    //判断是否班级是否改变
                    if (!oldStudent.equals(student.getGrade())) {
                        LambdaUpdateWrapper<Student> updateWrapper = new LambdaUpdateWrapper();
                        updateWrapper.eq(Student::getStudentNumber, student.getStudentNumber());
                        studentMapper.update(student, updateWrapper);
                    }
                } else {
                    studentMapper.insert(student);
                }
                //保存课程信息
                LambdaQueryWrapper<Classes> wrapper = new LambdaQueryWrapper();
                wrapper.eq(Classes::getStudentNumber, stuClass.getStudentNumber());
                wrapper.eq(Classes::getClassCode, stuClass.getClassCode());
                Classes classes = classesMapper.selectOne(wrapper);
                if (EmptyUtils.isNull(classes)) {
                    classesMapper.insert(stuClass);
                }

                //保存分数信息
                LambdaQueryWrapper<Score> scoreWrapper = new LambdaQueryWrapper();
                scoreWrapper.eq(Score::getStudentNumber, student.getStudentNumber());
                scoreWrapper.eq(Score::getExamTime, score.getExamTime());
                Score scores = scoreMapper.selectOne(scoreWrapper);
                if (EmptyUtils.isNull(scores)) {
                    score.setStudentNumber(student.getStudentNumber());
                    scoreMapper.insert(score);
                }
                loopCount++;
            }
            return new Result(200, "上传成功", true);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(500, "上传失败", false);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(500, "上传失败", false);
        }
    }
}
