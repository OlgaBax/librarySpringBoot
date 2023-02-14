package ru.bainc.dto;

import org.junit.jupiter.api.Test;
import ru.bainc.model.Tag;

import static org.junit.jupiter.api.Assertions.*;

class TagDtoTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "Test";
    private TagDto tagDto = new TagDto();

    @Test
    void setId() {
        tagDto.setId(DECIMAL);
        assertEquals(DECIMAL, tagDto.getId());
    }

    @Test
    void setTitle() {
        tagDto.setTitle(TEXT);
        assertEquals(TEXT, tagDto.getTitle());
    }

    @Test
    void testConstructor(){
        Tag tag = new Tag();
        tag.setId(DECIMAL);
        tag.setTagTitle(TEXT);

        tagDto=new TagDto(tag);
        assertEquals(DECIMAL, tagDto.getId());
        assertEquals(TEXT, tagDto.getTitle());
    }
}