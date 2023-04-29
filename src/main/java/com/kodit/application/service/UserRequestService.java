package com.kodit.application.service;

import com.kodit.application.exceptions.ApiExceptionResponse;
import com.kodit.application.exceptions.CustomException;
import com.kodit.application.model.RequestStatus;
import com.kodit.application.model.User;
import com.kodit.application.model.UserRequest;
import com.kodit.application.repository.UserRequestRepository;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.utils.ErrorMessageConstants;
import com.kodit.application.utils.SuccessMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRequestService {
    private final UserService userService;
    private final UserRequestRepository userRequestRepository;

    public ResponseEntity<ResponseWrapper> addUserRequest(Long userId, String username) {
        User currentUser = userService.findUserByUsername(username);
        log.info("User with username: {} found", username);
        log.info("User with id: {} is requesting to add user with id: {}", currentUser.getId(), userId);
        User userToRequest = userService.findUserById(userId);
        log.info("User with id: {} found", userId);
        if (userToRequest.equals(currentUser)) {
            log.error("User with id: {} cannot request to add himself", currentUser.getId());
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.SELF_REQUEST,
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (userRequestRepository.existsByRequestedToAndRequestedByAndStatus(userToRequest, currentUser, RequestStatus.PENDING)) {
            log.error("User with id: {} already requested to add user with id: {}", currentUser.getId(), userId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.ALREADY_REQUESTED,
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (userRequestRepository.existsByRequestedToAndRequestedByAndStatus(currentUser, userToRequest, RequestStatus.PENDING)) {
            log.error("User with id: {} already requested to add user with id: {}", userId, currentUser.getId());
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.ALREADY_REQUESTED_TO_YOU,
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (userRequestRepository.existsByRequestedToAndRequestedByAndStatus(currentUser, userToRequest, RequestStatus.ACCEPTED)
                || userRequestRepository.existsByRequestedToAndRequestedByAndStatus(userToRequest, currentUser, RequestStatus.ACCEPTED)) {
            log.error("User with id: {} already added user with id: {}", currentUser.getId(), userId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.ALREADY_FRIENDS,
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }

        UserRequest userRequest = UserRequest.builder()
                .requestedBy(currentUser)
                .requestedTo(userToRequest)
                .requestedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(RequestStatus.PENDING)
                .build();
        userRequestRepository.save(userRequest);
        log.info("User with id: {} requested to add user with id: {}", currentUser.getId(), userId);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.REQUEST_SENT));
    }

    public ResponseEntity<ResponseWrapper> acceptUserRequest(Long userRequestId, String username) {
        User currentUser = userService.findUserByUsername(username);
        log.info("User with username: {} found", username);
        UserRequest userRequest = userRequestRepository.findById(userRequestId).orElseThrow(() -> new CustomException(
                new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_FOUND,
                        HttpStatus.BAD_REQUEST, LocalDateTime.now())));
        if (!userRequest.getRequestedTo().equals(currentUser)) {
            log.error("User with id: {} cannot accept request with id: {}", currentUser.getId(), userRequestId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_FOUND,
                    HttpStatus.UNAUTHORIZED, LocalDateTime.now()));
        }
        if (!userRequest.getStatus().equals(RequestStatus.PENDING)) {
            log.error("User with id: {} cannot accept request with id: {}", currentUser.getId(), userRequestId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_PENDING,
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        userRequest.setStatus(RequestStatus.ACCEPTED);
        userRequest.setUpdatedAt(LocalDateTime.now());
        userRequestRepository.save(userRequest);

        currentUser.addFriend(userRequest.getRequestedBy());
        userRequest.getRequestedBy().addFriend(currentUser);
        userService.saveUser(currentUser);
        userService.saveUser(userRequest.getRequestedBy());

        log.info("User with id: {} accepted request with id: {}", currentUser.getId(), userRequestId);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.REQUEST_ACCEPTED));
    }

    public ResponseEntity<ResponseWrapper> rejectUserRequest(Long userRequestId, String username) {
        User currentUser = userService.findUserByUsername(username);
        log.info("User with username: {} found", username);
        UserRequest userRequest = userRequestRepository.findById(userRequestId).orElseThrow(() -> new CustomException(
                new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_FOUND,
                        HttpStatus.BAD_REQUEST, LocalDateTime.now())));
        if (!userRequest.getRequestedTo().equals(currentUser)) {
            log.error("User with id: {} cannot reject request with id: {}", currentUser.getId(), userRequestId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_FOUND,
                    HttpStatus.UNAUTHORIZED, LocalDateTime.now()));
        }
        if (!userRequest.getStatus().equals(RequestStatus.PENDING)) {
            log.error("User with id: {} cannot accept request with id: {}", currentUser.getId(), userRequestId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_PENDING,
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        userRequest.setStatus(RequestStatus.REJECTED);
        userRequest.setUpdatedAt(LocalDateTime.now());
        userRequestRepository.save(userRequest);
        log.info("User with id: {} rejected request with id: {}", currentUser.getId(), userRequestId);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.REQUEST_REJECTED));
    }

    public ResponseEntity<ResponseWrapper> cancelUserRequest(Long userRequestId, String username) {
        User currentUser = userService.findUserByUsername(username);
        log.info("User with username: {} found", username);
        UserRequest userRequest = userRequestRepository.findById(userRequestId).orElseThrow(() -> new CustomException(
                new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_FOUND,
                        HttpStatus.BAD_REQUEST, LocalDateTime.now())));
        if (!userRequest.getRequestedBy().equals(currentUser)) {
            log.error("User with id: {} cannot cancel request with id: {}", currentUser.getId(), userRequestId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_FOUND,
                    HttpStatus.UNAUTHORIZED, LocalDateTime.now()));
        }
        if (!userRequest.getStatus().equals(RequestStatus.PENDING)) {
            log.error("User with id: {} cannot accept request with id: {}", currentUser.getId(), userRequestId);
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.REQUEST_NOT_PENDING,
                    HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        userRequest.setStatus(RequestStatus.CANCELED);
        userRequest.setUpdatedAt(LocalDateTime.now());
        userRequestRepository.save(userRequest);
        log.info("User with id: {} canceled request with id: {}", currentUser.getId(), userRequestId);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.REQUEST_CANCELED));
    }
}
