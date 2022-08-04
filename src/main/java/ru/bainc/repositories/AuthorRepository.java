package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bainc.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author getById(Long id);
}
