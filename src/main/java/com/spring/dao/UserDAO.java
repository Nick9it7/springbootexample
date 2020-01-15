package com.spring.dao;

import com.spring.model.User;

public interface UserDAO {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByUsername(String username);

    void add(User user);
}
