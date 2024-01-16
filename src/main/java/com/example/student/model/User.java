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
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String realname;
    private String password;
    private String sex;

    public User() {
    }

    public User(String username, String realname, String password, String sex) {
        this.username = username;
        this.realname = realname;
        this.password = password;
        this.sex = sex;
    }
}

