package com.linktreeclone.api.security.jwt;

import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linktreeclone.api.payload.output.ApiResult;
import com.linktreeclone.api.payload.output.Error;
import com.linktreeclone.api.payload.output.UserInfoWithJwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("authEntryPointJwt")
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    
    private Error createErrorReponse(AuthenticationException authException) {
        Error errorResponse = new Error();
        if (authException instanceof BadCredentialsException) {
            errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            errorResponse.setErrorCode("404");
            errorResponse.setDetails("Invalid user credentials!");
            errorResponse.setMessage("Invalid user credentials!");
        } else {
            errorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            errorResponse.setErrorCode("401");
            errorResponse.setDetails("Full authentication is required to access resources");
            errorResponse.setMessage("User is unauthenticated!");
        }
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        try {
            ApiResult<UserInfoWithJwt> res = new ApiResult<UserInfoWithJwt>(null, createErrorReponse(authException));
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
            mapper.writeValue(response.getOutputStream(), res);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
}
