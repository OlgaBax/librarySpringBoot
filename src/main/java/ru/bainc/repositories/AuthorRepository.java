package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bainc.model.Author;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findBySurName(String surName);

    Author getById(Long id);

    @Query("select a from Author a where a.surName=:surName and a.name=:name and a.middleName=:middleName")
    Author findByFio(@Param("surName") String surName, @Param("name") String name, @Param("middleName")String middleName );


}
