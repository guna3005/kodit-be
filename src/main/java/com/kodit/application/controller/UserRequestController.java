package com.kodit.application.controller;

import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.service.UserRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/userRequests")
@RequiredArgsConstructor
public class UserRequestController {

    private final UserRequestService userRequestService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ResponseWrapper> addUserRequest(@PathVariable Long userId, Principal principal){
        return userRequestService.addUserRequest(userId, principal.getName());
    }
    @PutMapping("/accept/{userRequestId}")
    public ResponseEntity<ResponseWrapper> acceptUserRequest(@PathVariable Long userRequestId, Principal principal){
        return userRequestService.acceptUserRequest(userRequestId, principal.getName());
    }
    @PutMapping("/reject/{userRequestId}")
    public ResponseEntity<ResponseWrapper> rejectUserRequest(@PathVariable Long userRequestId, Principal principal){
        return userRequestService.rejectUserRequest(userRequestId, principal.getName());
    }
    @PutMapping("/cancel/{userRequestId}")
    public ResponseEntity<ResponseWrapper> cancelUserRequest(@PathVariable Long userRequestId, Principal principal){
        return userRequestService.cancelUserRequest(userRequestId, principal.getName());
    }


}
