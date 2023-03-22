package ru.bainc.services;

import liquibase.pro.packaged.T;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelExtensionsKt;
import ru.bainc.dto.AuthorDto;
import ru.bainc.model.Author;
import ru.bainc.repositories.AuthorRepository;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AuthorServiceTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    private AuthorRepository authorRepository;
    private AuthorService authorService;

    @BeforeEach
    void setup(){
        authorRepository = Mockito.mock(AuthorRepository.class);
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void getAllAuthors() {
        Mockito.when(authorRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(authorService.getAllAuthors());
    }

    @Test
    void getById() {
        Author author = new Author(TEXT,TEXT,TEXT);
        author.setId(DECIMAL);
        Mockito.when(authorRepository.findById(DECIMAL)).thenReturn(Optional.of(author));
        assertEquals(author, authorService.getById(DECIMAL).get());
    }

    @Test
    void getByAuthorSurName() {
        Mockito.when(authorRepository.findBySurName(TEXT)).thenReturn(Collections.emptyList());
        assertNotNull(authorService.getByAuthorSurName(TEXT));
    }

    @Test
    void getAllAuthorToFront() {
        Mockito.when(authorRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, authorService.getAllAuthorToFront().getStatusCode());
    }

    @Test
    void getByIdFromFront() {
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        Mockito.when(authorRepository.findById(DECIMAL)).thenReturn(Optional.of(author));
        assertEquals(HttpStatus.OK, authorService.getByIdFromFront(DECIMAL).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, authorService.getByIdFromFront(null).getStatusCode());
    }

    @Test
    void getBySurNameToFront() {
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        AuthorDto authorDto = new AuthorDto(author);
        Mockito.when(authorRepository.findBySurName(TEXT)).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, authorService.getBySurNameToFront(authorDto).getStatusCode());
    }

    @Test
    void addAuthor() {
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        assertEquals(author, authorService.addAuthor(author));
    }

    @Test
    void getByFio() {
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        Mockito.when(authorRepository.findByFio(TEXT, TEXT, TEXT)).thenReturn(author);
        assertEquals(author, authorService.getByFio(TEXT, TEXT, TEXT));
    }

    @Test
    void addAuthorFromFront() {
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        AuthorDto authorDto = new AuthorDto(author);
        Mockito.when(authorRepository.save(any())).thenReturn(author);
        assertEquals(HttpStatus.OK, authorService.addAuthorFromFront(authorDto).getStatusCode());
    }

    @Test
    void deleteById(){
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        Mockito.when(authorRepository.findById(DECIMAL)).thenReturn(Optional.of(author));
        assertTrue(authorService.deleteById(DECIMAL));
        Mockito.doNothing().when(authorRepository).deleteById(any());
        assertFalse(authorService.deleteById(null));
    }

    @Test
    void deleteByIdFromFront(){
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        Mockito.when(authorRepository.findById(DECIMAL)).thenReturn(Optional.of(author));
        assertEquals(HttpStatus.OK, authorService.deleteByIdFromFront(DECIMAL).getStatusCode());
        Mockito.doNothing().when(authorRepository).deleteById(any());
        assertEquals(HttpStatus.BAD_REQUEST, authorService.deleteByIdFromFront(any()).getStatusCode());
    }

    @Test
    void deleteAuthor(){
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        Mockito.when(authorRepository.findByFio(TEXT, TEXT, TEXT)).thenReturn(author);
        assertTrue(authorService.deleteAuthor(author.getName(), author.getSurName(), author.getMiddleName()));
        Mockito.when(authorRepository.findByFio(any(), any(), any())).thenReturn(null);
        assertFalse(authorService.deleteAuthor(any(), any(), any()));
    }

    @Test
    void deleteByFioToFront(){
        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        AuthorDto authorDto = new AuthorDto(author);
        Mockito.when(authorRepository.findByFio(authorDto.getName(), authorDto.getSurName(), authorDto.getMiddleName())).thenReturn(author);
        assertEquals(HttpStatus.OK, authorService.deleteByFioToFront(authorDto).getStatusCode());
        Mockito.when(authorRepository.findByFio(any(), any(), any())).thenReturn(null);
        assertEquals(HttpStatus.BAD_REQUEST, authorService.deleteByFioToFront(authorDto).getStatusCode());
    }
}