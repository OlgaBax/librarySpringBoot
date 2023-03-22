package ru.bainc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import ru.bainc.dto.GenreDto;
import ru.bainc.model.Genre;
import ru.bainc.repositories.GenreRepository;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class GenreServiceTest {

    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    private GenreService genreService;
    private GenreRepository genreRepository;

    @BeforeEach
    void setup(){
        // заглушка для репозитория
        genreRepository = Mockito.mock(GenreRepository.class);
        genreService = new GenreService(genreRepository);
    }

    @Test
    void getAll() {
        Mockito.when(genreRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(genreService.getAll());
    }

    @Test
    void getById() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.when(genreRepository.findById(DECIMAL)).thenReturn(Optional.of(genre));
        assertNotNull(genreService.getById(DECIMAL).get());
    }

    @Test
    void getByGenreTitle() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.when(genreRepository.findByGenreTitle(TEXT)).thenReturn(genre);
        assertNotNull(genreService.getByGenreTitle(TEXT));
        assertEquals(null, genreService.getByGenreTitle(null));

    }

    @Test
    void addGenre() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.when(genreRepository.save(null)).thenReturn(null);
        assertNull(genreService.addGenre(null));
        Mockito.when(genreRepository.save(genre)).thenReturn(genre);
        assertEquals(genre, genreService.addGenre(genre));

    }
    @Test
    void addGenreFromFront() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        GenreDto genreDto = new GenreDto(genre);
        Mockito.when(genreRepository.save(any())).thenReturn(genre);
        assertEquals(HttpStatus.OK, genreService.addGenreFromFront(genreDto).getStatusCode());
    }

    @Test
    void deleteGenreById() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.when(genreRepository.findById(DECIMAL)).thenReturn(Optional.of(genre));
        Mockito.doNothing().when(genreRepository).delete(genre);
        assertTrue(genreService.deleteGenreById(DECIMAL));
        assertFalse(genreService.deleteGenreById(null));
    }


    @Test
    void deleteByIdFromFront() throws Exception {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.when(genreRepository.findById(DECIMAL)).thenReturn(Optional.of(genre));
        assertEquals(HttpStatus.OK, genreService.deleteByIdFromFront(DECIMAL).getStatusCode());
        Mockito.doNothing().when(genreRepository).deleteById(any());
        assertEquals(HttpStatus.NOT_FOUND, genreService.deleteByIdFromFront(null).getStatusCode());
    }

    @Test
    void deleteGenreByTitle(){
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.doNothing().when(genreRepository).delete(Mockito.any(Genre.class));
        assertFalse(genreService.deleteGenreByTitle(genre.getGenreTitle()));
        Mockito.doReturn(genre).when(genreRepository).findByGenreTitle(genre.getGenreTitle());
        assertTrue(genreService.deleteGenreByTitle(genre.getGenreTitle()));
    }


    @Test
    void deleteByTitleToFront(){
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        GenreDto genreDto = new GenreDto(genre);
        Mockito.when(genreRepository.findByGenreTitle(genre.getGenreTitle())).thenReturn(genre);
        assertEquals(HttpStatus.OK, genreService.deleteByTitleToFront(genreDto).getStatusCode());
        Mockito.when(genreRepository.findByGenreTitle(any())).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, genreService.deleteByTitleToFront(genreDto).getStatusCode());
    }

    @Test
    void getAllGenresToFront() {
        Mockito.when(genreRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, genreService.getAllGenresToFront().getStatusCode());
    }

    @Test
    void getByIdToFront() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.when(genreRepository.findById(DECIMAL)).thenReturn(Optional.of(genre));
        assertEquals(HttpStatus.NOT_FOUND, genreService.getByIdToFront(null).getStatusCode());
        assertEquals(HttpStatus.OK, genreService.getByIdToFront(DECIMAL).getStatusCode());
    }

    @Test
    void getByTitleToFront() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        GenreDto genreDto = new GenreDto(genre);
        Mockito.when(genreRepository.findByGenreTitle(TEXT)).thenReturn(genre);
        assertEquals(HttpStatus.OK, genreService.getByTitleToFront(genreDto).getStatusCode());
        Mockito.when(genreRepository.findByGenreTitle(any())).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, genreService.getByTitleToFront(genreDto).getStatusCode());
    }
}