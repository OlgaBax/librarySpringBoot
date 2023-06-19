package ru.bainc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import ru.bainc.dto.BookInDto;
import ru.bainc.dto.BookOutDto;
import ru.bainc.dto.BookSearchDto;
import ru.bainc.model.*;
import ru.bainc.repositories.BookRepository;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static ru.bainc.model.FileFormatBook.TXT;

class BookServiceTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    private BookService bookService;
    private BookRepository bookRepository;
    private TagService tagService;
    private AuthorService authorService;
    private PubHouseService pubHouseService;
    private GenreService genreService;

    @BeforeEach
    void setup() {
        bookRepository = Mockito.mock(BookRepository.class);
        tagService = Mockito.mock(TagService.class);
        authorService = Mockito.mock(AuthorService.class);
        pubHouseService = Mockito.mock(PubHouseService.class);
        genreService = Mockito.mock(GenreService.class);
        bookService = new BookService(bookRepository, tagService, authorService, pubHouseService, genreService);
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
        Mockito.when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        // 1-ый способ
        //        assertNotNull(bookService.getAllBooks());
        // 2-ой способ
        List<Book> books = bookService.getAllBooks();
        assertTrue(books.isEmpty(), "List Books is empty");
    }

    @Test
    void getAllBooksToFront() {
        Mockito.when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, bookService.getAllBooksToFront().getStatusCode());
    }

    @Test
    void getById() {
        Book book = new Book();
        book.setId(DECIMAL);
        Mockito.when(bookRepository.findById(DECIMAL)).thenReturn(Optional.of(book));
        assertEquals(book, bookService.getById(DECIMAL).get());
    }

    @Test
    void getByIdFromFront() {

        creatorBook();
        Mockito.when(bookRepository.findById(DECIMAL)).thenReturn(Optional.of(creatorBook()));
        assertEquals(HttpStatus.OK, bookService.getByIdFromFront(DECIMAL).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, bookService.getByIdFromFront(null).getStatusCode());
    }

    @Test
    void getBookByTitle() {
        Mockito.when(bookRepository.findByTitle(TEXT)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookService.getBookByTitle(TEXT));
        assertNotNull(bookService.getBookByTitle(null));
    }

    @Test
    void getBookByPartTitle() {
        Mockito.when(bookRepository.findBookByPartTitle(TEXT)).thenReturn(Collections.emptyList());
        assertNotNull(bookService.getBookByTitle(TEXT));
        assertNotNull(bookService.getBookByTitle(null));
    }

    @Test
    void getByTitleFromFront() {
        BookOutDto bookOutDto = new BookOutDto(creatorBook());
        Mockito.when(bookRepository.findByTitle(TEXT)).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, bookService.getByTitleFromFront(bookOutDto).getStatusCode());
    }

    @Test
    void getBookByPartTitleFromFront() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        List<Long> listIdsTag = new ArrayList<>();
        listIdsTag.add(tag.getId());

        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setIdTag(DECIMAL);
        bookSearchDto.setPartTitle(TEXT);
        bookSearchDto.setPartSurname(TEXT);
        bookSearchDto.setTagsListId(listIdsTag);
        Mockito.when(bookRepository.findBookByPartTitle(TEXT)).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, bookService.getBookByPartTitleFromFront(bookSearchDto).getStatusCode());
    }

    @Test
    void getBookByGenre() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        Mockito.when(bookRepository.getByGenre(genre)).thenReturn(Collections.emptyList());
        assertNotNull(bookService.getBookByGenre(genre));
        assertEquals(Collections.emptyList(), bookService.getBookByGenre(null));
    }

    @Test
    void getByGenreFromFront() {
        Genre genre = new Genre(TEXT);
        genre.setId(DECIMAL);
        BookOutDto bookOutDto = new BookOutDto(creatorBook());
        Mockito.when(bookRepository.getByGenre(genre)).thenReturn(Collections.emptyList());
        Mockito.when(genreService.getByGenreTitle(genre.getGenreTitle())).thenReturn(genre);
        assertEquals(HttpStatus.OK, bookService.getByGenreFromFront(bookOutDto).getStatusCode());
        Mockito.when(bookRepository.getByGenre(any())).thenReturn(null);
        Mockito.when(genreService.getByGenreTitle(any())).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, bookService.getByGenreFromFront(bookOutDto).getStatusCode());
    }

    @Test
    void getByPubHouse() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        Mockito.when(bookRepository.getByPubHouse(pubHouse)).thenReturn(Collections.emptyList());
        assertNotNull(bookService.getByPubHouse(pubHouse));
        assertNotNull(bookService.getByPubHouse(null));
    }

    @Test
    void getByPubHouseFromFront() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        BookOutDto bookOutDto = new BookOutDto(creatorBook());
        Mockito.when(bookRepository.getByPubHouse(pubHouse)).thenReturn(Collections.emptyList());
        Mockito.when(pubHouseService.getByPubHouseTitle(pubHouse.getPubHouseTitle())).thenReturn(pubHouse);
        assertEquals(HttpStatus.OK, bookService.getByPubHouseFromFront(bookOutDto).getStatusCode());
        Mockito.when(bookRepository.getByPubHouse(any())).thenReturn(null);
        Mockito.when(pubHouseService.getByPubHouseTitle(any())).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, bookService.getByPubHouseFromFront(bookOutDto).getStatusCode());
    }

    @Test
    void getByTagFromFront() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setIdTag(DECIMAL);
        List<Long> tagList = new ArrayList<>();
        tagList.add(tag.getId());
        bookSearchDto.setTagsListId(tagList);
        Mockito.when(bookRepository.getByTagId(any())).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, bookService.getByTagFromFront(bookSearchDto).getStatusCode());
    }

    @Test
    void getByPartAuthorSurname() {
        Mockito.when(bookRepository.getBookByPartAuthorSurname(TEXT)).thenReturn(Collections.emptyList());
        assertNotNull(bookService.getByPartAuthorSurname(TEXT));
    }

    @Test
    void getByPartAuthorSurnameFromFront() {
        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setPartSurname(TEXT);
        Mockito.when(bookRepository.getBookByPartAuthorSurname(TEXT)).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, bookService.getByPartAuthorSurnameFromFront(bookSearchDto).getStatusCode());
    }

    @Test
    void addBook() {

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


        List<String> tagIdsList = new ArrayList<>();
        tagIdsList.add(DECIMAL.toString());

        List<String> authorIdsList = new ArrayList<>();
        authorIdsList.add(DECIMAL.toString());


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
        bookInDto.setTagsId(tagIdsList);
        bookInDto.setAuthorsId(authorIdsList);
        bookInDto.setFile(TEXT);


        Mockito.when(genreService.getById(DECIMAL)).thenReturn(Optional.of(genre));
        Mockito.when(pubHouseService.getById(DECIMAL)).thenReturn(Optional.of(pubHouse));
        Mockito.when(tagService.getById(DECIMAL)).thenReturn(Optional.of(tag));
        Mockito.when(authorService.getById(DECIMAL)).thenReturn(Optional.of(author));
        Book book = bookService.addBook(bookInDto);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.addBook(bookInDto));
    }

    @Test
    void saveInfoAboutBookInFile() {
        Book book = creatorBook();

        Author author = new Author(TEXT, TEXT, TEXT);
        author.setId(DECIMAL);
        List<String> authorList = new ArrayList<>();
        authorList.add(author.getId().toString());

        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        List<String> tagList = new ArrayList<>();
        tagList.add(tag.getId().toString());

        Mockito.when(bookService.getBookByTitle(TEXT)).thenReturn(Collections.emptyList());
        Mockito.when(genreService.getById(DECIMAL)).thenReturn(Optional.of(book.getGenre()));
        Mockito.when(pubHouseService.getById(DECIMAL)).thenReturn(Optional.of(book.getPubHouse()));
        Mockito.when(tagService.getById(DECIMAL)).thenReturn(Optional.of(tag));
        Mockito.when(authorService.getById(DECIMAL)).thenReturn(Optional.of(author));
        book.bookToStringInfo();

        assertTrue(bookService.saveInfoAboutBookInFile(book, TEXT));
    }

