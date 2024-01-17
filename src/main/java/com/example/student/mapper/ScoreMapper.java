package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student.model.Score;
import com.example.student.model.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScoreMapper extends BaseMapper<Score> {
    @Select("SELECT * FROM score WHERE id = #{id}")
    Score selectById(Long id);
}
