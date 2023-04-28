package com.kodit.application.service;

import com.kodit.application.dto.UserDto;
import com.kodit.application.mapper.UserMapper;
import com.kodit.application.model.User;
import com.kodit.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ResponseEntity<List<UserDto>> findUsers(String searchText) {
        log.info("Searching for users with search text: {}", searchText);
        List<User> users = userRepository.findAllByNameStartingWithIgnoreCaseOrUsernameStartingWithIgnoreCase(searchText, searchText);
        log.info("Found {} users", users.size());
        return ResponseEntity.ok(userMapper.convertToUserDtoList(users));
    }
}
