package com.cao.dao;

import com.cao.POJO.User;

public interface UserDao {
    public User findByUsername(String username);
}
