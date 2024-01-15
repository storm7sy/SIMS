package com.example.student.serivce;

import com.example.student.model.User;

public interface UserService {
    void register(User user);
    User login(String username, String password);
}
