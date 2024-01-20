package com.example.student.model.common;

import lombok.Data;

@Data
public class Result {
    public Integer code;
    public String message;
    public boolean success;
    public Object data;

    public Result() {
    }

    public Result(Integer code, String message, boolean success, Object data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }
    public Result(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }
    public Result(String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }
    public Result(String message) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }
}
