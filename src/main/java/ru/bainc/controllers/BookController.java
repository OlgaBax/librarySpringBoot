package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bainc.dto.BookInDto;
import ru.bainc.dto.BookOutDto;
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

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookInDto bookInDto) {
        if (bookService.addBook(bookInDto) != null) {
            return new ResponseEntity<>("Book add", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Что-то пошло не так",HttpStatus.BAD_REQUEST);
    }

}

