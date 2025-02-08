package org.wildcodeschool.myblog.Service;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.mapper.AuthorMapper;
import org.wildcodeschool.myblog.model.Author;
import org.wildcodeschool.myblog.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository,
                         AuthorMapper authorMapper
    ) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::convertToDTO).collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return null;
        }
        return authorMapper.convertToDTO(author);
    }

    public AuthorDTO createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.convertToDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        if (existingAuthor == null) {
            return null;
        }
        existingAuthor.setFirstname(author.getFirstname());
        existingAuthor.setLastname(author.getLastname());
        Author savedAuthor = authorRepository.save(existingAuthor);
        return authorMapper.convertToDTO(savedAuthor);
    }

    public Author deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return null;
        }
        authorRepository.deleteById(id);
        return author;
    }
}