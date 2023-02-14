package ru.bainc.dto;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class BookInDtoTest {
    private final String TEXT = "test";
    BookInDto bookInDto = new BookInDto();

    @Test
    void setTitle() {
        bookInDto.setTitle(TEXT);
        assertEquals(TEXT, bookInDto.getTitle());
    }

    @Test
    void setOriginalTitle() {
        bookInDto.setOriginalTitle(TEXT);
        assertEquals(TEXT, bookInDto.getOriginalTitle());
    }

    @Test
    void setYearEdition() {
        bookInDto.setYearEdition(TEXT);
        assertEquals(TEXT, bookInDto.getYearEdition());
    }

    @Test
    void setYearOfEditionTranslate() {
        bookInDto.setYearOfEditionTranslate(TEXT);
        assertEquals(TEXT, bookInDto.getYearOfEditionTranslate());
    }

    @Test
    void setIsbnOriginal() {
        bookInDto.setIsbnOriginal(TEXT);
        assertEquals(TEXT, bookInDto.getIsbnOriginal());
    }

    @Test
    void setIsbnTranslate() {
        bookInDto.setIsbnTranslate(TEXT);
        assertEquals(TEXT, bookInDto.getIsbnTranslate());
    }

    @Test
    void setFileFormatBook() {
        bookInDto.setFileFormatBook(TEXT);
        assertEquals(TEXT, bookInDto.getFileFormatBook());
    }

    @Test
    void setPathToZipBook() {
        bookInDto.setPathToZipBook(TEXT);
        assertEquals(TEXT, bookInDto.getPathToZipBook());
    }

    @Test
    void setGenreId() {
        bookInDto.setGenreId(TEXT);
        assertEquals(TEXT, bookInDto.getGenreId());
    }

    @Test
    void setPubHouseId() {
        bookInDto.setPubHouseId(TEXT);
        assertEquals(TEXT, bookInDto.getPubHouseId());
    }

    @Test
    void setPubHouseTranslateId() {
        bookInDto.setPubHouseTranslateId(TEXT);
        assertEquals(TEXT, bookInDto.getPubHouseTranslateId());
    }

    @Test
    void setTagsId() {
        bookInDto.setTagsId(Collections.emptyList());
        assertNotNull(bookInDto.getTagsId());
    }

    @Test
    void setAuthorsId() {
        bookInDto.setAuthorsId(Collections.emptyList());
        assertNotNull(bookInDto.getAuthorsId());
    }

    @Test
    void setFile() {
        bookInDto.setFile(TEXT);
        assertEquals(TEXT, bookInDto.getFile());
    }

    @Test
    void testConstructor(){
        BookInDto bookInDto = new BookInDto();
        assertNotNull(bookInDto);
    }
}