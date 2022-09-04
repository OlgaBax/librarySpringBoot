package ru.bainc.security.controllerssecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.bainc.security.dtosecurity.UserDto;
import ru.bainc.security.dtosecurity.UserInDto;
import ru.bainc.security.modelsecurity.User;
import ru.bainc.security.servicesecurity.UserService;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllToFront(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserInDto userInDto) {
        return userService.addUserFromFront(userInDto);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto>getUserById(@PathVariable Long id){
        User user = userService.getById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            UserDto result = UserDto.fromUser(user);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
    }

}
