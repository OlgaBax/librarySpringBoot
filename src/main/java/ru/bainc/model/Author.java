package ru.bainc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="surname")
    private String surName;

    @Column(name="name")
    private String name;

    @Column(name="middlename")
    private String middleName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    public Author(String surName) {
        this.surName = surName;
    }

    public Author(String surName, String name, String middleName) {
        this.surName = surName;
        this.name = name;
        this.middleName = middleName;
    }
}
