package com.library.dea.controller;

import com.library.dea.dto.AuthorDTO;
import com.library.dea.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/authors")
public class AuthorPageController {

    private final AuthorService authorService;

    public AuthorPageController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());

        return "authors/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("author", new AuthorDTO());

        return "authors/new";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AuthorDTO authorDTO) {
        authorService.createAuthor(authorDTO);

        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));

        return "authors/edit";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute AuthorDTO authorDTO
    ) {
        authorService.updateAuthor(id, authorDTO);

        return "redirect:/authors" ;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

}
