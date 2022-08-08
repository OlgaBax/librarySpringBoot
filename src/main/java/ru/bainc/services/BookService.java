package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.dto.BookInDto;
import ru.bainc.dto.BookOutDto;
import ru.bainc.model.*;
import ru.bainc.repositories.BookRepository;

import java.util.List;
import java.util.UUID;
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

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.getById(id);
    }

    public Book getByBookTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    public List<Book> getByGenre(Genre genre) {
        return bookRepository.getByGenre(genre);
    }

    public List<Book> getByPubHouse(PubHouse pubHouse) {
        return bookRepository.getByPubHouse(pubHouse);
    }

    public List<Book> getByAuthor(Author author) {
        return bookRepository.getByAuthor(author);
    }

    public List<Book> getByTag(Tag tag) {
        return bookRepository.getByTag(tag);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void deleteByBookTitle(Book book) {
        bookRepository.delete(book);
    }

    public List<BookOutDto> getAllBooksToFront() {
        return getAllBooks().stream().map(book -> new BookOutDto(book)).collect(Collectors.toList());
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
                .map(tag-> tagService.getById(Long.parseLong(tag)).orElse(null))
                .collect(Collectors.toSet()));
        book.setAuthors(bookInDto.getAuthorsId()
                .stream()
                .map(author-> authorService.getById(Long.parseLong(author)))
                .collect(Collectors.toSet()));

        return bookRepository.save(book);
    }
}
