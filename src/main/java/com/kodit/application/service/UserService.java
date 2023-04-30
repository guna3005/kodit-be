package com.kodit.application.service;

import com.kodit.application.dto.UserDto;
import com.kodit.application.exceptions.ApiExceptionResponse;
import com.kodit.application.exceptions.CustomException;
import com.kodit.application.mapper.UserMapper;
import com.kodit.application.model.User;
import com.kodit.application.repository.UserRepository;
import com.kodit.application.utils.ErrorMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ResponseEntity<List<UserDto>> findUsers(String searchText, String username) {
        log.info("Searching for users with search text: {}", searchText);
        User user = findUserByUsername(username);
        //todo later add the role based check
        log.info("User with username: {} found", username);
        List<User> users = userRepository.findAllByNameStartingWithIgnoreCaseOrUsernameStartingWithIgnoreCase(searchText, searchText);
        log.info("Found {} users", users.size());
        return ResponseEntity.ok(userMapper.convertToUserDtoList(users));
    }

    public User findUserById(Long userId) {
        log.info("Searching for user with id: {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(
                new ApiExceptionResponse(ErrorMessageConstants.USER_NOT_FOUND,
                        HttpStatus.BAD_REQUEST, LocalDateTime.now())));
    }

    public User findUserByUsername(String username) {
        log.info("Searching for user with username: {}", username);
        return userRepository.findByUsername(username);
    }

    public void saveUser(User currentUser) {
        log.info("Saving user with username: {}", currentUser.getUsername());
        userRepository.save(currentUser);
    }
}
