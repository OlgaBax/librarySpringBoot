package ru.bainc.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void setBooks() {
        Author author = new Author();
        Book book = new Book();
        Set<Book> bookSet = new HashSet<>();
        bookSet.add(book);
        author.setBooks(bookSet);
        assertNotNull(author.getBooks());
    }
}