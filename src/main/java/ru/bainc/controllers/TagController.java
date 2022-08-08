package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bainc.dto.TagDto;
import ru.bainc.services.TagService;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(){
       return tagService.getAllTagsToFront();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getById (@PathVariable Long id){
        return tagService.getByIdToFront(id);
    }

    @GetMapping("/title")
    public ResponseEntity<TagDto> getByTitle(@RequestBody TagDto tagDto){
        return tagService.getByTitleFromFront(tagDto);
    }

    @PostMapping
    public ResponseEntity<TagDto> addTag (@RequestBody TagDto tagDto){
        return  tagService.addTagFromFront(tagDto);
    }


    @DeleteMapping("/title")
    public ResponseEntity<?> deleteByTitle(@RequestBody TagDto tagDto){
        return tagService.deleteByTitleToFront(tagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById (@PathVariable Long id){
           return tagService.deleteByIdFromFront(id);
    }


}
