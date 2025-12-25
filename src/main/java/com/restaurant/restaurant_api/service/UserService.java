package com.restaurant.restaurant_api.service;

import com.restaurant.restaurant_api.user.User;
import com.restaurant.restaurant_api.user.UserRepository;
import com.restaurant.restaurant_api.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service bean for user-related operations. Marked with @Service so Spring will
 * create a bean and manage dependency injection.
 *
 * Comment: Beans are objects managed by Spring. Use @Autowired (or constructor
 * injection) to get them injected where needed.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String rawPassword) {
        User u = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .roles(Set.of(Role.ROLE_USER))
                .build();
        return userRepository.save(u);
    }
}
