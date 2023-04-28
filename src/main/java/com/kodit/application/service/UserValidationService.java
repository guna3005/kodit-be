package com.kodit.application.service;

import com.kodit.application.exceptions.RegistrationException;
import com.kodit.application.repository.UserRepository;
import com.kodit.application.security.dto.RegistrationRequest;
import com.kodit.application.utils.ErrorMessageConstants;
import com.kodit.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

	private static final String EMAIL_ALREADY_EXISTS = "email_already_exists";

	private static final String USERNAME_ALREADY_EXISTS = "username_already_exists";

	private final UserRepository userRepository;

	public void validateUser(RegistrationRequest registrationRequest) {

		final String email = registrationRequest.getEmail();
		final String username = registrationRequest.getUsername();

		checkEmail(email);
		checkUsername(username);
	}

	private void checkUsername(String username) {

		final boolean existsByUsername = userRepository.existsByUsername(username);

		if (existsByUsername) {

			log.warn("{} is already being used!", username);
			throw new RegistrationException(ErrorMessageConstants.USERNAME_ALREADY_EXISTS);
		}

	}

	private void checkEmail(String email) {

		final boolean existsByEmail = userRepository.existsByEmail(email);

		if (existsByEmail) {

			log.warn("{} is already being used!", email);

			throw new RegistrationException(ErrorMessageConstants.EMAIL_ALREADY_EXISTS);
		}
	}

}
