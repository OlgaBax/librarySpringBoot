package ru.bainc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.bainc.dto.TagDto;
import ru.bainc.model.Tag;
import ru.bainc.services.TagService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TagControllerTest {
    private TagController tagController;
    private TagService tagService;
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";

    @BeforeEach
    void setup(){
        tagService = Mockito.mock(TagService.class);
        tagController = new TagController(tagService);
    }

    @Test
    void getAllTags() {
        Mockito.when(tagService.getAllTagsToFront())
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));
        assertEquals(HttpStatus.OK, tagController.getAllTags().getStatusCode());
    }

    @Test
    void getById() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);

        TagDto tagDto = new TagDto(tag);
        Mockito.when(tagService.getByIdToFront(DECIMAL))
                .thenReturn(new ResponseEntity<>(tagDto,HttpStatus.OK));
        assertEquals(HttpStatus.OK, tagController.getById(DECIMAL).getStatusCode());
    }

    @Test
    void getByTitle() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);

        TagDto tagDto = new TagDto(tag);

        Mockito.when(tagService.getByTitleFromFront(tagDto)).thenReturn(new ResponseEntity<>(tagDto,HttpStatus.OK));
        assertEquals(HttpStatus.OK, tagController.getByTitle(tagDto).getStatusCode());
    }

    @Test
    void addTag() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        TagDto tagDto = new TagDto(tag);
        Mockito.when(tagService.addTagFromFront(tagDto)).thenReturn(new ResponseEntity<>(tagDto, HttpStatus.OK));
        assertEquals(HttpStatus.OK, tagController.addTag(tagDto).getStatusCode());
    }

    @Test
    void deleteById(){
        Mockito.when(tagService.deleteByIdFromFront(DECIMAL)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(HttpStatus.OK, tagController.deleteById(DECIMAL).getStatusCode());
    }

    @Test
    void deleteByTitle(){
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        TagDto tagDto = new TagDto(tag);
        Mockito.when(tagService.deleteByTitleToFront(tagDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(HttpStatus.OK, tagController.deleteByTitle(tagDto).getStatusCode());
    }
}