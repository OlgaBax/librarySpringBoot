package ru.bainc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.model.Tag;

@Getter
@Setter
@NoArgsConstructor
public class TagDto {

    private Long id;
    private String tagTitle;

    public TagDto(Tag tag){
        this.id = tag.getId();
        this.tagTitle = tag.getTagTitle();
    }

}
