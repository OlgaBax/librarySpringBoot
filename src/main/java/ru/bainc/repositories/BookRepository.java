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

    @Query("select b from Book b where b.genre=:genre")
    List<Book> getByGenre(@Param("genre") Genre genre);

    @Query("select b from Book b where b.pubHouse=:pubHouse")
    List<Book> getByPubHouse(@Param("pubHouse") PubHouse pubHouse);

//___________________________________________________________________________________________//
// работает
    @Query(value = "select * from books b where b.id in (select book_id from books_tags where tag_id in(:idTag)) order by b.id", nativeQuery = true)
    List<Book> getByTagId(@Param("idTag") Long idTag);

//работает
//@Query(value = "select * from books b where ? in (select tag_id from books_tags where book_id=b.id) order by b.id", nativeQuery = true)
//List<Book> getByTagId(@Param("idTag") Long id);



//    @Query(value = "select * from books b inner join books_tags on tag_id in(:idTag)", nativeQuery = true)
//    List<Book>getByTagId(@Param("idTag")List<Long> id);

//    @Query("select b.id id from Book b  " +
//            "right join fetch b.tags t where :tag in t")
//    List<Long>getByTagsList(@Param("tag") Tag tag);


//    @Query("select b from Book b where b.id in :idList")
//    List<Book>getByListId(@Param("idList") List<Integer> idList);


//    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
//            "join fetch b.pubHouse " +
//            "join fetch b.pubHouseTranslate join fetch b.tags where :author in elements(b.authors)" )
//    List<Book> getByAuthor (@Param("author")Author author);

//    @Query(value = "select distinct b from Book b join fetch b.authors join fetch b.genre " +
//            "join fetch b.pubHouse " +
//            "join fetch b.pubHouseTranslate join fetch b.tags where :tag in elements(b.tags)")
//    List<Book> getByTag (@Param("tag") Tag tag);

}