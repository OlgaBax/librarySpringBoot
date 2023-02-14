package ru.bainc.services;


import lombok.extern.log4j.Log4j;
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

    @Transactional
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public List<Author> getByAuthorSurName(String surName) {
        return authorRepository.findBySurName(surName);
    }


    @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    @Transactional
    public ResponseEntity<List<AuthorDto>> getAllAuthorToFront() {
        return new ResponseEntity<>(getAllAuthors()
                .stream()
                .map(author -> new AuthorDto(author))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<AuthorDto> getByIdFromFront(Long id) {
        Author author = getById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AuthorDto(author), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<List<AuthorDto>> getBySurNameToFront(AuthorDto authorDto) {
        return new ResponseEntity<>(getByAuthorSurName(authorDto.getSurName())
                .stream()
                .map(author -> new AuthorDto(author))
                .collect(Collectors.toList()), HttpStatus.OK);
   }


    @Transactional
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Author getByFio(String surname, String name, String middleName){
        return authorRepository.findByFio(surname, name, middleName);
    }

    @Transactional
    public ResponseEntity<AuthorDto> addAuthorFromFront(AuthorDto authorDto){
        Author author = getByFio(authorDto.getSurName(),authorDto.getName(),authorDto.getMiddleName());
        if(author != null){
            return new ResponseEntity<>(new AuthorDto(author),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new AuthorDto(addAuthor
                    (new Author(authorDto.getSurName(), authorDto.getName(), authorDto.getMiddleName()))),HttpStatus.OK);
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
    public ResponseEntity<?> deleteByFioToFront(AuthorDto authorDto) {
        Author author = getByFio(authorDto.getSurName(), authorDto.getName(), authorDto.getMiddleName());
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            deleteAuthor(author);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}



