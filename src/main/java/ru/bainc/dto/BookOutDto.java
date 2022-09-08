package ru.bainc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.model.Book;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                .map(author -> author.getSurName() + " "
                        + author.getName() + " "
                        + (author.getMiddleName() == null ? " " : author.getMiddleName()))
                .collect(Collectors.toSet());
    }

//    public static String setStringToString(Set<String> stringSet) {
//        StringBuilder sb = new StringBuilder();
//        stringSet.forEach(s -> sb.append(s).append("; "));
//        return sb.toString();
//    }
//
//    public String bookToStringInfo() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("UUID: ").append(this.uuid).append("\n");
//        builder.append("Название: ").append(this.title).append("\n");
//        builder.append("Оригинальное название: ").append(this.originalTitle).append("\n");
//        builder.append("Год издания оригинала: ").append(this.yearEdition).append("\n");
//        builder.append("Год издания перевода: ").append(this.yearOfEditionTranslate).append("\n");
//        builder.append("ISBN оригинала: ").append(this.isbnOriginal).append("\n");
//        builder.append("ISBN перевода: ").append(this.isbnTranslate).append("\n");
//        builder.append("Формат книги: ").append(this.fileFormatBook).append("\n");
//        builder.append("Имя файла архива: ").append(this.pathToZipBook).append("\n");
//        builder.append("Жанр: ").append(this.genre).append("\n");
//        builder.append("Издательство оригинала: ").append(this.pubHouse).append("\n");
//        builder.append("Издательство перевода: ").append(this.pubHouseTranslate).append("\n");
//        builder.append("Авторы: ").append(setStringToString(this.authors)).append("\n");
//        builder.append("Теги: ").append(setStringToString(this.tags)).append("\n");
//        builder.append("\n\n ");
//        return builder.toString();
//    }

}
