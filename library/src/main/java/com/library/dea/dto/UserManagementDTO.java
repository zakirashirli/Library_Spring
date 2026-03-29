package com.library.dea.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserManagementDTO {
    private Integer id;

    @NotBlank(message = "{auth.username.required}")
    @Size(min = 4, max = 50, message = "{auth.username.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "{auth.username.pattern}")
    private String username;

    @NotBlank(message = "{auth.password.required}")
    @Size(min = 8, max = 50, message = "{auth.password.size}")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
            message = "{auth.password.pattern}"
    )
    private String password;

    @NotBlank(message = "{auth.confirmPassword.required}")
    private String confirmPassword;

    @NotBlank(message = "{user.role.required}")
    private String role;

    @NotNull(message = "{user.enabled.required}")
    private Boolean enabled = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
