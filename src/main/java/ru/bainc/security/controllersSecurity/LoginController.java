package ru.bainc.security.controllersSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bainc.security.dtoSecurity.AuthDto;
import ru.bainc.security.jwt.JwtAuthenticationException;
import ru.bainc.security.jwt.JwtTokenProvider;
import ru.bainc.security.modelSecurity.User;
import ru.bainc.security.serviceSecurity.UserService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public LoginController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        try {
            String userName = authDto.getUsername();
            UsernamePasswordAuthenticationToken passwordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userName, authDto.getPassword());

            authenticationManager.authenticate(passwordAuthenticationToken);
            User user = userService.getByUserName(userName);
            if (user == null) {
                throw new UsernameNotFoundException("Username not found");
            }

            String token = jwtTokenProvider.createToken(userName, user.getRoles());

            Map<Object, Object> result = new HashMap<>();
            result.put("userName", userName);
            result.put("token", token);

            return ResponseEntity.ok(result);

        } catch (JwtAuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//403 ошибка
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credential");//пароль или логин неверный
        }
    }
}
