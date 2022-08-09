package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bainc.dto.AuthorDto;
import ru.bainc.services.AuthorService;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){
        return authorService.getAllAuthorToFront();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id){
        return authorService.getByIdFromFront(id);
    }

    @GetMapping("/surname")
    public ResponseEntity<AuthorDto> getBySurName(@RequestBody AuthorDto authorDto){
        return authorService.getBySurNameToFront(authorDto);
    }

    @GetMapping("/name")
    public ResponseEntity<AuthorDto> getByName(@RequestBody AuthorDto authorDto){
        return authorService.getByNameFromFront(authorDto);
    }

   @GetMapping("/middlename")
    public ResponseEntity<AuthorDto> getByMiddleName(@RequestBody AuthorDto authorDto){
        return authorService.getByMiddleNameFromFront(authorDto);
   }

   @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto){
        return authorService.addAuthorFromFront(authorDto);
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return authorService.deleteByIdFromFront(id);
   }

   @DeleteMapping("/surname")
    public ResponseEntity<?> deleteBySurname(@RequestBody AuthorDto authorDto){
        return authorService.deleteBySurNameToFront(authorDto);
   }
}
