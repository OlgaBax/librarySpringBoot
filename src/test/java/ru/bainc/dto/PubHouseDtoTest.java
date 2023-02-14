package ru.bainc.dto;

import org.junit.jupiter.api.Test;
import ru.bainc.model.PubHouse;

import static org.junit.jupiter.api.Assertions.*;

class PubHouseDtoTest {
    private final Long DECIMAL = 1L;
    private final String TEXT = "Test";
    private PubHouseDto pubHouseDto = new PubHouseDto();

    @Test
    void setId() {
        pubHouseDto.setId(DECIMAL);
        assertEquals(DECIMAL, pubHouseDto.getId());
    }

    @Test
    void setTitle() {
        pubHouseDto.setTitle(TEXT);
        assertEquals(TEXT, pubHouseDto.getTitle());
    }

    @Test
    void testConstructor(){
        PubHouse pubHouse = new PubHouse();
        pubHouse.setId(DECIMAL);
        pubHouse.setPubHouseTitle(TEXT);

        pubHouseDto = new PubHouseDto(pubHouse);
        assertEquals(DECIMAL, pubHouseDto.getId());
        assertEquals(TEXT, pubHouseDto.getTitle());
    }
}