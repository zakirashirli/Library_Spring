package com.library.dea.controller;

import com.library.dea.dto.RegisterForm;
import com.library.dea.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "Auth operations")
public class AuthRestController {
    private final UserService userService;

    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered!"),
            @ApiResponse(responseCode = "400", description = "Validation error!"),
            @ApiResponse(responseCode = "409", description = "User already exists!")
    })
    public ResponseEntity<String> register(@Valid @RequestBody RegisterForm form) {
        userService.register(form);

        return ResponseEntity.ok("User registered successfully");
    }
}
