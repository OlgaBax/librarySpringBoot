package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.dto.BookInDto;
import ru.bainc.dto.BookOutDto;
import ru.bainc.dto.BookSearchDto;
import ru.bainc.model.*;
import ru.bainc.repositories.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final TagService tagService;
    private final AuthorService authorService;
    private final PubHouseService pubHouseService;
    private final GenreService genreService;

    @Autowired
    public BookService(BookRepository bookRepository, TagService tagService,
                       AuthorService authorService, PubHouseService pubHouseService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.tagService = tagService;
        this.authorService = authorService;
        this.pubHouseService = pubHouseService;
        this.genreService = genreService;
    }

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public List<BookOutDto> getAllBooksToFront() {
        return getAllBooks().stream().map(book -> new BookOutDto(book)).collect(Collectors.toList());
    }

    @Transactional
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<BookOutDto> getByIdFromFront(Long id) {
        Book book = getById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new BookOutDto(book), HttpStatus.OK);
        }
    }

    @Transactional
    public List<Book> getByBookByTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @Transactional
    public ResponseEntity<List<BookOutDto>> getByTitleFromFront(BookOutDto bookOutDto) {
        return new ResponseEntity<>(getByBookByTitle(bookOutDto.getTitle())
                .stream()
                .map(book -> new BookOutDto(book)).collect(Collectors.toList()), HttpStatus.OK);
    }


    @Transactional
    public List<Book> getBookByGenre(Genre genre) {
        return bookRepository.getByGenre(genre);
    }

    @Transactional
    public ResponseEntity<List<BookOutDto>> getByGenreFromFront(BookOutDto bookOutDto) {
        Genre genre = genreService.getByGenreTitle(bookOutDto.getGenre());
        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(getBookByGenre(genre)
                    .stream()
                    .map(book -> new BookOutDto(book))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
    }

    @Transactional
    public List<Book> getByPubHouse(PubHouse pubHouse) {
        return bookRepository.getByPubHouse(pubHouse);
    }

    @Transactional
    public ResponseEntity<List<BookOutDto>> getByPubHouseFromFront(BookOutDto bookOutDto) {
        PubHouse pubHouse = pubHouseService.getByPubHouseTitle(bookOutDto.getPubHouse());
        if (pubHouse == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(getByPubHouse(pubHouse)
                    .stream()
                    .map(book -> new BookOutDto(book))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public ResponseEntity<?> deleteByIdToFront(Long id) {
        try {
            deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //_____________________________________________________________________________________________//
    @Transactional
    public ResponseEntity<List<BookOutDto>> getByTagFromFront(BookSearchDto bookSearchDto) {
        List<Book> books;
        books = bookRepository.getByTagId(bookSearchDto.getIdTag());
        if (books == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(books.stream()
            .map(book -> new BookOutDto(book)).collect(Collectors.toList()), HttpStatus.OK);
        }
    }

//        List<Tag> tags = new ArrayList<>();
//        for (Long id : bookSearchDto.getTagsListId()) {
//            Tag tag = tagService.getById(id).get();//(id.longValue()).get();
//            tags.add(tag);
//        }
//        List<Book> books;
//        Set<Long> idTags = new HashSet<>();
//        for (Tag tag : tags) {
//        }
//        idTags.addAll(bookRepository.getByTagsList(tag));
//
//        books = bookRepository.getByListId(new ArrayList<>(idTags));
//        if (books == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(books
//                    .stream()
//                    .map(book -> new BookOutDto(book))
//                    .collect(Collectors.toList()), HttpStatus.OK);
//        }
//    }

//    @Transactional
//    public List<Book> getByAuthor(Author author) {
//        return bookRepository.getByAuthor(author);
//    }


    @Transactional
    public void deleteByBookTitle(Book book) {
        bookRepository.delete(book);
    }


    @Transactional
    public Book addBook(BookInDto bookInDto) {
        Book book = new Book();
        book.setUuid(UUID.randomUUID());
        book.setTitle(bookInDto.getTitle());
        book.setOriginalTitle(bookInDto.getOriginalTitle());
        book.setYearEdition(bookInDto.getYearEdition());
        book.setYearOfEditionTranslate(bookInDto.getYearOfEditionTranslate());
        book.setIsbnOriginal(bookInDto.getIsbnOriginal());
        book.setIsbnTranslate(bookInDto.getIsbnTranslate());
        book.setFileFormatBook(FileFormatBook.valueOf(bookInDto.getFileFormatBook()));
        book.setPathToZipBook(bookInDto.getPathToZipBook());
        book.setGenre(genreService.getById(Long.parseLong(bookInDto.getGenreId())).orElse(null));//todo вставить проверку наличия жанра в базе
        book.setPubHouse(pubHouseService.getById(Long.parseLong(bookInDto.getPubHouseId())).orElse(null));
        book.setPubHouseTranslate(pubHouseService.getById(Long.parseLong(bookInDto.getPubHouseTranslateId())).orElse(null));
        book.setTags(bookInDto.getTagsId().stream()
                .map(tag -> tagService.getById(Long.parseLong(tag)).orElse(null))
                .collect(Collectors.toSet()));
        book.setAuthors(bookInDto.getAuthorsId()
                .stream()
                .map(author -> authorService.getById(Long.parseLong(author)).orElse(null))
                .collect(Collectors.toSet()));

        return bookRepository.save(book);
    }
}
