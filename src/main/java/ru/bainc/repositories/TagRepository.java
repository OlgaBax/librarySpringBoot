package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bainc.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

     Tag findByTagTitle (String tagTitle);
}
