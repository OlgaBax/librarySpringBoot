package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.model.PubHouse;
import ru.bainc.repositories.PubHouseRepository;

import java.util.List;

@Slf4j
@Service
public class PubHouseService {
    private final PubHouseRepository pubHouseRepository;

    @Autowired
    public PubHouseService(PubHouseRepository pubHouseRepository) {
        this.pubHouseRepository = pubHouseRepository;
    }

    public List<PubHouse> getAll() {
        return pubHouseRepository.findAll();
    }

    public PubHouse getById(Long id) {
        return pubHouseRepository.getById(id);
    }

    public PubHouse getByPubHouseTitle(String pubHouseTitle) {
        return pubHouseRepository.findByPubHouseTitle(pubHouseTitle);
    }

    @Transactional
    public PubHouse addPubHouse(PubHouse pubHouse) {
        PubHouse pubHouse1 = pubHouseRepository.save(pubHouse);
        return pubHouse1;
    }

    @Transactional
    public void deleteByPubHouseTitle(PubHouse pubHouse) {
        pubHouseRepository.delete(pubHouse);
    }

    @Transactional
    public void deleteById(Long id) {
        pubHouseRepository.deleteById(id);
    }
}
