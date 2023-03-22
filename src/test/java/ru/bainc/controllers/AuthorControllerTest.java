package ru.bainc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.bainc.dto.AuthorDto;
import ru.bainc.model.Author;
import ru.bainc.services.AuthorService;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AuthorControllerTest {
    private AuthorController authorController;
    private AuthorService authorService;
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";

    @BeforeEach
    void setup(){
        authorService = Mockito.mock(AuthorService.class);
        authorController = new AuthorController(authorService);
    }

    @Test
    void getAllAuthors() {
        Mockito.when(authorService.getAllAuthorToFront())
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));
        assertEquals(HttpStatus.OK, authorController.getAllAuthors().getStatusCode());
    }

    @Test
    void getById() {
        Author author = new Author(TEXT, TEXT,TEXT);
        author.setId(DECIMAL);
        AuthorDto authorDto = new AuthorDto(author);

        Mockito.when(authorService.getByIdFromFront(DECIMAL)).thenReturn(new ResponseEntity<>(authorDto, HttpStatus.OK));
        assertEquals(HttpStatus.OK, authorController.getById(DECIMAL).getStatusCode());
    }

    @Test
    void getBySurName() {
        Author author = new Author(TEXT, TEXT,TEXT);
        author.setId(DECIMAL);
        AuthorDto authorDto = new AuthorDto(author);

        Mockito.when(authorService.getBySurNameToFront(authorDto))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(HttpStatus.OK, authorController.getBySurName(authorDto).getStatusCode());
    }

    @Test
    void addAuthor() {
        Author author = new Author(TEXT, TEXT,TEXT);
        author.setId(DECIMAL);
        AuthorDto authorDto = new AuthorDto(author);

        Mockito.when(authorService.addAuthorFromFront(authorDto))
                .thenReturn(new ResponseEntity<>(authorDto, HttpStatus.OK));
        assertEquals(HttpStatus.OK, authorController.addAuthor(authorDto).getStatusCode());
    }

    @Test
    void deleteById(){
        Mockito.when(authorService.deleteByIdFromFront(DECIMAL))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(HttpStatus.OK, authorController.deleteById(DECIMAL).getStatusCode());
    }

    @Test
    void deleteByFio(){
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        AuthorDto authorDto = new AuthorDto(author);
        Mockito.when(authorService.deleteByFioToFront(authorDto))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(HttpStatus.OK, authorController.deleteByFio(authorDto).getStatusCode());
    }
}