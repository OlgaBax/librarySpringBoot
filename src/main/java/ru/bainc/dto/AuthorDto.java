package ru.bainc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import ru.bainc.model.Author;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto {

    private Long id;
    private String name;
    private String surName;
    private String middleName;

    public AuthorDto (Author author){
        this.id = author.getId();
        this.name = author.getName();
        this.middleName = author.getMiddleName();
        this.surName = author.getSurName() ;
    }


}
