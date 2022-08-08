package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.dto.TagDto;
import ru.bainc.model.Tag;
import ru.bainc.repositories.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag getByTagTitle(String tagTitle) {
        Tag tag = tagRepository.findByTagTitle(tagTitle);
        log.info("Tag with title {} found", tagTitle);
        return tag;
    }

    @Transactional
    public void deleteTagByTitle(Tag tag) {
        tagRepository.delete(tag);
    }

    @Transactional
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    public Tag addTag(Tag tag) {
        Tag tag1 = tagRepository.save(tag);
        return tag1;
    }

    public ResponseEntity<List<TagDto>> getAllTagsToFront() {
        return new ResponseEntity<>
                (tagRepository.findAll()
                        .stream()
                        .map(tag -> new TagDto(tag))
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<TagDto> getByIdToFront(Long id) {
        Tag tag = getById(id).orElse(null);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new TagDto(tag), HttpStatus.OK);
        }
    }

    public ResponseEntity<TagDto> getByTitleFromFront(TagDto tagDto) {
        Tag tag = getByTagTitle(tagDto.getTitle());
        if (tag != null) {
            return new ResponseEntity<>(new TagDto(getByTagTitle(tagDto.getTitle())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    public ResponseEntity<TagDto> addTagFromFront(TagDto tagDto) {
        Tag tag = getByTagTitle(tagDto.getTitle());
        if (tag != null) {
            log.warn("Таг с таким названием уже существует");
            return new ResponseEntity<>(new TagDto(tag), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new TagDto(addTag(new Tag(tagDto.getTitle()))), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteByTitleToFront(TagDto tagDto) {
        Tag tag = getByTagTitle(tagDto.getTitle());
        if (tag != null) {
            deleteTagByTitle(tag);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.info("Тага с таким названием не существует");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<?> deleteByIdFromFront (Long id){
        try{
            deleteTagById(id);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
