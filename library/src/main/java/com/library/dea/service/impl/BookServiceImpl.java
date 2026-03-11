package com.library.dea.service.impl;

import com.library.dea.dto.BookDTO;
import com.library.dea.entity.Author;
import com.library.dea.entity.Book;
import com.library.dea.mapper.BookMapper;
import com.library.dea.repository.AuthorRepository;
import com.library.dea.repository.BookRepository;
import com.library.dea.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
//    private final BookService bookService;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
//        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> showAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book showById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Book with id: " + id));
    }

    @Override
    public Book update(Integer id, BookDTO updatedBook) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Book with id: " + id));
        existing.setTitle(updatedBook.getTitle());
        existing.setPrice(updatedBook.getPrice());
        existing.setAmount(updatedBook.getAmount());

        return bookRepository.save(existing);
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public void saveDto(BookDTO bookDTO) {
        Book entity = BookMapper.toEntity(bookDTO);
        Author author = authorRepository
                .findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("No Author"));

        entity.setAuthor(author);
        bookRepository.save(entity);
    }
}
