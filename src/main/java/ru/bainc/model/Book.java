package ru.bainc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique=true)
    private UUID uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "originaltitle")
    private String originalTitle;

    @Column(name = "yearedition")
    private String yearEdition;

    @Column(name = "isbnoriginal")
    private String isbnOriginal;

    @Column(name = "isbntranslate")
    private String isbnTranslate;

    @Column(name = "yearofeditiontranslate")
    private String yearOfEditionTranslate;

    @Column(name = "fileformatbook")
    private FileFormatBook fileFormatBook;

    @Column(name = "pathtozipbook")
    private String pathToZipBook;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pubhouse_id")
    private PubHouse pubHouse;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pubhousetranslate_id")
    private PubHouse pubHouseTranslate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_tags",
               joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
               joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

}
