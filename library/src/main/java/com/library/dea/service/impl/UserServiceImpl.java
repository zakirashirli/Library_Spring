package com.library.dea.service.impl;

import com.library.dea.dto.RegisterForm;
import com.library.dea.dto.UserManagementDTO;
import com.library.dea.entity.User;
import com.library.dea.exception.PasswordMismatchException;
import com.library.dea.exception.UserAlreadyExistsException;
import com.library.dea.exception.UserNotFoundException;
import com.library.dea.repository.UserRepository;
import com.library.dea.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterForm form) {
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new UserAlreadyExistsException("auth.error.usernameExists");
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new PasswordMismatchException("auth.error.passwordMismatch");
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Override
    public List<UserManagementDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public UserManagementDTO getUserById(Integer id) {
        return toDto(findUser(id));
    }

    @Override
    public void createUser(UserManagementDTO form) {
        validateUserForm(form, null);
        userRepository.save(toEntity(form, new User()));
    }

    @Override
    public void updateUser(Integer id, UserManagementDTO form) {
        User existing = findUser(id);
        validateUserForm(form, id);
        userRepository.save(toEntity(form, existing));
    }

    @Override
    public void deleteUser(Integer id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    private void validateUserForm(UserManagementDTO form, Integer currentId) {
        User existingByUsername = userRepository.findByUsername(form.getUsername()).orElse(null);
        if (existingByUsername != null && !existingByUsername.getId().equals(currentId)) {
            throw new UserAlreadyExistsException("auth.error.usernameExists");
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new PasswordMismatchException("auth.error.passwordMismatch");
        }
    }

    private User findUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user.error.notFound"));
    }

    private UserManagementDTO toDto(User user) {
        UserManagementDTO dto = new UserManagementDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword("Password1!");
        dto.setConfirmPassword("Password1!");
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        return dto;
    }

    private User toEntity(UserManagementDTO form, User user) {
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(form.getRole());
        user.setEnabled(Boolean.TRUE.equals(form.getEnabled()));
        return user;
    }
}
