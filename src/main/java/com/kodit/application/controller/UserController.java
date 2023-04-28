package com.kodit.application.controller;

import com.kodit.application.dto.UserDto;
import com.kodit.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/find")
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam String searchText){
        return userService.findUsers(searchText);
    }
}
