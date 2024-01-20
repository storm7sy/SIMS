package com.example.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Accessors(chain = true)
@TableName("score")
public class Score {
    @TableId(type = IdType.AUTO)
    private Long id;//主键
    private String classCode;//课程编码
    private String score;//分数
    private Date examTime;//考试时间
    private String studentNumber;
    @TableField(exist = false)
    private String name;//名称
}
