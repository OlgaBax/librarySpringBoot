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
    private String pubHouseTitle;

    public PubHouseDto (PubHouse pubHouse){
         this.pubHouseTitle = pubHouse.getPubHouseTitle();
         this.id = pubHouse.getId();
    }
}
