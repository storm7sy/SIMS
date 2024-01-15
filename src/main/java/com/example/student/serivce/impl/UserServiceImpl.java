package com.example.student.serivce.impl;

import com.example.student.model.User;
import com.example.student.serivce.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
//    @Autowired
//    private UserMapper userMapper;

    @Override
    public void register(User user) {
        user.setId(UUID.randomUUID().toString());
        //userDAO.save(user);
    }

    @Override
    public User login(String username, String password) {
        return new User("root","孙圣尧","root","男");
        //return userDAO.login(username, password);
    }
}
