package ru.bainc.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bainc.security.modelsecurity.User;
import ru.bainc.security.servicesecurity.UserService;

@Service
@Slf4j
public class JwtUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);

        if(user == null){
            throw new JwtAuthenticationException("User with username:" + username + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.createUser(user);
        log.info("User {} successfully loaded", user.getUserName());

        return jwtUser;
    }
}
