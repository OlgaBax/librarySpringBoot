package ru.bainc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookInDto {

    private String title;
    private String originalTitle;
    private String yearEdition;
    private String yearOfEditionTranslate;
    private String isbnOriginal;
    private String isbnTranslate;
    private String fileFormatBook;
    private String pathToZipBook;
    private String genreId;
    private String pubHouseId;
    private String pubHouseTranslateId;
    private List<String> tagsId;
    private List<String> authorsId;
    private String file;

}
