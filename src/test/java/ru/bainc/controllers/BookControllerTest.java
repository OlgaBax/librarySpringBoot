package ru.bainc.controllers;

import liquibase.pro.packaged.M;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.bainc.dto.BookInDto;
import ru.bainc.dto.BookOutDto;
import ru.bainc.dto.BookSearchDto;
import ru.bainc.model.*;
import ru.bainc.services.BookService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.bainc.model.FileFormatBook.TXT;

class BookControllerTest {
    private BookService bookService;
    private BookController bookController;
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";

    @BeforeEach
    void setup(){
        bookService = Mockito.mock(BookService.class);
        bookController = new BookController(bookService);
    }

    public Book creatorBook() {
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

        Set<Author> authorSet = new HashSet<>();
        authorSet.add(author);

        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tag);

        Book book = new Book();
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
        book.setTags(tagSet);
        book.setAuthors(authorSet);

        return book;
    }

    @Test
    void getAllBooks() {
        Mockito.when(bookService.getAllBooksToFront())
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getAllBooks().getStatusCode());
    }

    @Test
    void getByTitle() {
        Book book = creatorBook();
        book.setId(DECIMAL);
        BookOutDto bookOutDto = new BookOutDto(book);

        Mockito.when(bookService.getByTitleFromFront(bookOutDto))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getByTitle(bookOutDto).getStatusCode());
    }

    @Test
    void getByPartTitle() {
        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setPartTitle(TEXT);
        Mockito.when(bookService.getBookByPartTitleFromFront(bookSearchDto))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getByPartTitle(bookSearchDto).getStatusCode());

    }

    @Test
    void getById() {
        Book book = creatorBook();
        book.setId(DECIMAL);
        BookOutDto bookOutDto = new BookOutDto(book);

        Mockito.when(bookService.getByIdFromFront(DECIMAL))
                .thenReturn(new ResponseEntity<>(bookOutDto, HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getById(DECIMAL).getStatusCode());
    }

    @Test
    void getByGenre() {
        Book book = creatorBook();
     //   book.setId(DECIMAL);
        BookOutDto bookOutDto = new BookOutDto(book);

        Mockito.when(bookService.getByGenreFromFront(bookOutDto))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getByGenre(bookOutDto).getStatusCode());


    }

    @Test
    void getByPubHouse() {
        Book book = creatorBook();
//        book.setId(DECIMAL);
        BookOutDto bookOutDto = new BookOutDto(book);

        Mockito.when(bookService.getByPubHouseFromFront(bookOutDto))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getByPubHouse(bookOutDto).getStatusCode());
    }

    @Test
    void getByTag() {
        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setIdTag(DECIMAL);

        Mockito.when(bookService.getByTagFromFront(bookSearchDto))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getByTag(bookSearchDto).getStatusCode());

    }

    @Test
    void getByPartAuthorSurname() {
        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setPartSurname(TEXT);

        Mockito.when(bookService.getByPartAuthorSurnameFromFront(bookSearchDto))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.getByPartAuthorSurname(bookSearchDto).getStatusCode());
    }

    @Test
    void addBook() throws Exception {
        Genre genre = new Genre();
        genre.setId(DECIMAL);
        genre.setGenreTitle(TEXT);

        PubHouse pubHouse = new PubHouse();
        pubHouse.setId(DECIMAL);
        pubHouse.setPubHouseTitle(TEXT);

        PubHouse pubHouseTranslate = new PubHouse();
        pubHouseTranslate.setId(DECIMAL);
        pubHouseTranslate.setPubHouseTitle(TEXT);

        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        List<String> authorList = new ArrayList<>();
        authorList.add(author.getId().toString());

        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        List<String> tagList = new ArrayList<>();
        tagList.add(tag.getId().toString());

        BookInDto bookInDto = new BookInDto();
        bookInDto.setTitle(TEXT);
        bookInDto.setOriginalTitle(TEXT);
        bookInDto.setIsbnOriginal(TEXT);
        bookInDto.setIsbnTranslate(TEXT);
        bookInDto.setYearEdition(TEXT);
        bookInDto.setYearOfEditionTranslate(TEXT);
        bookInDto.setFileFormatBook("TXT");
        bookInDto.setPathToZipBook(TEXT);
        bookInDto.setGenreId(DECIMAL.toString());
        bookInDto.setPubHouseId(DECIMAL.toString());
        bookInDto.setPubHouseTranslateId(DECIMAL.toString());
        bookInDto.setTagsId(tagList);
        bookInDto.setAuthorsId(authorList);
        bookInDto.setFile(TEXT);

        Book book = creatorBook();
        BookOutDto bookOutDto = new BookOutDto(book);

        Mockito.when(bookService.addBookFromFront(bookInDto))
                .thenReturn(new ResponseEntity<>(bookOutDto,HttpStatus.OK));
        assertEquals(HttpStatus.OK, bookController.addBook(bookInDto).getStatusCode());

    }

    @Test
    void downloadBook() {
        Book book = creatorBook();
        book.setId(DECIMAL);


    }
}