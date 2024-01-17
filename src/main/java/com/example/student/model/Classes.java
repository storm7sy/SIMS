package com.example.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ToString
@Accessors(chain = true)
@TableName("class")
public class Classes {
    @TableId(type = IdType.AUTO)
    private Long id; //主键
    private String studentNumber; //学号
    private String className;//课程名称
    private String classCode;//课程编码
    private String grade; //年级
    @TableField(exist = false)
    List<Score> scores;//成绩
}
