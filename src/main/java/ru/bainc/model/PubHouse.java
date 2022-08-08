package ru.bainc.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pubhouses")
public class PubHouse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "title")
    private String pubHouseTitle;

    public PubHouse(String pubHouseTitle) {
        this.pubHouseTitle = pubHouseTitle;
    }
}
