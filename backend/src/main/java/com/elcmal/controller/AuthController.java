package com.elcmal.controller;

import com.elcmal.payload.request.LoginRequest;
import com.elcmal.payload.request.SignupRequest;
import com.elcmal.payload.response.MessageResponse;
import com.elcmal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping(value = "/api/auth", produces = "application/json")
public class AuthController {

    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.authenticateUser(loginRequest));
        } catch (Exception e) {
            String[] msgpAram={};
            String msg=messageSource.getMessage("validation.errorinvalidusernameorpassword.message",msgpAram, LocaleContextHolder.getLocale());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(msg));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authService.registerUser(signUpRequest);
//            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            String[] msgpAram={};
            String msg=messageSource.getMessage("validation.succefullyrecord.message",msgpAram, LocaleContextHolder.getLocale());
            return ResponseEntity.ok(new MessageResponse(msg));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}
