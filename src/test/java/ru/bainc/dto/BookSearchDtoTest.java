package ru.bainc.dto;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BookSearchDtoTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    private BookSearchDto bookSearchDto = new BookSearchDto();


    @Test
    void setTagsListId() {
        bookSearchDto.setTagsListId(Collections.emptyList());
        assertNotNull(bookSearchDto.getTagsListId());
    }

    @Test
    void setIdTag() {
        bookSearchDto.setIdTag(DECIMAL);
        assertEquals(DECIMAL, bookSearchDto.getIdTag());
    }

    @Test
    void setPartTitle() {
        bookSearchDto.setPartTitle(TEXT);
        assertEquals(TEXT, bookSearchDto.getPartTitle());
    }

    @Test
    void setPartSurname() {
        bookSearchDto.setPartSurname(TEXT);
        assertEquals(TEXT, bookSearchDto.getPartSurname());
    }

}