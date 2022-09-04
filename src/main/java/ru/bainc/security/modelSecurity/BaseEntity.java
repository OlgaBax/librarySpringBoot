package ru.bainc.security.modelSecurity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
