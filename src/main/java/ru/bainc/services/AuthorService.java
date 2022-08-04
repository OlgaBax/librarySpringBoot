package ru.bainc.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.model.Author;
import ru.bainc.repositories.AuthorRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors (){
        return authorRepository.findAll();
    }

    public Author getById(Long id){
        return authorRepository.getById(id);
    }

    public Author getByAuthorSurName(String surName){
        return authorRepository.findBySurName(surName);
    }

    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }

    public void deleteById(Long id){
        authorRepository.deleteById(id);
    }

    public void deleteAuthor(Author author){
        deleteAuthor(author);
    }
}
