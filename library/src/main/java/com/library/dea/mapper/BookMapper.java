package com.library.dea.mapper;

import com.library.dea.dto.BookDTO;
import com.library.dea.entity.Book;

public class BookMapper {
    public static BookDTO toDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setAmount(book.getAmount());
        if (book.getAuthor() != null) {
            bookDTO.setAuthorId(book.getAuthor().getId());
            bookDTO.setAuthorName(book.getAuthor().getName());
        }

        return bookDTO;
    }

    public static Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setAmount(bookDTO.getAmount());


        return book;
    }

}
