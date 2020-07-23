package com.gloresoft.weatherapp.backend.services;

import com.gloresoft.weatherapp.backend.dto.UserDto;

public interface UserService {
    void saveUser(UserDto user);

    UserDto getUser(String username);

}
