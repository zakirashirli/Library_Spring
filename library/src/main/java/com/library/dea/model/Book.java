package com.library.dea.model;

import org.springframework.stereotype.Component;

@Component // bean
public class Book {
    private String title;
    private String author;


    Book() {

    }

    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
