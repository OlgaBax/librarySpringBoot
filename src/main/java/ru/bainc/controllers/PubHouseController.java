package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bainc.dto.PubHouseDto;
import ru.bainc.model.PubHouse;
import ru.bainc.services.PubHouseService;

import java.util.List;

@RestController
@RequestMapping("/pubhouse")
public class PubHouseController {

    private final PubHouseService pubHouseService;

    @Autowired
    public PubHouseController(PubHouseService pubHouseService) {
        this.pubHouseService = pubHouseService;
    }

    @GetMapping
    public ResponseEntity<List<PubHouseDto>> getAllPubhouses (){
        return pubHouseService.getAllPubHousesToFront();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PubHouseDto> getById(@PathVariable Long id){
        return pubHouseService.getByIdToFront(id);
    }

    @GetMapping("/title")
    public ResponseEntity<PubHouseDto> getByTitle(@RequestBody PubHouseDto pubHouseDto){
        return pubHouseService.getByTitleToFront(pubHouseDto);
    }

    @PostMapping
    public ResponseEntity<PubHouseDto> addPubHouse(@RequestBody PubHouseDto pubHouseDto){
        return pubHouseService.addPubHouseFromFront(pubHouseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PubHouseDto> deleteById(@PathVariable Long id){
        return pubHouseService.deleteByIdFromFront(id);
    }

    @DeleteMapping("/title")
    public ResponseEntity<?> deleteByTitle(@RequestBody PubHouseDto pubHouseDto){
        return pubHouseService.deleteByTitleToFront(pubHouseDto);
    }

}
