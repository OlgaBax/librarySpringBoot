package ru.bainc.dto;

import org.junit.jupiter.api.Test;
import ru.bainc.model.Author;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDtoTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    AuthorDto authorDto = new AuthorDto();

    @Test
    void setId() {
        authorDto.setId(DECIMAL);
        assertEquals(DECIMAL,authorDto.getId());
    }

    @Test
    void setName() {
        authorDto.setName(TEXT);
        assertEquals(TEXT, authorDto.getName());
    }

    @Test
    void setSurName() {
        authorDto.setSurName(TEXT);
        assertEquals(TEXT, authorDto.getSurName());
    }

    @Test
    void setMiddleName() {
        authorDto.setMiddleName(TEXT);
        assertEquals(TEXT, authorDto.getMiddleName());
    }

    @Test
    void testConstructor(){
        Author author = new Author();
        author.setId(DECIMAL);
        author.setName(TEXT);
        author.setSurName(TEXT);
        author.setMiddleName(TEXT);

        authorDto = new AuthorDto(author);
        assertEquals(DECIMAL,authorDto.getId());
        assertEquals(TEXT, authorDto.getName());
        assertEquals(TEXT, authorDto.getSurName());
        assertEquals(TEXT, authorDto.getMiddleName());
    }
}