package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.model.*;
import ru.bainc.repositories.BookRepository;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks (){
        return bookRepository.findAll();
    }

    public Book getById(Long id){
        return bookRepository.getById(id);
    }

    public Book getByBookTitle(String bookTitle){
        return bookRepository.findByTitle(bookTitle);
    }

    public List<Book> getByGenre(Genre genre){
        return bookRepository.getByGenre(genre);
    }

    public List<Book> getByPubHouse(PubHouse pubHouse){
        return bookRepository.getByPubHouse(pubHouse);
    }

    public List<Book> getByAuthor(Author author){
        return bookRepository.getByAuthor(author);
    }

    public List<Book> getByTag(Tag tag){
        return bookRepository.getByTag(tag);
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }

    public void deleteByBookTitle(Book book){
        bookRepository.delete(book);
    }
}
