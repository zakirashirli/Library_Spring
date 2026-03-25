package com.library.dea.service.impl;

import com.library.dea.dto.AuthorDTO;
import com.library.dea.entity.Author;
import com.library.dea.exception.AuthorAlreadyExistsException;
import com.library.dea.exception.AuthorNotFoundException;
import com.library.dea.repository.AuthorRepository;
import com.library.dea.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

//    private AuthorMapper authorMapper;

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("No Author with ID: " + id));

        return mapToDto(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        if (authorRepository.existsByName(authorDTO.getName())) {
            throw new AuthorAlreadyExistsException("Author Already Exists!");
        }

        Author author = mapToEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);

        return mapToDto(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("No Author with ID: " + id));

        author.setName(authorDTO.getName());

        return mapToDto(authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }


    //MAPPER
    private AuthorDTO mapToDto(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName()
        );
    }

    private Author mapToEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());

        return author;
    }


}
