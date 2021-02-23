package com.wyl.blog.service;

import com.wyl.blog.dao.UserRepository;
import com.wyl.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {

        User user = userRepository.findByUsernameAndPassword(username,password);

        return user;
    }
}
