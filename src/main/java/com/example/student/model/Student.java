package com.example.student.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@TableName("student")
public class Student {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String studentNumber;
    private String name;
    private String gender;
    private String grade;
}
