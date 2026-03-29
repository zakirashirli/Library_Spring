package com.library.dea.controller;

import com.library.dea.dto.BookDTO;
import com.library.dea.entity.Author;
import com.library.dea.entity.Book;
import com.library.dea.exception.AuthorNotFoundException;
import com.library.dea.mapper.BookMapper;
import com.library.dea.repository.AuthorRepository;
import com.library.dea.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/books")
public class BookPageController {
    private final BookService bookService;
    private final AuthorRepository authorRepository;

    public BookPageController(BookService bookService, AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }

    // table
    @GetMapping // mvc
    public String showBooks(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(required = false) Long authorId,
                            Model model,
                            Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Page<Book> bookPage;

        if (authorId != null) {
            bookPage = bookService.findByAuthor(authorId, page, size);
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new AuthorNotFoundException("Author not found with ID: " + authorId));
            model.addAttribute("selectedAuthorId", authorId);
            model.addAttribute("selectedAuthorName", author.getName());
            model.addAttribute("keyword", null);
        } else if (keyword != null && !keyword.isBlank()) {
            bookPage = bookService.search(keyword, page, size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("selectedAuthorId", null);
            model.addAttribute("selectedAuthorName", null);
        } else {
            bookPage = bookService.findPaginated(page, size);
            model.addAttribute("keyword", null);
            model.addAttribute("selectedAuthorId", null);
            model.addAttribute("selectedAuthorName", null);
        }



        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("authors", authorRepository.findAll());

        return "library/list";
    }

//    @GetMapping
//    public String list(Model model) {
//        List<BookDTO> books = bookService.showAll()
//                .stream()
//                .map(BookMapper::toDTO)
//                .toList();
//
//        model.addAttribute("books", books);
//        return "library/list";
//    }


    // add form
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorRepository.findAll());
        return "library/new";
    }

    // save
    @PostMapping
    public String save(
            @Valid @ModelAttribute("book") BookDTO bookDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorRepository.findAll());
            return bookDTO.getId() == null ? "library/new" : "library/edit";
        }
        bookService.saveDto(bookDTO);
        return "redirect:/admin/books" ;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Book book = bookService.showById(id);
        model.addAttribute("book", BookMapper.toDTO(book));
        model.addAttribute("authors", authorRepository.findAll());
        return "library/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/search")
    public String searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        return "redirect:/admin/books?keyword=" + keyword + "&page=" + page + "&size=" + size;
    }
}
