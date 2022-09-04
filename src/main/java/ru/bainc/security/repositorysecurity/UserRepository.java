package ru.bainc.security.repositorysecurity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bainc.security.modelsecurity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String name);
    Integer deleteUserByUserName(String userName);



}
