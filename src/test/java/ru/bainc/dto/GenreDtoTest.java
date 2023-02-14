package ru.bainc.dto;

import org.junit.jupiter.api.Test;
import ru.bainc.model.Genre;

import static org.junit.jupiter.api.Assertions.*;

class GenreDtoTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "Test";
    private GenreDto genreDto = new GenreDto();

    @Test
    void setId() {
        genreDto.setId(DECIMAL);
        assertEquals(DECIMAL, genreDto.getId());
    }

    @Test
    void setTitle() {
        genreDto.setTitle(TEXT);
        assertEquals(TEXT, genreDto.getTitle());
    }

    @Test
    void testConstructor(){
        Genre genre = new Genre();
        genre.setId(DECIMAL);
        genre.setGenreTitle(TEXT);

        genreDto = new GenreDto(genre);
        assertEquals(DECIMAL, genreDto.getId());
        assertEquals(TEXT, genreDto.getTitle());

    }
}