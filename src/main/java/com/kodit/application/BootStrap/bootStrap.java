package com.kodit.application.BootStrap;

import com.kodit.application.configuration.PasswordEncoderConfiguration;
import com.kodit.application.model.User;
import com.kodit.application.model.UserRole;
import com.kodit.application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
public class bootStrap implements CommandLineRunner {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder().userRole(UserRole.USER).email("hello@hello.com").password(bCryptPasswordEncoder.encode("helloworld")).name("hello").build();
        User savedUser = userRepository.save(user1);
    }


}
