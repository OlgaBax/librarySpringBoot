package ru.bainc.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";


    @Test
    void setBooks() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Book book = new Book();
        Set<Book> bookSet = new HashSet<>();
        bookSet.add(book);
        tag.setBooks(bookSet);
        assertNotNull(tag.getBooks());
    }
}