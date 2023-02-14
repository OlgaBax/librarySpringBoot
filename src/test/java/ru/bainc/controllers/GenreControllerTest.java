package ru.bainc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.bainc.dto.GenreDto;
import ru.bainc.model.Genre;
import ru.bainc.services.GenreService;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class GenreControllerTest {
    private GenreController genreController;
    private GenreService genreService;
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";

    @BeforeEach
    void setup(){
        genreService = Mockito.mock(GenreService.class);
        genreController = new GenreController(genreService);
    }

    @Test
    void getAllGenres() {
        Mockito.when(genreService.getAllGenresToFront()).thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));
        assertEquals(HttpStatus.OK,genreController.getAllGenres().getStatusCode());
    }

    @Test
    void getById() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        GenreDto genreDto = new GenreDto(genre);
        Mockito.when(genreService.getByIdToFront(DECIMAL)).thenReturn(new ResponseEntity<>(genreDto, HttpStatus.OK));
        assertEquals(HttpStatus.OK, genreController.getById(DECIMAL).getStatusCode());
    }

    @Test
    void getByTitle() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        GenreDto genreDto = new GenreDto(genre);
        Mockito.when(genreService.getByTitleToFront(genreDto)).thenReturn(new ResponseEntity<>(genreDto, HttpStatus.OK));
        assertEquals(HttpStatus.OK, genreController.getByTitle(genreDto).getStatusCode());
    }

    @Test
    void addGenre() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        GenreDto genreDto = new GenreDto(genre);
        Mockito.when(genreService.addGenreFromFront(genreDto)).thenReturn(new ResponseEntity<>(genreDto,HttpStatus.OK));
        assertEquals(HttpStatus.OK, genreController.addGenre(genreDto).getStatusCode());
    }
}