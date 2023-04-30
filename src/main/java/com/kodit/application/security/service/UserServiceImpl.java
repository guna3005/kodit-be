package com.kodit.application.security.service;

import com.kodit.application.model.User;
import com.kodit.application.model.UserRole;
import com.kodit.application.repository.UserRepository;
import com.kodit.application.security.dto.AuthenticatedUserDto;
import com.kodit.application.security.dto.RegistrationRequest;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.security.mapper.UserMapper;
import com.kodit.application.service.UserValidationService;
import com.kodit.application.utils.SuccessMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserValidationService userValidationService;

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseWrapper registration(RegistrationRequest registrationRequest) {

        userValidationService.validateUser(registrationRequest);

        final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.USER);

        userRepository.save(user);

        final String username = registrationRequest.getUsername();
        log.info("{} registered successfully!", username);

        return new ResponseWrapper(SuccessMessageConstants.REGISTRATION_SUCCESSFUL);
    }

    @Override
    public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

        final User user = findByUsername(username);

        return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
    }
}
