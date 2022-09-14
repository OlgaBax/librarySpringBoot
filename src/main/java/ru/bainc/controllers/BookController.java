package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.bainc.dto.BookInDto;
import ru.bainc.dto.BookOutDto;
import ru.bainc.dto.BookSearchDto;
import ru.bainc.services.BookService;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<BookOutDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooksToFront(), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/title")
    public ResponseEntity<List<BookOutDto>>getByTitle(@RequestBody BookOutDto bookOutDto){
        return bookService.getByTitleFromFront(bookOutDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/partTitle")
    public ResponseEntity<List<BookOutDto>>getByPartTitle(@RequestBody BookSearchDto bookSearchDto){
        return bookService.getBookByPartTitleFromFront(bookSearchDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<BookOutDto> getById(@PathVariable Long id){
        return bookService.getByIdFromFront(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/genre")
    public ResponseEntity<List<BookOutDto>>getByGenre(@RequestBody BookOutDto bookOutDto){
        return bookService.getByGenreFromFront(bookOutDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/pubhouse")
    public ResponseEntity<List<BookOutDto>>getByPubHouse(@RequestBody BookOutDto bookOutDto){
        return bookService.getByPubHouseFromFront(bookOutDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/tag")
    public ResponseEntity<List<BookOutDto>>getByTag(@RequestBody BookSearchDto bookSearchDto){
        return bookService.getByTagFromFront(bookSearchDto);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/partAuthorSurname")
    public ResponseEntity<List<BookOutDto>>getByPartAuthorSurname(@RequestBody BookSearchDto bookSearchDto){
        return bookService.getByPartAuthorSurnameFromFront(bookSearchDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        return bookService.deleteByIdToFront(id);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BookOutDto> addBook(@RequestBody BookInDto bookInDto) throws Exception {
        return bookService.addBookFromFront(bookInDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadBook (@PathVariable Long id) throws Exception {
        return bookService.downloadBookFromFront(id);
    }
}