//    @Test
//    void addBookFromFront() throws Exception {
//        Genre genre = new Genre();
//        genre.setId(DECIMAL);
//        genre.setGenreTitle(TEXT);
//
//        PubHouse pubHouse = new PubHouse();
//        pubHouse.setId(DECIMAL);
//        pubHouse.setPubHouseTitle(TEXT);
//
//        PubHouse pubHouseTranslate = new PubHouse();
//        pubHouseTranslate.setId(DECIMAL);
//        pubHouseTranslate.setPubHouseTitle(TEXT);
//
//        Author author = new Author(TEXT, TEXT, TEXT);
//        author.setId(DECIMAL);
//        List<String> authorList = new ArrayList<>();
//        authorList.add(author.getId().toString());
//
//        Tag tag = new Tag(TEXT);
//        tag.setId(DECIMAL);
//        List<String> tagList = new ArrayList<>();
//        tagList.add(tag.getId().toString());
//
//        BookInDto bookInDto = new BookInDto();
//        bookInDto.setTitle(TEXT);
//        bookInDto.setOriginalTitle(TEXT);
//        bookInDto.setIsbnOriginal(TEXT);
//        bookInDto.setIsbnTranslate(TEXT);
//        bookInDto.setYearEdition(TEXT);
//        bookInDto.setYearOfEditionTranslate(TEXT);
//        bookInDto.setFileFormatBook("TXT");
//        bookInDto.setPathToZipBook(TEXT);
//        bookInDto.setGenreId(DECIMAL.toString());
//        bookInDto.setPubHouseId(DECIMAL.toString());
//        bookInDto.setPubHouseTranslateId(DECIMAL.toString());
//        bookInDto.setTagsId(tagList);
//        bookInDto.setAuthorsId(authorList);
//        bookInDto.setFile(TEXT);
//
//        Book book =  creatorBook();
//
//        File file = new File(TEXT);
//
//
//        Mockito.when(genreService.getById(DECIMAL)).thenReturn(Optional.of(genre));
//        Mockito.when(pubHouseService.getById(DECIMAL)).thenReturn(Optional.of(pubHouse));
//        Mockito.when(tagService.getById(DECIMAL)).thenReturn(Optional.of(tag));
//        Mockito.when(authorService.getById(DECIMAL)).thenReturn(Optional.of(author));
//        Mockito.when(bookService.getBookByTitle(bookInDto.getTitle())).thenReturn(Collections.emptyList());
//        Mockito.when(bookService.addBook(bookInDto)).thenReturn(book);
//        Mockito.when(bookRepository.save(any())).thenReturn(book);
//        MockedStatic<SevenZCompress> dummy = Mockito.mockStatic(SevenZCompress.class);
//        SevenZCompress sevenZCompress = new SevenZCompress();
//        dummy.when(()-> SevenZCompress.compress(TEXT, file)).thenReturn(new SevenZCompress());
//
// //       book.bookToStringInfo();
//        assertEquals(HttpStatus.OK, bookService.addBookFromFront(bookInDto).getStatusCode());
//
//
//    }

