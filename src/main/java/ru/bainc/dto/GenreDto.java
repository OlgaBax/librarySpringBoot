package ru.bainc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.model.Genre;

@Getter
@Setter
@NoArgsConstructor
public class GenreDto {

    private Long id;
    private String title;


    public GenreDto(Genre genre) {
        this.id = genre.getId();
        this.title = genre.getGenreTitle();
    }
}
