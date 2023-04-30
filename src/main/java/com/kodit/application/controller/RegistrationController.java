package com.kodit.application.controller;

import com.kodit.application.security.dto.RegistrationRequest;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest) {

        final ResponseWrapper registrationResponse = userService.registration(registrationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

}
