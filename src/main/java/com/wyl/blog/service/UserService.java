package com.wyl.blog.service;

import com.wyl.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
