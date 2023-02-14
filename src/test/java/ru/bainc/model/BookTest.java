package ru.bainc.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.bainc.model.FileFormatBook.TXT;

class BookTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";

    @Test
    void setStringToString() {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("1");
        stringSet.add("2");
        assertEquals("1; 2; ", Book.setStringToString(stringSet));
    }

    @Test
    void bookToStringInfo() {
        Genre genre = new Genre();
        genre.setId(DECIMAL);
        genre.setGenreTitle(TEXT);

        PubHouse pubHouse = new PubHouse();
        pubHouse.setId(DECIMAL);
        pubHouse.setPubHouseTitle(TEXT);

        PubHouse pubHouseTranslate = new PubHouse();
        pubHouseTranslate.setId(DECIMAL);
        pubHouseTranslate.setPubHouseTitle(TEXT);

        FileFormatBook fileFormatBook = TXT;

        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);

        Set<Author> authorList = new HashSet<>();
        authorList.add(author);

        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);

        Set<Tag> listTags = new HashSet<>();
        listTags.add(tag);

        Book book = new Book();
        book.setId(DECIMAL);
        book.setUuid(UUID.randomUUID());
        book.setTitle(TEXT);
        book.setOriginalTitle(TEXT);
        book.setYearEdition(TEXT);
        book.setYearOfEditionTranslate(TEXT);
        book.setIsbnOriginal(TEXT);
        book.setIsbnTranslate(TEXT);
        book.setGenre(genre);
        book.setFileFormatBook(fileFormatBook);
        book.setPathToZipBook(TEXT);
        book.setPubHouse(pubHouse);
        book.setPubHouseTranslate(pubHouseTranslate);
        book.setTags(listTags);
        book.setAuthors(authorList);

        System.out.println(book.bookToStringInfo());
        String testString = "UUID: " + book.getUuid() +"\n" +
                "Название: test\n" +
                "Оригинальное название: test\n" +
                "Год издания оригинала: test\n" +
                "Год издания перевода: test\n" +
                "ISBN оригинала: test\n" +
                "ISBN перевода: test\n" +
                "Формат книги: TXT\n" +
                "Имя файла архива: test\n" +
                "Жанр: test\n" +
                "Издательство оригинала: test\n" +
                "Издательство перевода: test\n" +
                "Авторы: testtesttest; \n" +
                "Теги: test; \n\n\n ";
        assertEquals(testString, book.bookToStringInfo());
    }
}