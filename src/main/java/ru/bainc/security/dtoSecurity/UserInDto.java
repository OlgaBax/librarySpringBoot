package ru.bainc.security.dtoSecurity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInDto {

    private String userName;
    private String firstName;
    private String lastName;
    private String status;
    private String password;
    private List<String> rolesId;




}
