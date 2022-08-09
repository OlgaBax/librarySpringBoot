package ru.bainc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.model.Book;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookOutDto {
    private Long id;
    private UUID uuid;
    private String title;
    private String originalTitle;
    private String yearEdition;
    private String yearOfEditionTranslate;
    private String isbnOriginal;
    private String isbnTranslate;
    private String fileFormatBook;
    private String pathToZipBook;
    private String genre;
    private String pubHouse;
    private String pubHouseTranslate;
    private Set<String> tags;
    private Set<String> authors;

    public BookOutDto(Book book) {
        this.id = book.getId();
        this.uuid = book.getUuid();
        this.title = book.getTitle();
        this.originalTitle = book.getOriginalTitle();
        this.yearEdition = book.getYearEdition();
        this.yearOfEditionTranslate = book.getYearOfEditionTranslate();
        this.isbnOriginal = book.getIsbnOriginal();
        this.isbnTranslate = book.getIsbnTranslate();
        this.fileFormatBook = book.getFileFormatBook().name();
        this.pathToZipBook = book.getPathToZipBook();
        this.genre = book.getGenre().getGenreTitle();
        this.pubHouse = book.getPubHouse().getPubHouseTitle();
        this.pubHouseTranslate = book.getPubHouseTranslate().getPubHouseTitle();
        this.tags = book.getTags().stream().map(tag -> tag.getTagTitle()).collect(Collectors.toSet());
        this.authors = book.getAuthors()
                .stream()
                .map(author ->author.getSurName() + " "
                + author.getName() + " "
                + (author.getMiddleName() == null? " " : author.getMiddleName()))
                .collect(Collectors.toSet());
    }
}
