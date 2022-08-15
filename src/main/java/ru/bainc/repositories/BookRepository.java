package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bainc.model.*;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String bookTitle);

    @Query("select b from Book b where b.genre=:genre")
    List<Book> getByGenre(@Param("genre") Genre genre);

    @Query("select b from Book b where b.pubHouse=:pubHouse")
    List<Book>getByPubHouse(@Param("pubHouse") PubHouse pubHouse);


//    @Query("select distinct b from Book b join fetch b.tags where :tag in elements(b.tags)")
//    List<Book>getByTag(@Param("tag") Tag tag);

    @Query("select distinct b from Book b where b.tags = element(:tagId)")
    List<Book>getByTagIdList(@Param("tagId") Integer tagId);


    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
            "join fetch b.pubHouse " +
            "join fetch b.pubHouseTranslate join fetch b.tags where :author in elements(b.authors)" )
    List<Book> getByAuthor (@Param("author")Author author);

//    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
//            "join fetch b.pubHouse " +
//            "join fetch b.pubHouseTranslate join fetch b.tags where :tag in elements(b.tags)")
//    List<Book> getByTag (@Param("tag") Tag tag);

    }
