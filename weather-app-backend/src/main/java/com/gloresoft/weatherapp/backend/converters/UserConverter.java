package com.gloresoft.weatherapp.backend.converters;

import com.gloresoft.weatherapp.backend.dto.UserDto;
import com.gloresoft.weatherapp.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserConverter(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    public User convert(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setDateOfBirth(userDto.getDateOfBirth());

        return user;
    }

    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        // Password returned will be in encoded format
        userDto.setPassword(user.getPassword());
        userDto.setDateOfBirth(user.getDateOfBirth());

        return userDto;
    }
}
