package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(){
       return tagService.getAllTagsToFront();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getById (@PathVariable Long id){
        return tagService.getByIdToFront(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/title")
    public ResponseEntity<TagDto> getByTitle(@RequestBody TagDto tagDto){
        return tagService.getByTitleFromFront(tagDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TagDto> addTag (@RequestBody TagDto tagDto){
        return  tagService.addTagFromFront(tagDto);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/title")
    public ResponseEntity<?> deleteByTitle(@RequestBody TagDto tagDto){
        return tagService.deleteByTitleToFront(tagDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById (@PathVariable Long id){
           return tagService.deleteByIdFromFront(id);
    }


}