//    @Test
//    void downloadBook() throws IOException {
//        Book book = creatorBook();
//        Mockito.when(bookRepository.findById(DECIMAL)).thenReturn(Optional.of(book));
//        MockedStatic<SevenZDecompress> mockedStatic = Mockito.mockStatic(SevenZDecompress.class);
//        assertEquals(book, bookService.downloadBook(DECIMAL));
//    }


//    @Test
//    void downloadBook() throws Exception {
//        Book book = creatorBook();
//        book.setId(DECIMAL);
//        Mockito.when(bookService.downloadBookFromFront(DECIMAL))
//                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        assertEquals(HttpStatus.OK, bookController.downloadBook(DECIMAL).getStatusCode());
//        Mockito.when(bookService.downloadBookFromFront(any()))
//                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//        assertEquals(HttpStatus.BAD_REQUEST, bookController.downloadBook(any()).getStatusCode());
//    }

//    @Test
//    void downloadBookFromFront() {
//    }

    @Test
    void deleteById() {
        Book book = creatorBook();
        Mockito.when(bookRepository.findById(DECIMAL)).thenReturn(Optional.of(book));
        assertTrue(bookService.deleteById(DECIMAL));
        Mockito.doNothing().when(bookRepository).delete(book);
        assertFalse(bookService.deleteById(null));
    }

    @Test
    void deleteByIdToFront() {
        Book book = creatorBook();
        Mockito.when(bookRepository.findById(DECIMAL)).thenReturn(Optional.of(book));
        assertEquals(HttpStatus.OK, bookService.deleteByIdToFront(DECIMAL).getStatusCode());
        Mockito.doNothing().when(bookRepository).deleteById(any());
        assertEquals(HttpStatus.BAD_REQUEST, bookService.deleteByIdToFront(null).getStatusCode());
    }
}