package ru.bainc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.bainc.dto.PubHouseDto;
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<PubHouseDto>> getAllPubhouses (){
        return pubHouseService.getAllPubHousesToFront();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<PubHouseDto> getById(@PathVariable Long id){
        return pubHouseService.getByIdToFront(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/title")
    public ResponseEntity<PubHouseDto> getByTitle(@RequestBody PubHouseDto pubHouseDto){
        return pubHouseService.getByTitleToFront(pubHouseDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
       public ResponseEntity<PubHouseDto> addPubHouse(@RequestBody PubHouseDto pubHouseDto){
        return pubHouseService.addPubHouseFromFront(pubHouseDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<PubHouseDto> deleteById(@PathVariable Long id){
        return pubHouseService.deleteByIdFromFront(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/title")
    public ResponseEntity<?> deleteByTitle(@RequestBody PubHouseDto pubHouseDto){
        return pubHouseService.deleteByTitleToFront(pubHouseDto);
    }

}
