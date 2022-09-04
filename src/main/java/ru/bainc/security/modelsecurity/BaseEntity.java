package ru.bainc.security.modelsecurity;

import lombok.Data;

import javax.persistence.*;

//@Getter
//@Setter
@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
