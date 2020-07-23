package com.gloresoft.weatherapp.backend.security.jwt;

// To do - Move this to config
public class JwtConstants {
    // 10 mins
    public static final long EXPIRATION_TIME = 600000;
    public static final String SECRET = "secret";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
