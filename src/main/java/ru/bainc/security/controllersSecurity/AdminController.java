package ru.bainc.security.controllersSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.bainc.security.dtoSecurity.UserDto;
import ru.bainc.security.dtoSecurity.UserInDto;
import ru.bainc.security.modelSecurity.User;
import ru.bainc.security.serviceSecurity.RoleService;
import ru.bainc.security.serviceSecurity.UserService;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
