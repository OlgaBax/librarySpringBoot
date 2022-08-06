package ru.bainc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bainc.dto.GenreDto;
import ru.bainc.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres (){
        return genreService.getAllGenresToFront();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getById(@PathVariable Long id){
        return genreService.getByIdToFront(id);
    }

    @GetMapping("/title")
    public ResponseEntity<GenreDto> getByTitle(@RequestBody GenreDto genreDto){
        return genreService.getByTitleToFront(genreDto);
    }

    @PostMapping
    public ResponseEntity<GenreDto> addGenre(@RequestBody GenreDto genreDto){
        return genreService.addGenreFromFront(genreDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return genreService.deleteByIdFromFront(id);
    }

    @DeleteMapping("/title")
    public ResponseEntity<?> deleteByTitle(@RequestBody GenreDto genreDto){
        return genreService.deleteByTitleToFront(genreDto);
    }


}
