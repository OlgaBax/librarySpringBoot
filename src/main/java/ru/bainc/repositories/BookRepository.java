package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bainc.model.*;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String bookTitle);

    //hql запрос
    @Query("select b from Book b where b.title like %:partTitle%")
    List<Book> findBookByPartTitle(@Param("partTitle") String partTitle);

//    нативный запрос. Работает.
//    @Query(value = "select * from books b where b.title like %:partTitle%", nativeQuery = true)
//    List<Book> findBookByPartTitle(@Param("partTitle") String partTitle);



    @Query("select b from Book b where b.genre=:genre")
    List<Book> getByGenre(@Param("genre") Genre genre);

    @Query("select b from Book b where b.pubHouse=:pubHouse")
    List<Book> getByPubHouse(@Param("pubHouse") PubHouse pubHouse);

//    мой вариант
//    @Query(value = "select * from books b where b.id in (select book_id from books_tags where tag_id=:idTag)", nativeQuery = true)
//    List<Book> getByTagId(@Param("idTag") Long idTag);

    // Вариант Алексея
    @Query(value = "select * from books b where :idTag in (select tag_id from books_tags where book_id=b.id)", nativeQuery = true)
    List<Book> getByTagId(@Param("idTag") Long idTag);
//______________________________________________________________________________________________________//
    @Query(value = "select * from books b where b.id in " +
            "(select book_id from books_authors where author_id in " +
            "(select a.id from authors a where a.surname like %:partSurname%))", nativeQuery = true)
    List<Book>getBookByPartAuthorSurname(@Param("partSurname") String partSurname);

//    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
//            "join fetch b.pubHouse " +
//            "join fetch b.pubHouseTranslate join fetch b.tags where :author in elements(b.authors)" )
//    List<Book> getByAuthor (@Param("author")Author author);

//    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
//            "join fetch b.pubHouse " +
//            "join fetch b.pubHouseTranslate join fetch b.tags where :tag in elements(b.tags)")
//    List<Book> getByTag (@Param("tag") Tag tag);

}