package com.library.dea.controller;

import com.library.dea.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookPageController {
    private final BookService bookService;


    public BookPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping // mvc
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.showAll());
        return "library/list";
    }
}
