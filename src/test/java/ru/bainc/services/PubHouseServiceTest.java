package ru.bainc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import ru.bainc.dto.PubHouseDto;
import ru.bainc.model.PubHouse;
import ru.bainc.repositories.PubHouseRepository;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PubHouseServiceTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "test";
    private PubHouseService pubHouseService;
    private PubHouseRepository pubHouseRepository;


    @BeforeEach
    void setup(){
        pubHouseRepository = Mockito.mock(PubHouseRepository.class);
        pubHouseService = new PubHouseService(pubHouseRepository);
    }

    @Test
    void getAll() {
        Mockito.when(pubHouseRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(pubHouseService.getAll());
    }

    @Test
    void getById() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        Mockito.when(pubHouseRepository.findById(DECIMAL)).thenReturn(Optional.of(pubHouse));
        assertEquals(pubHouse, pubHouseService.getById(DECIMAL).get());
    }

    @Test
    void getByPubHouseTitle() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        Mockito.when(pubHouseRepository.findByPubHouseTitle(TEXT)).thenReturn(pubHouse);
        assertEquals(pubHouse, pubHouseService.getByPubHouseTitle(TEXT));
        assertNull(pubHouseService.getByPubHouseTitle(null));
    }

    @Test
    void addPubHouse() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        Mockito.when(pubHouseRepository.save(pubHouse)).thenReturn(pubHouse);
        assertEquals(pubHouse, pubHouseService.addPubHouse(pubHouse));
        Mockito.when(pubHouseRepository.save(null)).thenReturn(null);
        assertNull(pubHouseService.addPubHouse(null));
    }

    @Test
    void getAllPubHousesToFront() {
        Mockito.when(pubHouseRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, pubHouseService.getAllPubHousesToFront().getStatusCode());
    }

    @Test
    void getByIdToFront() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        Mockito.when(pubHouseRepository.findById(DECIMAL)).thenReturn(Optional.of(pubHouse));
        assertEquals(HttpStatus.NOT_FOUND, pubHouseService.getByIdToFront(null).getStatusCode());
        assertEquals(HttpStatus.OK, pubHouseService.getByIdToFront(DECIMAL).getStatusCode());
    }

    @Test
    void getByTitleToFront() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        PubHouseDto pubHouseDto = new PubHouseDto(pubHouse);
        Mockito.when(pubHouseRepository.findByPubHouseTitle(TEXT)).thenReturn(pubHouse);
        assertEquals(HttpStatus.OK, pubHouseService.getByTitleToFront(pubHouseDto).getStatusCode());
        Mockito.when(pubHouseRepository.findByPubHouseTitle(any())).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, pubHouseService.getByTitleToFront(pubHouseDto).getStatusCode());
    }

    @Test
    void addPubHouseFromFront() {
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        PubHouseDto pubHouseDto = new PubHouseDto(pubHouse);
        Mockito.when(pubHouseRepository.save(any())).thenReturn(pubHouse);
        assertEquals(HttpStatus.OK, pubHouseService.addPubHouseFromFront(pubHouseDto).getStatusCode());
    }

    @Test
    void deleteById(){
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        Mockito.doNothing().when(pubHouseRepository).deleteById(any());
        assertFalse(pubHouseService.deleteById(null));
        Mockito.when(pubHouseRepository.findById(DECIMAL)).thenReturn(Optional.of(pubHouse));
        assertTrue(pubHouseService.deleteById(DECIMAL));
    }

    @Test
    void deleteByIdFromFront(){
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        PubHouseDto pubHouseDto = new PubHouseDto(pubHouse);
        Mockito.when(pubHouseRepository.findById(DECIMAL)).thenReturn(Optional.of(pubHouse));
        assertEquals(HttpStatus.OK, pubHouseService.deleteByIdFromFront(DECIMAL).getStatusCode());
        Mockito.doNothing().when(pubHouseRepository).deleteById(any());
        assertEquals(HttpStatus.BAD_REQUEST, pubHouseService.deleteByIdFromFront(null).getStatusCode());
    }

    @Test
    void deleteByPubHouseTitle(){
        PubHouse pubHouse = new PubHouse(TEXT);
        pubHouse.setId(DECIMAL);
        Mockito.when(pubHouseRepository.findByPubHouseTitle(pubHouse.getPubHouseTitle())).thenReturn(pubHouse);
        assertTrue(pubHouseService.deleteByPubHouseTitle(pubHouse.getPubHouseTitle()));
        Mockito.when(pubHouseRepository.findByPubHouseTitle(any())).thenReturn(null);
        assertFalse(pubHouseService.deleteByPubHouseTitle(null));
    }
}