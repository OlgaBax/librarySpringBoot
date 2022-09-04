package ru.bainc.security.controllerssecurity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("USER")
    @GetMapping
public ResponseEntity<String>hello(){
        return new ResponseEntity<>("Hello, user!", HttpStatus.OK);
    }
}
