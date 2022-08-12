package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.dto.PubHouseDto;
import ru.bainc.model.PubHouse;
import ru.bainc.repositories.PubHouseRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PubHouseService {
    private final PubHouseRepository pubHouseRepository;

    @Autowired
    public PubHouseService(PubHouseRepository pubHouseRepository) {
        this.pubHouseRepository = pubHouseRepository;
    }

    @Transactional
    public List<PubHouse> getAll() {
        return pubHouseRepository.findAll();
    }

    @Transactional
    public Optional<PubHouse> getById(Long id) {
        return pubHouseRepository.findById(id);
    }

    @Transactional
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

    @Transactional
    public ResponseEntity<List<PubHouseDto>> getAllPubHousesToFront() {
        return new ResponseEntity<>(getAll()
                .stream().map(pubHouse -> new PubHouseDto(pubHouse)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PubHouseDto> getByIdToFront(Long id) {
        PubHouse pubHouse = getById(id).orElse(null);
        if (pubHouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new PubHouseDto(pubHouse), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<PubHouseDto> getByTitleToFront(PubHouseDto pubHouseDto) {
        PubHouse pubHouse = getByPubHouseTitle(pubHouseDto.getTitle());
        if (pubHouse != null) {
            return new ResponseEntity<>(new PubHouseDto(getByPubHouseTitle(pubHouseDto.getTitle())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<PubHouseDto> addPubHouseFromFront(PubHouseDto pubHouseDto) {
        PubHouse pubHouse = getByPubHouseTitle(pubHouseDto.getTitle());
        if (pubHouse != null) {
            log.warn("Издательство с таким названием уже существует");
            return new ResponseEntity<>(new PubHouseDto(pubHouse), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new PubHouseDto(addPubHouse(new PubHouse(pubHouseDto.getTitle()))), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<PubHouseDto> deleteByIdFromFront(Long id) {
        try {
            deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteByTitleToFront(PubHouseDto pubHouseDto){
      PubHouse pubHouse = getByPubHouseTitle(pubHouseDto.getTitle());
      if(pubHouse != null){
          deleteByPubHouseTitle(pubHouse);
          return new ResponseEntity<>(HttpStatus.OK);
      } else {
          log.info("Издательства с таким названием не существует");
      }return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
