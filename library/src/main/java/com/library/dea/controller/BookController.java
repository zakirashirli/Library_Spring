package com.library.dea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }
}
