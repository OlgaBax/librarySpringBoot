package ru.bainc.security.dtosecurity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.security.modelsecurity.Status;
import ru.bainc.security.modelsecurity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private Status status;
    private List<RoleDto> roles;
    private String password;

    public UserDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.status = user.getStatus();
        this.roles = user.getRoles().stream().map(role -> new RoleDto(role)).collect(Collectors.toList());
        this.password = user.getPassword();

    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setStatus(user.getStatus());
        userDto.setRoles(user.getRoles().stream().map(role -> new RoleDto(role)).collect(Collectors.toList()));
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
