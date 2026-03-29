package com.library.dea.service;

import com.library.dea.dto.RegisterForm;
import com.library.dea.dto.UserManagementDTO;

import java.util.List;

public interface UserService {
    void register(RegisterForm form);

    List<UserManagementDTO> getAllUsers();

    UserManagementDTO getUserById(Integer id);

    void createUser(UserManagementDTO user);

    void updateUser(Integer id, UserManagementDTO user);

    void deleteUser(Integer id);
}
