package com.library.dea.controller;

import com.library.dea.entity.Book;
import com.library.dea.service.BookService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        List<Book> books = bookService.showAll();
        boolean authenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        boolean isAdmin = authenticated && authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("books", books);
        model.addAttribute("bookCount", books.size());
        model.addAttribute("authenticated", authenticated);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("currentUsername", authenticated ? authentication.getName() : null);

        return "home";
    }
}
