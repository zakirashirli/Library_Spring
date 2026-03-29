package com.library.dea.controller;

import com.library.dea.dto.UserManagementDTO;
import com.library.dea.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserPageController {

    private final UserService userService;

    public AdminUserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        UserManagementDTO user = new UserManagementDTO();
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        model.addAttribute("user", user);
        return "users/new";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") UserManagementDTO user,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        userService.createUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("user") UserManagementDTO user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "users/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
