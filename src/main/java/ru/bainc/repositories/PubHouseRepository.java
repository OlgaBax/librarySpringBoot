package ru.bainc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bainc.model.PubHouse;

@Repository
public interface PubHouseRepository extends JpaRepository<PubHouse, Long>{

    PubHouse findByPubHouseTitle(String pubHouseTitle);

}
