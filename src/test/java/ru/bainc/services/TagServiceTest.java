package ru.bainc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelExtensionsKt;
import ru.bainc.dto.TagDto;
import ru.bainc.model.Tag;
import ru.bainc.repositories.TagRepository;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class TagServiceTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    private TagService tagService;
    private TagRepository tagRepository;

    @BeforeEach
    void setup(){
        tagRepository = Mockito.mock(TagRepository.class);
        tagService = new TagService(tagRepository);
    }


    @Test
    void getAll() {
        Mockito.when(tagRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(tagService.getAll());
    }

    @Test
    void getById() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Mockito.when(tagRepository.findById(DECIMAL)).thenReturn(Optional.of(tag));
        assertEquals(tag, tagService.getById(DECIMAL).get());
    }

    @Test
    void getByTagTitle() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Mockito.when(tagRepository.findByTagTitle(TEXT)).thenReturn(tag);
        assertEquals(tag, tagService.getByTagTitle(TEXT));
        assertNull(tagService.getByTagTitle(null));
    }

    @Test
    void addTag() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);
        assertEquals(tag, tagService.addTag(tag));
//        Mockito.when(tagRepository.save(null)).thenReturn(null);
        assertNull(tagService.addTag(null));
    }

    @Test
    void getAllTagsToFront() {
        Mockito.when(tagRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, tagService.getAllTagsToFront().getStatusCode());
    }

    @Test
    void getByIdToFront() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Mockito.when(tagRepository.findById(DECIMAL)).thenReturn(Optional.of(tag));
        assertEquals(HttpStatus.OK, tagService.getByIdToFront(DECIMAL).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, tagService.getByIdToFront(null).getStatusCode());
    }

    @Test
    void getByTitleFromFront() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        TagDto tagDto = new TagDto(tag);
        Mockito.when(tagRepository.findByTagTitle(TEXT)).thenReturn(tag);
        assertEquals(HttpStatus.OK, tagService.getByTitleFromFront(tagDto).getStatusCode());
        Mockito.when(tagRepository.findByTagTitle(any())).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, tagService.getByTitleFromFront(tagDto).getStatusCode());
    }

    @Test
    void addTagFromFront() {
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        TagDto tagDto = new TagDto(tag);
        Mockito.when(tagRepository.save(any())).thenReturn(tag);
        assertEquals(HttpStatus.OK, tagService.addTagFromFront(tagDto).getStatusCode());
    }

    @Test
    void deleteTagById(){
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Mockito.when(tagRepository.findById(DECIMAL)).thenReturn(Optional.of(tag));
        assertTrue(tagService.deleteTagById(DECIMAL));
        Mockito.doNothing().when(tagRepository).deleteById(any());
        assertFalse(tagService.deleteTagById(null));
    }

    @Test
    void deleteByIdFromFront(){
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Mockito.when(tagRepository.findById(DECIMAL)).thenReturn(Optional.of(tag));
        assertEquals(HttpStatus.OK,(tagService.deleteByIdFromFront(DECIMAL).getStatusCode()));
        Mockito.doNothing().when(tagRepository).deleteById(any());
        assertEquals(HttpStatus.NOT_FOUND, tagService.deleteByIdFromFront(null).getStatusCode());
    }

    @Test
    void deleteTagByTitle(){
        Tag tag = new Tag(TEXT);
        tag.setId(DECIMAL);
        Mockito.when(tagRepository.findByTagTitle(tag.getTagTitle())).thenReturn(tag);
        assertTrue(tagService.deleteTagByTitle(TEXT));
        Mockito.when(tagRepository.findByTagTitle(any())).thenReturn(null);
        assertFalse(tagService.deleteTagByTitle(any()));
    }

    @Test
    void deleteByTitleToFront(){
        Tag tag =new Tag(TEXT);
        tag.setId(DECIMAL);
        TagDto tagDto = new TagDto(tag);
        Mockito.when(tagRepository.findByTagTitle(TEXT)).thenReturn(tag);
        assertEquals(HttpStatus.OK, tagService.deleteByTitleToFront(tagDto).getStatusCode());
        Mockito.when(tagRepository.findByTagTitle(any())).thenReturn(null);
        assertEquals(HttpStatus.BAD_REQUEST, tagService.deleteByTitleToFront(tagDto).getStatusCode());
    }
}