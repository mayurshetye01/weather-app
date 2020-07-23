package com.gloresoft.weatherapp.backend.repository;


import com.gloresoft.weatherapp.backend.entities.User;

public interface UserRepository {
    void save(User user);

    User findUserByUsername(String username);
}
