package com.library.dea.service;

import com.library.dea.dto.BookDTO;
import com.library.dea.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    // create method (POST)
    Book add(Book book);

    // show all books (GET)
    List<Book> showAll();

    //pagination
    Page<Book> getBooks(Pageable pageable);

//    // show book by title
//    List<Book> getAllByTitle(String title);
//
//    // show books by author
//    List<Book> getAllByAuthor(String author);
//
//    // get all by min price
//    List<Book> getAllByMinPrice(Double price);
//
//    // show books by min amount
//    List<Book> getAllByMinAmount(Integer minAmount);

//    List<Author> getAllAuthors();

    // show book by id (GET)
    Book showById(Integer id);

//    Author findAuthorById(Long id);

    Book update(Integer id, BookDTO updatedBook);

    // delete book (DELETE)
    void deleteBook(Integer id);

    // Pagination
    Page<Book> findPaginated(int page, int size);

    // search
    Page<Book> search(String keyword, int page, int size);

    Page<Book> findByAuthor(Long authorId, int page, int size);

    void saveDto(BookDTO bookDTO);


}
