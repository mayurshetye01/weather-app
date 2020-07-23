package com.gloresoft.weatherapp.backend.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gloresoft.weatherapp.backend.config.ApplicationParameters;
import com.gloresoft.weatherapp.backend.config.Roles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gloresoft.weatherapp.backend.security.jwt.JwtConstants.SECRET;

/**
 * This class is responsible to check if the request has a valid JWT token when request other than login is performed
 * If token is valid request will be passed further and user will be added in SecurityContext
 */


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private ApplicationParameters applicationParameters;

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        init(req);
        String header = req.getHeader(JwtConstants.HEADER_STRING);

        if (header == null || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private void init(HttpServletRequest req){
        if(applicationParameters==null){
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            applicationParameters = webApplicationContext.getBean(ApplicationParameters.class);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtConstants.HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JwtConstants.TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));
            }
            return null;
        }
        return null;
    }

    // To be improved. As of now only adding ADMIN role if username is admin username
    private List<GrantedAuthority> getAuthorities(String user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (applicationParameters.getAdminUserName().equals(user))
            authorities.add(new SimpleGrantedAuthority(Roles.ADMIN.value()));
        return authorities;
    }
}