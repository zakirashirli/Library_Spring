package com.library.dea.exception;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFound(BookNotFoundException ex, Model model, Locale locale) {
        model.addAttribute("errorMessage", resolveMessage(ex.getMessage(), locale));
        return "error/error";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex, Model model, Locale locale, HttpServletRequest request) {
        model.addAttribute("errorMessage", resolveMessage(ex.getMessage(), locale));
        applyAuthBackLink(model, request, locale);
        return "error/username-error";
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public String handleAuthorNotFound(AuthorNotFoundException ex, Model model, Locale locale) {
        model.addAttribute("errorMessage", resolveMessage(ex.getMessage(), locale));
        return "error/author-error";
    }

    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public String handleAuthorAlreadyExists(AuthorAlreadyExistsException ex, Model model, Locale locale) {
        model.addAttribute("errorMessage", resolveMessage(ex.getMessage(), locale));
        return "error/author-error";
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public String handlePasswordMismatch(PasswordMismatchException ex, Model model, Locale locale, HttpServletRequest request) {
        model.addAttribute("errorMessage", resolveMessage(ex.getMessage(), locale));
        applyAuthBackLink(model, request, locale);
        return "error/password-mismatch-error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model, Locale locale) {
        model.addAttribute("errorMessage", resolveMessage(ex.getMessage(), locale));
        return "error/error";
    }

    private String resolveMessage(String value, Locale locale) {
        if (value == null) {
            return null;
        }
        return messageSource.getMessage(value, null, value, locale);
    }

    private void applyAuthBackLink(Model model, HttpServletRequest request, Locale locale) {
        String uri = request.getRequestURI();
        if (uri != null && uri.startsWith("/admin/users")) {
            model.addAttribute("backUrl", "/admin/users");
            model.addAttribute("backText", messageSource.getMessage("nav.users", null, locale));
            return;
        }

        model.addAttribute("backUrl", "/register");
        model.addAttribute("backText", messageSource.getMessage("button.goToRegister", null, locale));
    }
}
