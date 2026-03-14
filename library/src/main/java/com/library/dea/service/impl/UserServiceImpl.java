package com.library.dea.service.impl;

import com.library.dea.dto.RegisterForm;
import com.library.dea.entity.User;
import com.library.dea.repository.UserRepository;
import com.library.dea.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register user
    @Override
    public void register(RegisterForm form) {
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new RuntimeException("Пароли не совпадают!");
        }
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }
}
