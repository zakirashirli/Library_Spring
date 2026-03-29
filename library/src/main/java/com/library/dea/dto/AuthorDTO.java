package com.library.dea.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorDTO {
    private Long id;
    private int bookCount;

    @NotBlank(message = "{author.validation.required}")
    @Size(min = 3, max = 100, message = "{author.validation.size}")
    private String name;

    public AuthorDTO() {

    }

    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorDTO(Long id, String name, int bookCount) {
        this.id = id;
        this.name = name;
        this.bookCount = bookCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
}
