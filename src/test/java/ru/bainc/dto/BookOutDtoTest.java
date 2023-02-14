package ru.bainc.dto;

import org.junit.jupiter.api.Test;
import ru.bainc.model.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.bainc.model.FileFormatBook.*;

class BookOutDtoTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    BookOutDto bookOutDto = new BookOutDto();

    @Test
    void setId() {
        bookOutDto.setId(DECIMAL);
        assertEquals(DECIMAL, bookOutDto.getId());
    }

    @Test
    void setUUID() {
        UUID uuid = UUID.randomUUID();
        bookOutDto.setUuid(uuid);
        assertEquals(uuid, bookOutDto.getUuid());
    }

    @Test
   void setTitle(){
        bookOutDto.setTitle(TEXT);
        assertEquals(TEXT, bookOutDto.getTitle());
    }

    @Test
    void setOriginalTitle(){
        bookOutDto.setOriginalTitle(TEXT);
        assertEquals(TEXT, bookOutDto.getOriginalTitle());
    }

    @Test
    void setYearEdition(){
        bookOutDto.setYearEdition(TEXT);
        assertEquals(TEXT, bookOutDto.getYearEdition());
    }

    @Test
    void setYearOfEditionTranslate(){
        bookOutDto.setYearOfEditionTranslate(TEXT);
        assertEquals(TEXT, bookOutDto.getYearOfEditionTranslate());
    }

    @Test
    void setIsbnOriginal(){
        bookOutDto.setIsbnOriginal(TEXT);
        assertEquals(TEXT, bookOutDto.getIsbnOriginal());
    }

    @Test
    void setIsbnTranslate(){
        bookOutDto.setIsbnTranslate(TEXT);
        assertEquals(TEXT, bookOutDto.getIsbnTranslate());
    }

    @Test
    void setFileFormatBook() {
        bookOutDto.setFileFormatBook(TEXT);
        assertEquals(TEXT, bookOutDto.getFileFormatBook());
    }

    @Test
    void setPathToZipBook() {
        bookOutDto.setPathToZipBook(TEXT);
        assertEquals(TEXT, bookOutDto.getPathToZipBook());
    }

    @Test
    void setGenre(){
        bookOutDto.setGenre(TEXT);
        assertEquals(TEXT, bookOutDto.getGenre());
    }

    @Test
    void setPubHouse(){
        bookOutDto.setPubHouse(TEXT);
        assertEquals(TEXT, bookOutDto.getPubHouse());
    }

    @Test
    void setPubHouseTranslate(){
        bookOutDto.setPubHouseTranslate(TEXT);
        assertEquals(TEXT, bookOutDto.getPubHouseTranslate());
    }

    @Test
    void testConstruct() {
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

        BookOutDto bookOutDto = new BookOutDto(book);
        assertEquals(DECIMAL, bookOutDto.getId());
        assertNotNull(bookOutDto.getUuid());
        assertEquals(TEXT, bookOutDto.getTitle());
        assertEquals(TEXT, bookOutDto.getOriginalTitle());
        assertEquals(TEXT, bookOutDto.getYearEdition());
        assertEquals(TEXT, bookOutDto.getYearOfEditionTranslate());
        assertEquals(TEXT, bookOutDto.getIsbnOriginal());
        assertEquals(TEXT, bookOutDto.getIsbnTranslate());
        assertEquals(genre.getGenreTitle(), bookOutDto.getGenre());
        assertEquals(fileFormatBook.toString(), bookOutDto.getFileFormatBook());
        assertEquals(book.getPathToZipBook(), bookOutDto.getPathToZipBook());
        assertEquals(pubHouse.getPubHouseTitle(), bookOutDto.getPubHouse());
        assertEquals(pubHouseTranslate.getPubHouseTitle(), bookOutDto.getPubHouseTranslate());
        assertNotNull(bookOutDto.getTags());
        assertNotNull(bookOutDto.getAuthors());
    }

    @Test
    void testConstructDefault() {
        BookOutDto bookOutDto = new BookOutDto();
        assertNotNull(bookOutDto);
    }
}