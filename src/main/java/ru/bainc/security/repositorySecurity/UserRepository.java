package ru.bainc.security.repositorySecurity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bainc.security.modelSecurity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String name);
    Integer deleteUserByUserName(String userName);



}
