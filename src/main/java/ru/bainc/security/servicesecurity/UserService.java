package ru.bainc.security.servicesecurity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bainc.security.dtosecurity.UserDto;
import ru.bainc.security.dtosecurity.UserInDto;
import ru.bainc.security.modelsecurity.Status;
import ru.bainc.security.modelsecurity.User;
import ru.bainc.security.repositorysecurity.UserRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

   @Transactional
    public List<UserDto> getAllToFront() {
        return getAll()
                .stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public User getById(Long id) {
        User result = userRepository.getById(id);
        if(result == null){
            log.warn("IN getById no user found by id:{}",id);
            return null;
        }else {
            log.info("User with id:{} found",id);
            return result;
        }

    }

   @Transactional
    public User getByUserName(String userName) {
        User result = userRepository.findByUserName(userName);
        log.info("User with username: {} found", userName);
        return result;
    }

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
        log.info("User with id:{} deleted", id);
    }

       public User addUser(UserInDto userInDto){
        User user = new User();
        user.setUserName(userInDto.getUserName());
        user.setFirstName(userInDto.getFirstName());
        user.setLastName(userInDto.getLastName());
        user.setStatus(Status.valueOf(userInDto.getStatus()));
        user.setRoles(userInDto.getRolesId()
                .stream()
                .map(role-> roleService.getById(Long.parseLong(role)).orElse(null))
                        .collect(Collectors.toList()));
        user.setPassword(passwordEncoder.encode(userInDto.getPassword()));

        return userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<UserDto> addUserFromFront (UserInDto userInDto){
        User user = getByUserName(userInDto.getUserName());
        if(user != null){
            log.info("Такой User уже существует");
        } else{
            user = addUser(userInDto);
        }
            return new ResponseEntity<>(new UserDto(user),HttpStatus.OK);

        }
    }





