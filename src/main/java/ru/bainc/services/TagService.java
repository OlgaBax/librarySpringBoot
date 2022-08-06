package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.model.Tag;
import ru.bainc.repositories.TagRepository;
import java.util.List;

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

    public Tag getById(Long id){
        return tagRepository.getById(id);
    }

    public Tag getByTagTitle(String tagTitle){
        Tag tag = tagRepository.findByTagTitle(tagTitle);
        log.info("Tag with title {} found", tagTitle);
        return tag;
    }

    @Transactional
    public void deleteTag(Tag tag){
        tagRepository.delete(tag);
    }

    @Transactional
    public void deleteById(Long id){
        tagRepository.getById(id);
    }

    @Transactional
    public Tag addTag(Tag tag){
        Tag tag1 = tagRepository.save(tag);
        return tag1;
    }

}
