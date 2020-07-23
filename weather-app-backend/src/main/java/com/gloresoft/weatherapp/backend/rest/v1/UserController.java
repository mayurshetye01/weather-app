package com.gloresoft.weatherapp.backend.rest.v1;

import com.gloresoft.weatherapp.backend.dto.UserDto;
import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping
    public void addUser(@RequestBody @Valid UserDto user) {
        userService.saveUser(user);
    }

}
