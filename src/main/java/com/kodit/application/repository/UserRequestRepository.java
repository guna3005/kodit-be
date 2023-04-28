package com.kodit.application.repository;

import com.kodit.application.model.User;
import com.kodit.application.model.UserRequest;
import org.springframework.data.repository.CrudRepository;

public interface UserRequestRepository extends CrudRepository<UserRequest,Long> {

    Boolean existsByRequestedToAndRequestedBy(User requestedTo, User requestedBy);
}
