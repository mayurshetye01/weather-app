package com.gloresoft.weatherapp.backend.services.impl;

import com.gloresoft.weatherapp.backend.converters.UserConverter;
import com.gloresoft.weatherapp.backend.dto.UserDto;
import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.repository.UserRepository;
import com.gloresoft.weatherapp.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public void saveUser(UserDto userDto) {
        userRepository.save(userConverter.convert(userDto));
    }

    @Override
    public UserDto getUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null)
            throw new NoSuchElementException("User with username " + username + " does not exist");
        return userConverter.convert(user);
    }
}
