package com.kodit.application.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegistrationRequest {

	@NotEmpty(message = "name cannot be empty")
	private String name;

	@Email(message = "Please enter a valid email address")
	@NotEmpty(message = "email cannot be empty")
	private String email;

	@NotEmpty(message = "username cannot be empty")
	private String username;

	@NotEmpty(message = "password cannot be empty")
	private String password;

}
