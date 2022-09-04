package ru.bainc.security.dtosecurity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.security.modelsecurity.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private String name;

    public RoleDto(Role role) {
        this.name = role.getName();
    }
}
