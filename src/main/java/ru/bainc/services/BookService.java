package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.dto.BookInDto;
import ru.bainc.dto.BookOutDto;
import ru.bainc.dto.BookSearchDto;
import ru.bainc.model.*;
import ru.bainc.repositories.BookRepository;
import ru.bainc.util.Base64Utils;
import ru.bainc.util.SevenZCompress;
import ru.bainc.util.SevenZDecompress;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @Value("${defaultbookinfofilename}")
    private String bookInfoFileName;

    @Value("${filebooktemp}")
    private String bookTemp;

    @Value("${filebookstorage}")
    private String bookStorage;


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
    public List<Book> getBookByTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @Transactional
    public List<Book> getBookByPartTitle(String partTitle) {
        return bookRepository.findBookByPartTitle("%" + partTitle.toLowerCase() + "%");
    }


    @Transactional
    public ResponseEntity<List<BookOutDto>> getByTitleFromFront(BookOutDto bookOutDto) {
        return new ResponseEntity<>(getBookByTitle(bookOutDto.getTitle())
                .stream()
                .map(book -> new BookOutDto(book)).collect(Collectors.toList()), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<List<BookOutDto>> getBookByPartTitleFromFront(BookSearchDto bookSearchDto) {
        return new ResponseEntity<>(getBookByPartTitle(bookSearchDto.getPartTitle())
                .stream()
                .map(book -> new BookOutDto(book))
                .sorted((x1, x2) -> x1.getId().compareTo(x2.getId()))
                .collect(Collectors.toList())
                , HttpStatus.OK);
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
    public ResponseEntity<List<BookOutDto>> getByTagFromFront(BookSearchDto bookSearchDto) {
        Set<Book> books = new HashSet<>();
        for (Long id : bookSearchDto.getTagsListId()) {
            List<Book> tempList = bookRepository.getByTagId(id);
            books.addAll(tempList);
        }
        if (books == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(books
                    .stream()
                    .map(book -> new BookOutDto(book)).sorted((x1, x2) -> x1.getId().compareTo(x2.getId()))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
    }

    @Transactional
    public List<Book> getByPartAuthorSurname(String partSurname) {
        return bookRepository.getBookByPartAuthorSurname("%" + partSurname.toLowerCase() + "%");
    }

    @Transactional
    public ResponseEntity<List<BookOutDto>> getByPartAuthorSurnameFromFront(BookSearchDto bookSearchDto) {
        return new ResponseEntity<>(getByPartAuthorSurname(bookSearchDto.getPartSurname())
                .stream()
                .map(book -> new BookOutDto(book))
                .sorted((x1, x2) -> x1.getId().compareTo(x2.getId()))
                .collect(Collectors.toList()), HttpStatus.OK);
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

    public boolean saveInfoAboutBookInFile(Book book, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(book.bookToStringInfo());
            writer.flush();
            log.info("File {} saved", path);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    @Transactional
    public ResponseEntity<BookOutDto> addBookFromFront(BookInDto bookInDto) throws Exception {
        List<Book> books = getBookByTitle(bookInDto.getTitle());
        Book newBook = new Book();
        Boolean flag = false;
        if (!books.isEmpty()) {
            for (Book element : books) {
                if (!element.getAuthors()
                        .stream()
                        .map(x -> x.getId())
                        .collect(Collectors.toList())
                        .containsAll((bookInDto.getAuthorsId())
                                .stream()
                                .map(Long::valueOf)
                                .collect(Collectors.toList()))) {
                    flag = true;
                } else if (element.getPubHouse().getId() != Long.parseLong(bookInDto.getPubHouseId())) {
                    flag = true;
                } else if (!element.getYearEdition().equals(bookInDto.getYearEdition())) {
                    flag = true;
                } else {
                    log.info("Такая книга уже существует");
                    return new ResponseEntity<>(new BookOutDto(element), HttpStatus.OK);
                }
            }
            if (flag) {
                newBook = addBook(bookInDto);
                saveInfoAboutBookInFile(newBook, bookTemp + File.separator + bookInfoFileName);
            }
        } else newBook = addBook(bookInDto);
        saveInfoAboutBookInFile(newBook, bookTemp + File.separator + bookInfoFileName);

        if (bookInDto.getFile() != null) {
            if (Base64Utils.base64ToFile(bookInDto.getFile(),
                    bookTemp + File.separator + newBook.getUuid()
                            + "." + newBook.getFileFormatBook().toString().toLowerCase())) {
                log.info("File book save: {}", bookTemp + File.separator + newBook.getUuid()
                        + "." + newBook.getFileFormatBook().toString().toLowerCase());
            }
        }

        File[] files =
                {new File(bookTemp + File.separator + newBook.getUuid() + "."
                        + newBook.getFileFormatBook().toString().toLowerCase()),
                        new File(bookTemp + File.separator + bookInfoFileName)};


        SevenZCompress.compress(bookTemp + File.separator + newBook.getUuid() + "." + "7z", files);
        SevenZCompress.copyFile7zOutBookTemp(new File(bookTemp + File.separator + newBook.getUuid() + "." + "7z"),
                new File(bookStorage + File.separator + newBook.getUuid() + "." + "7z"));

        SevenZCompress.cleanBookTemp(new File(bookTemp));
        newBook.setPathToZipBook(newBook.getUuid() + "." + "7z");
        return new ResponseEntity<>(new BookOutDto(newBook), HttpStatus.OK);
    }

    @Transactional
    public Book downloadBook(Long id) {
        Book book = bookRepository.findById(id).get();
        if (book != null) {
            try {
                SevenZDecompress.decompress(bookStorage + File.separator + book.getPathToZipBook(),
                        bookTemp);
                log.info("Книга распакована");
            } catch (IOException e) {
                log.error("File not found exception");
            }
        }
        return book;
    }

    @Transactional
    public ResponseEntity<String> downloadBookFromFront(Long id) throws Exception {
        Book book = downloadBook(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            ResponseEntity<String> response = new ResponseEntity<>
                    (Base64Utils.fileToBase64(bookTemp + File.separator + book.getUuid() + "."
                    + book.getFileFormatBook().toString().toLowerCase()), HttpStatus.OK);

            SevenZCompress.cleanBookTemp(new File(bookTemp));
            return response;
        }
    }
}


