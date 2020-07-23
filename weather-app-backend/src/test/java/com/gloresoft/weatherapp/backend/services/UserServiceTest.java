package com.gloresoft.weatherapp.backend.services;

import com.gloresoft.weatherapp.backend.converters.UserConverter;
import com.gloresoft.weatherapp.backend.dto.UserDto;
import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.repository.UserRepository;
import com.gloresoft.weatherapp.backend.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Spy
    UserConverter userConverter = new UserConverter(mock(BCryptPasswordEncoder.class));

    private final String testUsername = "test@test.com";
    private final String testPassword = "test";
    private final Date testDob = new Date();

    @Test
    public void saveUserTest() {
        UserDto testUser = new UserDto();
        testUser.setUsername(testUsername);
        testUser.setPassword(testPassword);
        testUser.setDateOfBirth(testDob);

        userService.saveUser(testUser);

        verify(userRepository, Mockito.times(1)).save(any(User.class));

    }

    @Test
    public void getUserTest() {
        User testUser = new User();
        testUser.setUsername(testUsername);
        testUser.setPassword(testPassword);
        testUser.setDateOfBirth(testDob);

        when(userRepository.findUserByUsername(testUsername)).thenReturn(testUser);

        UserDto result = userService.getUser(testUsername);

        verify(userRepository, Mockito.times(1)).findUserByUsername(testUsername);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(testUser.getPassword(), result.getPassword());

    }

}
