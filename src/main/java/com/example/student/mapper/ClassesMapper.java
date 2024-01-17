package com.example.student.mapper;// ClassesMapper.java

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student.model.Classes;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClassesMapper extends BaseMapper<Classes> {

    @Select("SELECT c.*, s.id AS scoreId, s.score, DATE_FORMAT(s.exam_time, '%Y-%m-%d')as exam_time  FROM class c " +
            "LEFT JOIN score s ON c.class_code = s.class_code " +
            "WHERE c.id = #{id}")
    @Results({
            @Result(property = "scores", column = "scoreId",
                    many = @Many(select = "com.example.student.mapper.ScoreMapper.selectById"))
    })
    List<Classes> selectClassesWithScoresByStudentNumber(Long id);
}
