package ru.bainc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.model.PubHouse;

@Getter
@Setter
@NoArgsConstructor
public class PubHouseDto {
    private Long id;
    private String title;

    public PubHouseDto (PubHouse pubHouse){
        this.id = pubHouse.getId();
        this.title = pubHouse.getPubHouseTitle();
    }
}
