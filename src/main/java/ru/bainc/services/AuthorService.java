package ru.bainc.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.dto.AuthorDto;
import ru.bainc.model.Author;
import ru.bainc.repositories.AuthorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    public Author getByAuthorSurName(String surName) {
        Author author = authorRepository.findBySurName(surName);
        return author;
    }


    @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    public ResponseEntity<List<AuthorDto>> getAllAuthorToFront() {
        return new ResponseEntity<>(getAllAuthors()
                .stream()
                .map(author -> new AuthorDto(author))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<AuthorDto> getByIdFromFront(Long id) {
        Author author = getById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AuthorDto(author), HttpStatus.OK);
        }
    }

    public ResponseEntity<AuthorDto> getBySurNameToFront(AuthorDto authorDto) {
        Author author = getByAuthorSurName(authorDto.getSurName());
        if (author != null) {
            return new ResponseEntity<>(new AuthorDto(getByAuthorSurName(author.getSurName())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public Author getByAuthorName(String name) {
        return authorRepository.findByName(name);
    }

    public ResponseEntity<AuthorDto> getByNameFromFront(AuthorDto authorDto) {
        Author author = getByAuthorName(authorDto.getName());
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AuthorDto(getByAuthorName(author.getName())), HttpStatus.OK);
        }
    }

    public Author getByMiddleName(String middleName) {
        return authorRepository.findByMiddleName(middleName);
    }

    public ResponseEntity<AuthorDto> getByMiddleNameFromFront(AuthorDto authorDto) {
        Author author = getByMiddleName(authorDto.getMiddleName());
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AuthorDto(getByMiddleName(author.getMiddleName())), HttpStatus.OK);
        }
    }

    @Transactional
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public ResponseEntity<AuthorDto> addAuthorFromFront(AuthorDto authorDto) {
        Author author = getByAuthorSurName(authorDto.getSurName());
        if (author != null && getByAuthorName(authorDto.getName()) != null
                && getByMiddleName(authorDto.getMiddleName()) != null) {
            log.info("Такой автор уже существует");
            return new ResponseEntity<>(new AuthorDto(author), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new AuthorDto(addAuthor
                    (new Author(authorDto.getSurName(), authorDto.getName(), authorDto.getMiddleName()))), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteByIdFromFront(Long id) {
        try {
            deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Author with id {} deleted", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteBySurNameToFront(AuthorDto authorDto) {
        Author author = getByAuthorSurName(authorDto.getSurName());
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            deleteAuthor(author);
            log.info("Author with surname {} deleted", authorDto.getSurName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}



