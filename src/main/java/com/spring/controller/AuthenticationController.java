package com.spring.controller;

import com.spring.dao.UserDAO;
import com.spring.exception.DAOException;
import com.spring.util.JwtTokenUtil;
import com.spring.model.User;
import com.spring.payload.ApiResponse;
import com.spring.payload.LoginResponse;
import com.spring.payload.LoginRequest;
import com.spring.payload.SignUpRequest;
import com.spring.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ExceptionHandler(DAOException.class)
    public final ResponseEntity<Object> handleDAOException(DAOException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Incorrect username ot password"),
                    HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> create(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userDAO.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userDAO.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDAO.add(user);

        return new ResponseEntity<>(new ApiResponse(true, "User registered successfully"),
                HttpStatus.CREATED);
    }
}
