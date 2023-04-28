package com.kodit.application.security.service;

import com.kodit.application.model.User;
import com.kodit.application.security.dto.AuthenticatedUserDto;
import com.kodit.application.security.dto.RegistrationRequest;
import com.kodit.application.security.dto.ResponseWrapper;


public interface UserService {

	User findByUsername(String username);

	ResponseWrapper registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
