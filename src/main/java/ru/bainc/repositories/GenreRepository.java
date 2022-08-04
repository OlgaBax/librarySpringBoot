package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bainc.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByGenreTitle (String genreTitle);


}
