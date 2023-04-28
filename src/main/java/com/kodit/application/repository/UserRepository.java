package com.kodit.application.repository;

import com.kodit.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	List<User> findAllByNameStartingWithIgnoreCaseOrUsernameStartingWithIgnoreCase(String Name, String username);
	List<User> findAllByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(String Name, String username);

}
