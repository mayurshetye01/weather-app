package com.gloresoft.weatherapp.backend.security.jwt;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String role;
}
