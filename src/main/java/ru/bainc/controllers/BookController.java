package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<BookOutDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooksToFront(), HttpStatus.OK);
    }
    @GetMapping("/title")
    public ResponseEntity<List<BookOutDto>>getByTitle(@RequestBody BookOutDto bookOutDto){
        return bookService.getByTitleFromFront(bookOutDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookOutDto> getById(@PathVariable Long id){
        return bookService.getByIdFromFront(id);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<BookOutDto>>getByGenre(@RequestBody BookOutDto bookOutDto){
        return bookService.getByGenreFromFront(bookOutDto);
    }

    @GetMapping("/pubhouse")
    public ResponseEntity<List<BookOutDto>>getByPubHouse(@RequestBody BookOutDto bookOutDto){
        return bookService.getByPubHouseFromFront(bookOutDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        return bookService.deleteByIdToFront(id);
    }

    //______________________________________________________________________________________________________//
    @GetMapping("/tag")
    public ResponseEntity<List<BookOutDto>>getByTag(@RequestBody BookSearchDto bookSearchDto){
        return bookService.getByTagFromFront(bookSearchDto);
    }
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookInDto bookInDto) {
        if (bookService.addBook(bookInDto) != null) {
            return new ResponseEntity<>("Book add", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Что-то пошло не так",HttpStatus.BAD_REQUEST);
    }

}

