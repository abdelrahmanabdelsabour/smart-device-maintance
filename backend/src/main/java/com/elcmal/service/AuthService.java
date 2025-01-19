package com.elcmal.service;

import com.elcmal.exception.AuthenticationException;
import com.elcmal.model.Role;
import com.elcmal.model.User;
import com.elcmal.payload.request.LoginRequest;
import com.elcmal.payload.request.SignupRequest;
import com.elcmal.payload.response.JwtResponse;
import com.elcmal.repository.RoleRepository;
import com.elcmal.repository.UserRepository;
import com.elcmal.security.jwt.JwtUtils;
import com.elcmal.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        logger.debug("Attempting to authenticate user: {}", loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            logger.info("User authenticated successfully: {}", loginRequest.getUsername());
            return new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", loginRequest.getUsername());
            throw new AuthenticationException("Invalid username or password", e);
        } catch (Exception e) {
            logger.error("Unexpected error during authentication for user: {}", loginRequest.getUsername(), e);
            throw new AuthenticationException("Authentication failed", e);
        }
    }

    @Transactional
    public void registerUser(SignupRequest signUpRequest) {
        logger.debug("Attempting to register new user: {}", signUpRequest.getUsername());

        // Validate username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            logger.warn("Registration failed - Username already exists: {}", signUpRequest.getUsername());
            throw new AuthenticationException("Username is already taken!");
        }

        // Validate email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            logger.warn("Registration failed - Email already exists: {}", signUpRequest.getEmail());
            throw new AuthenticationException("Email is already in use!");
        }

        try {
            // Create new user
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            Set<String> strRoles = signUpRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null || strRoles.isEmpty()) {
                Role userRole = roleRepository.findByName(Role.ERole.ROLE_USER)
                        .orElseThrow(() -> {
                            logger.error("User Role not found in database");
                            return new RuntimeException("Error: User Role not found.");
                        });
                roles.add(userRole);
                logger.debug("Assigning default USER role to: {}", signUpRequest.getUsername());
            } else {
                strRoles.forEach(role -> {
                    logger.debug("Processing role assignment: {} for user: {}", role, signUpRequest.getUsername());
                    switch (role.toLowerCase()) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(Role.ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> {
                                        logger.error("Admin Role not found in database");
                                        return new RuntimeException("Error: Admin Role not found.");
                                    });
                            roles.add(adminRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName(Role.ERole.ROLE_USER)
                                    .orElseThrow(() -> {
                                        logger.error("User Role not found in database");
                                        return new RuntimeException("Error: User Role not found.");
                                    });
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            userRepository.save(user);
            logger.info("User registered successfully: {}", signUpRequest.getUsername());
            
        } catch (Exception e) {
            logger.error("Error during user registration: {}", signUpRequest.getUsername(), e);
            throw new AuthenticationException("Error during registration", e);
        }
    }
}
