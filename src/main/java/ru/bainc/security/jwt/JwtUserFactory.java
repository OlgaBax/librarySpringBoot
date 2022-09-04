package ru.bainc.security.jwt;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.bainc.security.modelSecurity.Role;
import ru.bainc.security.modelSecurity.Status;
import ru.bainc.security.modelSecurity.User;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser createUser(User user){
        return new JwtUser(
                user.getId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
//                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
//                user.getUpdated(),
                getAuthorities(user.getRoles())
        );

    }
    public static List<GrantedAuthority> getAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
