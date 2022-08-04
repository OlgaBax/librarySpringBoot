package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bainc.model.*;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByBookTitle();

    //  @Modifying не нужна потому что это select запрос
    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
            "join fetch b.pubHouse join fetch b.pubHouseTranslate join fetch b.tags where b.genre=:genre")
    List<Book> getByGenre(@Param("genre") Genre genre);


    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
            "join fetch b.pubHouse " +
            "join fetch b.pubHouseTranslate join fetch b.tags where b.pubHouse=:pubHouse or b.pubHouseTranslate=:pubHouse")
    List<Book> getByPubHouse(@Param("pubHouse")PubHouse pubHouse);

    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
            "join fetch b.pubHouse " +
            "join fetch b.pubHouseTranslate join fetch b.tags where :author in elements(b.authors)" )
    List<Book> getByAuthor (@Param("author")Author author);

    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
            "join fetch b.pubHouse " +
            "join fetch b.pubHouseTranslate join fetch b.tags where :tag in elements(b.tags)")
    List<Book> getByTag (@Param("tag") Tag tag);

    }
