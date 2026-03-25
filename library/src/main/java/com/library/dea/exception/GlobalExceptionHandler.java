package com.library.dea.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFound(BookNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/username-error";
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public String handleAuthorNotFound(AuthorNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/author-error";
    }

    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public String handleAuthorAlreadyExists(AuthorAlreadyExistsException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/author-error";
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public String handlePasswordMismatch(PasswordMismatchException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/password-mismatch-error";
    }

}
