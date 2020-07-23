package com.gloresoft.weatherapp.backend.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloresoft.weatherapp.backend.config.ApplicationParameters;
import com.gloresoft.weatherapp.backend.config.Roles;
import com.gloresoft.weatherapp.backend.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is responsible to check if user is valid when a login is attempted.
 * If user is valid a token will be returned
 */

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private ApplicationParameters applicationParameters;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private void init(HttpServletRequest req) {
        if (applicationParameters == null) {
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            applicationParameters = webApplicationContext.getBean(ApplicationParameters.class);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        init(req);
        try {
            UserDto creds = new ObjectMapper().readValue(req.getInputStream(), UserDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        String username = ((User) auth.getPrincipal()).getUsername();

        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtConstants.SECRET.getBytes()));

        try (PrintWriter out = res.getWriter()) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            out.print(buildResponseBody(token, username));
            out.flush();
        } catch (IOException e) {
            log.error("Exception occured while authenticating request", e);
            throw new RuntimeException(e);
        }

    }

    private String buildResponseBody(String token, String username) throws JsonProcessingException {
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        if (applicationParameters.getAdminUserName().equals(username))
            jwtResponse.setRole(Roles.ADMIN.name());
        return new ObjectMapper().writeValueAsString(jwtResponse);
    }

}