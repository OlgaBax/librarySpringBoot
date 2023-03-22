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

    @Transactional
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Transactional
    public Optional<Tag> getById(Long id) {
        return tagRepository.findById(id);
    }

    @Transactional
    public Tag getByTagTitle(String tagTitle) {
        Tag tag = tagRepository.findByTagTitle(tagTitle);
        log.info("Tag with title {} found", tagTitle);
        return tag;
    }


    @Transactional
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public ResponseEntity<List<TagDto>> getAllTagsToFront() {
        return new ResponseEntity<>
                (getAll()
                        .stream()
                        .map(tag -> new TagDto(tag))
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<TagDto> getByIdToFront(Long id) {
        Tag tag = getById(id).orElse(null);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new TagDto(tag), HttpStatus.OK);
        }
    }

    @Transactional
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
    public boolean deleteTagByTitle(String title) {
        Tag tag = tagRepository.findByTagTitle(title);
        if (tag != null) {
            tagRepository.delete(tag);
            return true;
        } else return false;
    }

    @Transactional
    public ResponseEntity<?> deleteByTitleToFront(TagDto tagDto) {
        if (deleteTagByTitle(tagDto.getTitle())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            log.info("Тага с таким названием не существует");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public boolean deleteTagById(Long id) {
        Tag tag = tagRepository.findById(id).orElse(null);
        if (tag != null) {
            tagRepository.delete(tag);
            return true;
        } else return false;
    }


    @Transactional
    public ResponseEntity<?> deleteByIdFromFront(Long id) {
        if (deleteTagById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
