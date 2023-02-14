package ru.bainc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.bainc.dto.PubHouseDto;
import ru.bainc.model.PubHouse;
import ru.bainc.services.PubHouseService;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class PubHouseControllerTest {
    private PubHouseService pubHouseService;
    private PubHouseController pubHouseController;
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";

    @BeforeEach
    void setup(){
        pubHouseService = Mockito.mock(PubHouseService.class);
        pubHouseController = new PubHouseController(pubHouseService);
    }

    @Test
    void getAllPubhouses() {
        Mockito.when(pubHouseService.getAllPubHousesToFront())
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));
        assertEquals(HttpStatus.OK, pubHouseController.getAllPubhouses().getStatusCode());
    }

    @Test
    void getById() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);

        PubHouseDto pubHouseDto = new PubHouseDto(pubHouse);

        Mockito.when(pubHouseService.getByIdToFront(DECIMAL))
                .thenReturn(new ResponseEntity<>(pubHouseDto,HttpStatus.OK));
        assertEquals(HttpStatus.OK, pubHouseController.getById(DECIMAL).getStatusCode());
//        Mockito.when(pubHouseService.getByIdToFront(any())).;
//        assertEquals(HttpStatus.NOT_FOUND, pubHouseController.getById(any()).getStatusCode());

    }

    @Test
    void getByTitle() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);

        PubHouseDto pubHouseDto = new PubHouseDto(pubHouse);

        Mockito.when(pubHouseService.getByTitleToFront(pubHouseDto))
                .thenReturn(new ResponseEntity<>(pubHouseDto, HttpStatus.OK));
        assertEquals(HttpStatus.OK, pubHouseController.getByTitle(pubHouseDto).getStatusCode());

    }

    @Test
    void addPubHouse() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);

        PubHouseDto pubHouseDto = new PubHouseDto(pubHouse);

        Mockito.when(pubHouseService.addPubHouseFromFront(pubHouseDto))
                .thenReturn(new ResponseEntity<>(pubHouseDto,HttpStatus.OK));
        assertEquals(HttpStatus.OK, pubHouseController.addPubHouse(pubHouseDto).getStatusCode());
    }

}